package com.ridvankabak.newsapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deleteAll()
    }
}
