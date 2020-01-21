package com.martins.milton.zipcode.ui.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.martins.milton.zipcode.R
import com.martins.milton.zipcode.data.models.Person

class PeopleAdapter(private val items: List<Person>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false).let {
            return PersonHolder(it)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is PersonHolder -> holder.bind(item)
        }
    }
}