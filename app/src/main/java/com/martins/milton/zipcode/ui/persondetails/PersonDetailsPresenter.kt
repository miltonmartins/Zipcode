package com.martins.milton.zipcode.ui.persondetails

import com.martins.milton.zipcode.data.models.Person
import com.martins.milton.zipcode.data.source.DataResult
import com.martins.milton.zipcode.data.source.PeopleRepository

class PersonDetailsPresenter(
    private val peopleRepository: PeopleRepository,
    private val personDetailsView: PersonDetailsContract.View
) : PersonDetailsContract.Presenter {
    override fun editPerson(person: Person) {
        peopleRepository.editPerson(person)
        personDetailsView.navigateToPeopleList()
    }

    override fun addPerson(person: Person) {
        peopleRepository.addPerson(person)
        personDetailsView.navigateToPeopleList()
    }

    override fun deletePerson(person: Person) {
        peopleRepository.deletePerson(person)
    }

    override fun getPerson(cpf: String) {
        peopleRepository.getPerson(cpf, object : DataResult.Single {
            override fun <T> onDataLoaded(data: T) = personDetailsView.showPerson(data as Person)
            override fun onFailure() = personDetailsView.showMessage("NOT-FOUND")
        })
    }
}