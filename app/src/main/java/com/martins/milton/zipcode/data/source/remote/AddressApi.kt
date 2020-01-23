package com.martins.milton.zipcode.data.source.remote

import com.martins.milton.zipcode.data.models.Address
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AddressApi {
    @GET("{zipCode}/json")
    fun getAddress(@Path("zipCode") zipCode: String): Call<Address>
}