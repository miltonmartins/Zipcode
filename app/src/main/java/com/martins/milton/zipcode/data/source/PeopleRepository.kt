package com.martins.milton.zipcode.data.source

import com.martins.milton.zipcode.data.models.Person
import com.martins.milton.zipcode.data.source.local.PeopleLocalRepository

class PeopleRepository(
    private val peopleLocalRepository: PeopleLocalRepository
) : PeopleDataSource {
    override fun addPerson(person: Person) = peopleLocalRepository.addPerson(person)

    override fun deletePerson(person: Person) = peopleLocalRepository.deletePerson(person)

    override fun editPerson(person: Person) = peopleLocalRepository.editPerson(person)

    override fun getPeople(callback: DataResult.Many) {
        peopleLocalRepository.getPeople(object : DataResult.Many {
            override fun <T> onDataLoaded(data: List<T>) = callback.onDataLoaded(data)
            override fun onFailure() = callback.onFailure()
        })
    }

    override fun getPerson(cpf: String, callback: DataResult.Single) {
        peopleLocalRepository.getPerson(cpf, object : DataResult.Single {
            override fun <T> onDataLoaded(data: T) = callback.onDataLoaded(data)
            override fun onFailure() = callback.onFailure()
        })
    }
}