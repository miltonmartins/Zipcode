package com.martins.milton.zipcode.data.models

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("cep") val zipCode: String,
    @SerializedName("logradouro") var street: String,
    @SerializedName("complemento") var complement: String,
    @SerializedName("bairro") var neighborhood: String,
    @SerializedName("uf") var uf: String,
    @SerializedName("localidade") var city: String,
    var number: String
) {
    override fun toString(): String {
        return "$street, $neighborhood, $complement, $city/$uf"
    }
}