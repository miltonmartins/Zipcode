package com.martins.milton.zipcode.data.source.local

import androidx.room.*
import com.martins.milton.zipcode.data.models.Person

@Dao
interface PeopleDao {
    @Query("SELECT * FROM People")
    fun getPeople(): List<Person>

    @Query("SELECT * FROM People WHERE cpf = :cpf")
    fun getPerson(cpf: String): Person

    @Delete
    fun deletePerson(person: Person)

    @Insert
    fun addPerson(person: Person)

    @Update
    fun editPerson(person: Person)
}