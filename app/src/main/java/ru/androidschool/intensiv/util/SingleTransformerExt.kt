package ru.androidschool.intensiv.util

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <R> addSchedulers(): SingleTransformer<R, R> {
    return SingleTransformer {
        it.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}
