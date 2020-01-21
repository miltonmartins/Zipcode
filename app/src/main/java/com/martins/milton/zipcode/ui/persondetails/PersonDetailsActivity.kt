package com.martins.milton.zipcode.ui.persondetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.martins.milton.zipcode.R
import com.martins.milton.zipcode.data.source.PeopleRepository
import com.martins.milton.zipcode.data.source.local.PeopleLocalRepository
import com.martins.milton.zipcode.data.source.local.ZipcodeDatabase
import com.martins.milton.zipcode.ui.base.Constants
import com.martins.milton.zipcode.utils.replaceFragmentInActivity

class PersonDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        startFragment()
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()

        return super.onSupportNavigateUp()
    }

    private fun startFragment() {
        val cpf = intent.getStringExtra(Constants.EXTRA_CPF)

        val personDetailsFragment = supportFragmentManager
            .findFragmentById(R.id.frame_person_details_content) as PersonDetailsFragment?
            ?: PersonDetailsFragment.newInstance(cpf).also {
                replaceFragmentInActivity(it, R.id.frame_person_details_content)
            }

        personDetailsFragment.presenter = PersonDetailsPresenter(
            PeopleRepository(PeopleLocalRepository(ZipcodeDatabase.getInstance(this).peopleDao())),
            personDetailsFragment
        )
    }
}
