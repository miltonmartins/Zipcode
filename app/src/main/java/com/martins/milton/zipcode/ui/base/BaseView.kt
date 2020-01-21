package com.martins.milton.zipcode.ui.base

interface BaseView<T> {
    var presenter: T
    fun showMessage(message: String)
}