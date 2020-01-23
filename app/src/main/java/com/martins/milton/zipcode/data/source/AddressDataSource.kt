package com.martins.milton.zipcode.data.source

import com.martins.milton.zipcode.data.models.Address
import retrofit2.Call

interface AddressDataSource {
    fun getAddress(zipCode: String): Call<Address>
}