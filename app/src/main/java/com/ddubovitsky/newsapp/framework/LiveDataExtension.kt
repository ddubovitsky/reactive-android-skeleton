package com.ddubovitsky.newsapp.framework

import androidx.lifecycle.LiveData


open class SafeLiveData<T>(defaulValue : T, private val active : ()->Unit = {} , private val inactive : ()->Unit = {}) : LiveData<T>() {

    init {
        value = defaulValue
    }

    override fun getValue(): T {
        return super.getValue()!!
    }

    override fun setValue(value: T) {
        super.setValue(value)
    }

    override fun onActive() {
        super.onActive()
        active.invoke()
    }

    override fun onInactive() {
        super.onInactive()
        inactive.invoke()
    }
}

class MutableSafeLiveData<T>(defaulValue : T, active : ()->Unit = {} , inactive : ()->Unit = {}) :
        SafeLiveData<T>(defaulValue, active = active, inactive = inactive) {
    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        super.postValue(value)
    }
}