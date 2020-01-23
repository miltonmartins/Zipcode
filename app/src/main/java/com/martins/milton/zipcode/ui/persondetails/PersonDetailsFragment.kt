package com.martins.milton.zipcode.ui.persondetails

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.martins.milton.zipcode.R
import androidx.fragment.app.FragmentTransaction
import com.martins.milton.zipcode.data.models.Address
import com.martins.milton.zipcode.data.models.Person
import com.martins.milton.zipcode.ui.address.AddressCallback
import com.martins.milton.zipcode.ui.address.AddressFragment
import com.martins.milton.zipcode.ui.base.ActionType
import com.martins.milton.zipcode.ui.base.Constants
import com.martins.milton.zipcode.utils.*
import kotlinx.android.synthetic.main.fragment_person_details.*
import java.io.InputStream

class PersonDetailsFragment : Fragment(), PersonDetailsContract.View {
    override lateinit var presenter: PersonDetailsContract.Presenter

    private lateinit var actionType: ActionType
    private var mAddresses = mutableListOf<Address>()
    private var mAddressesFragment = mutableListOf<AddressFragment>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        start()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save_icon -> {
                setupSaveButton()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun start() {
        when (val cpf = this.arguments?.get(Constants.EXTRA_CPF)) {
            null -> {
                actionType = ActionType.ADD
                activity?.title = getString(R.string.person_new)
                bindRequired()
                addAddressFragment(null)
            }
            else -> {
                actionType = ActionType.EDIT
                activity?.title = getString(R.string.person_edit)
                loadPerson(cpf.toString())
            }
        }

        bindListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_details, container, false)
    }

    override fun bindListeners() {
        person_details_cpf.addTextChangedListener(
            MaskEditUtil.mask(
                person_details_cpf,
                MaskEditUtil.FORMAT_CPF
            )
        )
        person_details_birthDate.addTextChangedListener(
            MaskEditUtil.mask(
                person_details_birthDate,
                MaskEditUtil.FORMAT_DATE
            )
        )

        person_details_photo.setOnClickListener {
            loadPhoto()
        }
    }

    override fun showMessage(message: String) {
        context?.toast(message)
    }

    override fun bindRequired() {
        person_details_cpf.error = getString(R.string.required)
        person_details_name.error = getString(R.string.required)
        person_details_birthDate.error = getString(R.string.required)
    }

    override fun setupSaveButton() {
        if (validateFields()) {
            when (actionType) {
                ActionType.ADD -> presenter.addPerson(getPersonData())
                ActionType.EDIT -> presenter.editPerson(getPersonData())
                ActionType.DELETE -> Unit
            }
        }
    }

    override fun bindFocusAndError(editText: EditText, error: String) {
        editText.let {
            when {
                it.text.isNullOrEmpty() -> {
                    it.requestFocus()
                    it.error = error
                    throw Exception()
                }

                else -> {
                    it.error = null
                }
            }
        }
    }

    override fun validateFields(): Boolean {
        try {
            bindFocusAndError(person_details_name, getString(R.string.required))
            bindFocusAndError(person_details_cpf, getString(R.string.required)).also {
                person_details_cpf.let {
                    if (it.text.length < 14) {
                        it.error = getString(R.string.cpf_invalid)
                        it.requestFocus()
                        return false
                    }
                }
            }
            bindFocusAndError(person_details_birthDate, getString(R.string.required)).also {
                person_details_birthDate.let {
                    if (it.text.length < 10) {
                        it.error = getString(R.string.date_invalid)
                        it.requestFocus()
                        return false
                    }
                }
            }

            person_details_photo.let {
                if (it.drawable == null) {
                    it.requestFocus()
                    context?.toast(getString(R.string.image_import_required))
                    return false
                }
            }

            return true

        } catch (e: Exception) {
            Log.e("error", e.toString())
            return false
        }
    }

    override fun getPersonData(): Person {
        return Person(
            cpf = MaskEditUtil.unmask(person_details_cpf.text.toString()),
            name = person_details_name.text.toString(),
            birthDate = person_details_birthDate.text.toString().toDate(),
            photo = ImageUtil.convert(person_details_photo.drawable.toBitmap()),
            addresses = mAddresses
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                val imageStream: InputStream? =
                    activity?.contentResolver?.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)

                person_details_photo.setImageBitmap(selectedImage)
            } catch (e: Exception) {
                Log.e("image_error", e.toString())
                context?.toast(getString(R.string.image_import_error))
            }
        } else {
            context?.toast(getString(R.string.image_import_select_image))
        }
    }

    override fun showPerson(person: Person) {
        person_details_cpf.setText(person.cpf)
        person_details_cpf.isEnabled = false
        person_details_name.setText(person.name)
        person_details_birthDate.setText(person.birthDate.format())
        person_details_photo.setImageBitmap(ImageUtil.convert(person.photo))
        mAddresses = person.addresses as MutableList<Address>

        for (address in mAddresses) {
            addAddressFragment(address)
        }

        addAddressFragment(null)
    }

    override fun loadPerson(cpf: String) = presenter.getPerson(cpf)

    override fun navigateToPeopleList() {
        this.activity?.finish()
    }

    override fun addAddressFragment(address: Address?) {
        val fragMan: FragmentManager? = fragmentManager
        val fragTransaction: FragmentTransaction = fragMan!!.beginTransaction()

        val addressFragment = AddressFragment.newInstance(object : AddressCallback {
            override fun onAdd(address: Address) = addAddress(address)

            override fun onDelete(address: Address) {
                val index = mAddresses.indexOf(address)
                removeAddressFragment(index)
                mAddresses.removeAt(index)
            }
        }, address)

        mAddressesFragment.add(addressFragment)

        fragTransaction.add(R.id.layout_person_details_main, addressFragment, "fragment")
        fragTransaction.commit()
    }

    override fun addAddress(address: Address) {
        mAddresses.add(address)
        addAddressFragment(null)
    }

    override fun removeAddressFragment(index: Int) {
        val fragMan: FragmentManager? = fragmentManager
        val fragTransaction: FragmentTransaction = fragMan!!.beginTransaction()

        val addressFragment = mAddressesFragment[index]
        mAddressesFragment.removeAt(index)

        fragTransaction.remove(addressFragment)
        fragTransaction.commit()
    }

    override fun loadPhoto() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 123)
    }

    companion object {
        fun newInstance(cpf: String?) =
            PersonDetailsFragment().apply {
                arguments = Bundle().apply { putString(Constants.EXTRA_CPF, cpf) }
            }
    }
}
