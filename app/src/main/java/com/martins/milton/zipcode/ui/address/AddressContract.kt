package com.martins.milton.zipcode.ui.address

import com.martins.milton.zipcode.data.models.Address
import com.martins.milton.zipcode.ui.base.ActionType
import com.martins.milton.zipcode.ui.base.BasePresenter
import com.martins.milton.zipcode.ui.base.BaseView

interface AddressContract {

    interface View : BaseView<Presenter> {
        var actionType: ActionType
        fun loadAddress()
        fun loadAddress(address: Address)
        fun showAddress(address: Address?)
        fun bindListeners()
        fun setNumber()
        fun bindHolder(address: Address)
    }

    interface Presenter : BasePresenter {
        fun getAddress(zipCode: String)
    }
}