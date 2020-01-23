package com.martins.milton.zipcode.ui.persondetails

import android.widget.EditText
import com.martins.milton.zipcode.data.models.Address
import com.martins.milton.zipcode.data.models.Person
import com.martins.milton.zipcode.ui.base.BasePresenter
import com.martins.milton.zipcode.ui.base.BaseView

interface PersonDetailsContract {

    interface View : BaseView<Presenter> {
        fun start()
        fun showPerson(person: Person)
        fun loadPerson(cpf: String)
        fun navigateToPeopleList()
        fun loadPhoto()
        fun setupSaveButton()
        fun getPersonData(): Person
        fun validateFields(): Boolean
        fun bindListeners()
        fun bindFocusAndError(editText: EditText, error: String)
        fun removeAddressFragment(index: Int)
        fun addAddress(address: Address)
        fun addAddressFragment(address: Address?)
        fun bindRequired()
    }

    interface Presenter : BasePresenter {
        fun getPerson(cpf: String)
        fun editPerson(person: Person)
        fun addPerson(person: Person)
    }
}