package ru.androidschool.intensiv.rx

import android.view.View
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <R> addSchedulers(): SingleTransformer<R, R> {
    return SingleTransformer {
        it.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}

fun <R> addProgress(progress: View? = null): SingleTransformer<R, R> {
    return SingleTransformer {
        it.doOnSubscribe { progress?.visibility = View.VISIBLE }
            .doFinally { progress?.visibility = View.GONE }
    }
}
