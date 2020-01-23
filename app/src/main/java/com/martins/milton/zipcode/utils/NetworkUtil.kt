package com.martins.milton.zipcode.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtil {

    companion object {
        private const val URL_ADDRESS = "https://viacep.com.br/ws/"
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(URL_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}