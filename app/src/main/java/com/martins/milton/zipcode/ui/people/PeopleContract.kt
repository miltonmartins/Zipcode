package com.martins.milton.zipcode.ui.people

import com.martins.milton.zipcode.data.models.Person
import com.martins.milton.zipcode.ui.base.BasePresenter
import com.martins.milton.zipcode.ui.base.BaseView

interface PeopleContract {

    interface View : BaseView<Presenter> {
        fun onClickAddMenu()
        fun loadPeople()
        fun showPeople(people: List<Person>)
        fun showEmptyList()
        fun hideEmptyList()
        fun removePerson(index: Int)
    }

    interface Presenter : BasePresenter {
        fun getPeople()
        fun deletePerson(person: Person)
    }
}