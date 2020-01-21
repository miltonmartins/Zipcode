package com.martins.milton.zipcode.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class Address(
    @PrimaryKey val zipCode: String,
    var street: String,
    var complement: String,
    var number: String,
    var neighborhood: String,
    var uf: String,
    var city: String
)