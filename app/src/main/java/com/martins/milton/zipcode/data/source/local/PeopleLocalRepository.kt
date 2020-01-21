package com.martins.milton.zipcode.data.source.local

import com.martins.milton.zipcode.data.models.Person
import com.martins.milton.zipcode.data.source.DataResult
import com.martins.milton.zipcode.data.source.PeopleDataSource
import com.martins.milton.zipcode.utils.AppExecutors

class PeopleLocalRepository(
    private val peopleDao: PeopleDao,
    private val appExecutors: AppExecutors = AppExecutors()
) : PeopleDataSource {
    override fun getPeople(callback: DataResult.Many) {
        appExecutors.diskIO.execute {
            val people = peopleDao.getPeople()
            appExecutors.mainThread.execute {
                if (people.isNotEmpty()) {
                    callback.onDataLoaded(people)
                } else {
                    callback.onFailure()
                }
            }
        }
    }

    override fun getPerson(cpf: String, callback: DataResult.Single) {
        appExecutors.diskIO.execute {
            val person = peopleDao.getPerson(cpf)
            appExecutors.mainThread.execute {
                if (person.cpf.isNotEmpty()) {
                    callback.onDataLoaded(person)
                } else {
                    callback.onFailure()
                }
            }
        }
    }

    override fun deletePerson(person: Person) =
        appExecutors.diskIO.execute { peopleDao.deletePerson(person) }

    override fun addPerson(person: Person) =
        appExecutors.diskIO.execute { peopleDao.addPerson(person) }


    override fun editPerson(person: Person) =
        appExecutors.diskIO.execute { peopleDao.editPerson(person) }
}