package com.ddubovitsky.newsapp.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ddubovitsky.newsapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.viewData.observe(this, Observer { data ->
            handleViewData(data!!)
        })
        viewModel.getData()
    }

    fun handleViewData(data: Data) {
        when(data){
            is DataLoaded -> {
                somtext.setText(data.number)
            }
            is Nothing -> {
                somtext.setText("Is there anybody outthere?")
            }
        }
    }
}
