package com.martins.milton.zipcode.data.source

import com.martins.milton.zipcode.data.models.Address
import com.martins.milton.zipcode.data.source.remote.AddressApi
import com.martins.milton.zipcode.utils.NetworkUtil
import retrofit2.Call

class AddressRepository(
    private val addressesApi: AddressApi = NetworkUtil
        .getRetrofitInstance().create(AddressApi::class.java)
) : AddressDataSource {
    override fun getAddress(zipCode: String): Call<Address> =
        addressesApi.getAddress(zipCode)
}