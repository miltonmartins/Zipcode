package com.martins.milton.zipcode.ui.people

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.martins.milton.zipcode.R
import com.martins.milton.zipcode.data.models.Person
import com.martins.milton.zipcode.data.source.PeopleRepository
import com.martins.milton.zipcode.data.source.local.PeopleLocalRepository
import com.martins.milton.zipcode.data.source.local.ZipcodeDatabase
import com.martins.milton.zipcode.ui.persondetails.PersonDetailsActivity
import com.martins.milton.zipcode.ui.base.Constants
import com.martins.milton.zipcode.utils.toast
import kotlinx.android.synthetic.main.activity_people.*

class PeopleActivity : AppCompatActivity(), PeopleContract.View {
    override lateinit var presenter: PeopleContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people)

        presenter = PeoplePresenter(
            PeopleRepository(PeopleLocalRepository(ZipcodeDatabase.getInstance(this).peopleDao())),
            this
        )

        setupAddButton()
    }

    override fun onResume() {
        super.onResume()
        loadPeople()
    }

    override fun loadPeople() {
        presenter.getPeople()
    }

    override fun showMessage(message: String) {
        this.toast(message)
    }

    override fun showPeople(people: List<Person>) {
        rv_people.run {
            layoutManager = LinearLayoutManager(this@PeopleActivity)
            adapter = PeopleAdapter(people)
        }
    }

    override fun showEmptyList() {
        txt_empty_list.visibility = View.VISIBLE
    }

    override fun hideEmptyList() {
        txt_empty_list.visibility = View.GONE
    }

    override fun setupAddButton() {
        this.fab_action.setOnClickListener {
            val cpf: String? = null
            val intent = Intent(applicationContext, PersonDetailsActivity::class.java).apply {
                putExtra(Constants.EXTRA_CPF, cpf)
            }

            startActivity(intent)
        }
    }
}