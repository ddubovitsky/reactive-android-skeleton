package com.ddubovitsky.newsapp.ui

import android.annotation.SuppressLint
import com.ddubovitsky.newsapp.framework.BaseReactViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

interface Data;

object Nothing : Data

class DataLoaded(val number: String) : Data

class MainViewModel : BaseReactViewModel<Data>() {

    override val initialViewData = Nothing;

    @SuppressLint("CheckResult")
    fun getData() {
        if(viewData.value != Nothing)
            return;

         Observable.range(1, 5)
                .concatMap({ number -> Observable.just(number).delay(1, TimeUnit.SECONDS) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::postResult)
    }

    private fun postResult(number: Int) {
        updateViewData(DataLoaded("Data numba $number is loaded"))
    }
}