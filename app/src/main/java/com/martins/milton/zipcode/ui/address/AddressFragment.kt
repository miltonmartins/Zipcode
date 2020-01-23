package com.martins.milton.zipcode.ui.address

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.martins.milton.zipcode.R
import com.martins.milton.zipcode.data.models.Address
import com.martins.milton.zipcode.data.source.AddressRepository
import com.martins.milton.zipcode.ui.base.ActionType
import com.martins.milton.zipcode.utils.MaskEditUtil
import com.martins.milton.zipcode.utils.toast
import kotlinx.android.synthetic.main.fragment_address.*

class AddressFragment : Fragment(), AddressContract.View {
    override var presenter: AddressContract.Presenter = AddressPresenter(AddressRepository(), this)
    override lateinit var actionType: ActionType

    private var mAddress: Address? = null
    private lateinit var mAddressCallback: AddressCallback

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        actionType = ActionType.ADD
        btn_address_action.setOnClickListener {
            when (actionType) {
                ActionType.ADD -> {
                    mAddressCallback.onAdd(mAddress!!)
                    bindHolder(mAddress!!)
                }
                ActionType.DELETE -> {
                    mAddressCallback.onDelete(mAddress!!)
                }
                else -> Unit
            }
        }

        if (mAddress != null) {
            loadAddress(mAddress!!)
        }

        bindListeners()
    }

    override fun bindListeners() {
        txt_address_zipCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) = Unit
            override fun beforeTextChanged(s: CharSequence, st: Int, c: Int, a: Int) = Unit
            override fun onTextChanged(s: CharSequence, st: Int, b: Int, c: Int) {
                if (s.length == 9) {
                    loadAddress()
                }
            }
        })

        txt_address_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) = Unit
            override fun beforeTextChanged(s: CharSequence, st: Int, c: Int, a: Int) = Unit
            override fun onTextChanged(s: CharSequence, st: Int, b: Int, c: Int) {
                if (s.isNotEmpty()) {
                    btn_address_action.visibility = View.VISIBLE
                } else {
                    btn_address_action.visibility = View.GONE
                }

                setNumber()
            }
        })

        txt_address_zipCode.addTextChangedListener(
            MaskEditUtil.mask(
                txt_address_zipCode,
                MaskEditUtil.FORMAT_CEP
            )
        )
    }

    override fun loadAddress(address: Address) {
        bindHolder(address)
    }

    override fun setNumber() {
        mAddress?.number = txt_address_number.text.toString()
    }

    override fun bindHolder(address: Address) {
        txt_address_zipCode.setText(address.zipCode)
        txt_address_zipCode.isEnabled = false
        txt_address_full.text = address.toString()

        txt_address_number_label.visibility = View.VISIBLE
        txt_address_number.visibility = View.VISIBLE
        txt_address_number.isEnabled = false
        txt_address_number.setText(address.number)

        btn_address_action.setImageResource(R.drawable.ic_delete)
        btn_address_action.visibility = View.VISIBLE
        actionType = ActionType.DELETE
    }

    override fun loadAddress() {
        presenter.getAddress(MaskEditUtil.unmask(txt_address_zipCode.text.toString()))
    }

    override fun showAddress(address: Address?) {
        mAddress = address
        txt_address_full.text = address.toString()

        txt_address_number_label.visibility = View.VISIBLE
        txt_address_number.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {
        context?.toast(message)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    companion object {
        fun newInstance(
            addressCallback: AddressCallback,
            address: Address? = null
        ) =
            AddressFragment().apply {
                mAddress = address
                mAddressCallback = addressCallback
            }
    }
}