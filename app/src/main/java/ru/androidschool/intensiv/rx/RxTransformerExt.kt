package ru.androidschool.intensiv.rx

import android.view.View
import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <R> addSingleSchedulers(): SingleTransformer<R, R> {
    return SingleTransformer {
        it.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}

fun <R> addSingleProgress(progress: View): SingleTransformer<R, R> {
    return SingleTransformer {
        it.doOnSubscribe { progress.visibility = View.VISIBLE }
            .doFinally { progress.visibility = View.GONE }
    }
}

fun addCompletableSchedulers(): CompletableTransformer {
    return CompletableTransformer {
        it.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}

fun <R> addObservableSchedulers(): ObservableTransformer<R, R> {
    return ObservableTransformer {
        it.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}

fun <R> addFlowableSchedulers(): FlowableTransformer<R, R> {
    return FlowableTransformer {
        it.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}
