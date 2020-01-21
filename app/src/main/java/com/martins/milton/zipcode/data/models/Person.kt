package com.martins.milton.zipcode.data.models

import androidx.room.*
import com.martins.milton.zipcode.utils.DateConverter
import java.util.Date

@Entity(tableName = "people")
@TypeConverters(DateConverter::class)
data class Person(
    @PrimaryKey var cpf: String,
    var name: String,
    var birthDate: Date,
    var photo: String
) {
    @Ignore
    var addresses: List<Address> = mutableListOf()

    constructor(
        cpf: String,
        name: String,
        birthDate: Date,
        photo: String, addresses: List<Address> = mutableListOf()
    ) : this(cpf, name, birthDate, photo) {
        this.addresses = addresses
    }
}