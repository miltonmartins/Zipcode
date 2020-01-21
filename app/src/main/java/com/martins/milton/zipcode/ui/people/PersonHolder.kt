package com.martins.milton.zipcode.ui.people

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.martins.milton.zipcode.data.models.Person
import com.martins.milton.zipcode.ui.base.Constants
import com.martins.milton.zipcode.ui.persondetails.PersonDetailsActivity
import com.martins.milton.zipcode.utils.ImageUtil
import com.martins.milton.zipcode.utils.format
import com.martins.milton.zipcode.utils.formatCpf
import kotlinx.android.synthetic.main.item_person.view.*

class PersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(person: Person) {
        with(itemView) {
            item_person_cpf.text = person.cpf.formatCpf()
            item_person_photo.setImageBitmap(ImageUtil.convert(person.photo))
            item_person_name.text = person.name
            item_person_birthDate.text = person.birthDate.format()
            item_person_card.setOnClickListener {
                val intent = Intent(context, PersonDetailsActivity::class.java).apply {
                    putExtra(Constants.EXTRA_CPF, person.cpf)
                }

                context.startActivity(intent)
            }
        }
    }
}