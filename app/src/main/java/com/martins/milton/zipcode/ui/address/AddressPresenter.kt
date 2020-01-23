package com.martins.milton.zipcode.ui.address

import com.martins.milton.zipcode.data.models.Address
import com.martins.milton.zipcode.data.source.AddressRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressPresenter(
    private val addressRepository: AddressRepository,
    private val addressView: AddressContract.View
) : AddressContract.Presenter {

    override fun getAddress(zipCode: String) {
        val callback = addressRepository.getAddress(zipCode)

        callback.enqueue(object : Callback<Address> {

            override fun onFailure(call: Call<Address>, t: Throwable) {
                addressView.showMessage(t.toString())
            }

            override fun onResponse(call: Call<Address>, response: Response<Address>) {
                addressView.showAddress(response.body())
            }
        })
    }
}