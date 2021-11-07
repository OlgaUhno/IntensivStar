package ru.androidschool.intensiv.domain.usecase

import android.view.View
import io.reactivex.*
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

fun <T> Single<T>.addSchedulers(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
