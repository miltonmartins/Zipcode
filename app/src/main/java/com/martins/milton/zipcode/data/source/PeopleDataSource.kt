package com.martins.milton.zipcode.data.source

import com.martins.milton.zipcode.data.models.Person

interface PeopleDataSource {
    fun getPeople(callback: DataResult.Many)
    fun getPerson(cpf: String, callback: DataResult.Single)
    fun deletePerson(person: Person)
    fun addPerson(person: Person)
    fun editPerson(person: Person)
}