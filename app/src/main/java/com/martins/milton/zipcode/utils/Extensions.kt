package com.martins.milton.zipcode.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun Context.toast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}

@SuppressLint("SimpleDateFormat")
fun Date.format(): String {
    return SimpleDateFormat("dd/MM/yyyy").format(this)
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date {
    return this?.let {
        SimpleDateFormat("dd/MM/yyyy").parse(it)
    }
}

fun String.formatCpf(): String {
    return "${this.substring(0, 3)}.${this.substring(3, 6)}.${this.substring(
        6,
        9
    )}-${this.substring(9, 11)}"
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}
