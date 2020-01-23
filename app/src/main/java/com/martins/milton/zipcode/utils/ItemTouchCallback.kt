package com.martins.milton.zipcode.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.martins.milton.zipcode.ui.base.BaseView
import com.martins.milton.zipcode.ui.people.PeopleContract

class ItemTouchCallback(
    private val mView: BaseView<*>,
    dragDirs: Int = 0,
    swipeDirs: Int = ItemTouchHelper.LEFT
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        i: Int
    ) {
        if (i == ItemTouchHelper.LEFT) {
            when (mView) {
                is PeopleContract.View -> {
                    mView.removePerson(viewHolder.adapterPosition)
                }
            }
        }
    }
}