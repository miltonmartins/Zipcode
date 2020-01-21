package com.martins.milton.zipcode.data.source

interface DataResult {
    interface Single {
        fun <T> onDataLoaded(data: T)
        fun onFailure()
    }

    interface Many {
        fun <T> onDataLoaded(data: List<T>)
        fun onFailure()
    }
}