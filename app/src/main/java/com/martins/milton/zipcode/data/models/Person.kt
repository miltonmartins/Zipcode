package com.martins.milton.zipcode.data.models

import androidx.room.*
import com.martins.milton.zipcode.utils.DateConverter
import com.martins.milton.zipcode.utils.ListConverter
import java.util.Date

@Entity(tableName = "people")
@TypeConverters(DateConverter::class, ListConverter::class)
data class Person(
    @PrimaryKey var cpf: String,
    var name: String,
    var birthDate: Date,
    var photo: String,
    var addresses: List<Address>
)