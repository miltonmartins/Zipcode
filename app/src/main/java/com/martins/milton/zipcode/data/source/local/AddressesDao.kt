package com.martins.milton.zipcode.data.source.local

import androidx.room.*
import com.martins.milton.zipcode.data.models.Address

@Dao
interface AddressesDao {
    @Query("SELECT * FROM addresses WHERE zipCode = :zipCode")
    fun getAddress(zipCode: String): Address
}