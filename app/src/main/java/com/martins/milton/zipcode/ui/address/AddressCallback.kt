package com.martins.milton.zipcode.ui.address

import com.martins.milton.zipcode.data.models.Address

interface AddressCallback {
    fun onAdd(address: Address)
    fun onDelete(address: Address)
}