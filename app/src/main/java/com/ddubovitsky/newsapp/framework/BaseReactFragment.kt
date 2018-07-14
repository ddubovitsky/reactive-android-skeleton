package com.ddubovitsky.newsapp.framework

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

open class FragmentCreator<T : Parcelable, F : BaseReactFragment<T, *, *>>(val fragmentClass : Class<F>) {
    fun newInstance(params : T) : F {
        return fragmentClass.newInstance().apply {
            initParams = params
        }
    }
}

abstract class BaseReactFragment<I : Parcelable, D, T : BaseReactViewModel<D>> : Fragment() {

    companion object {
        val INIT_PARAMS = "INIT_PARAMS"
    }

    protected lateinit var viewModel: T

    abstract protected fun applyViewData(viewData: D)

    var initParams : I?
    get() = arguments?.getParcelable<I>(INIT_PARAMS)
    set(value) {
        val bundle = Bundle()
        bundle.putParcelable(INIT_PARAMS, value)
        arguments = bundle
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return createViewModel() as T
            }
        }).get(getType())
        viewModel.onRestore(savedInstanceState)
        viewModel.viewData.observe(this, Observer {
            applyViewData(it!!)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun createViewModel(): T

    abstract fun getType(): Class<T>

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.onSave(outState)
    }
}