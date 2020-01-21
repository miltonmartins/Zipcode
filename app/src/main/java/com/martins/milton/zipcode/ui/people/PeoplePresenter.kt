package com.martins.milton.zipcode.ui.people

import com.martins.milton.zipcode.data.models.Person
import com.martins.milton.zipcode.data.source.DataResult
import com.martins.milton.zipcode.data.source.PeopleRepository

class PeoplePresenter(
    private val peopleRepository: PeopleRepository,
    private val peopleView: PeopleContract.View
) : PeopleContract.Presenter {
    override fun deletePerson(person: Person) {
        TODO("not implemented")
    }

    override fun getPeople() {
        peopleRepository.getPeople(object : DataResult.Many {
            override fun <T> onDataLoaded(data: List<T>) {
                peopleView.showPeople(data as List<Person>)
                peopleView.hideEmptyList()
            }

            override fun onFailure() {
                peopleView.showEmptyList()
            }
        })
    }
}