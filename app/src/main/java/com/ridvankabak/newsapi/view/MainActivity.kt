package com.ridvankabak.newsapi.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ridvankabak.newsapi.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("onCreate","Çalıştı")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy","Çalıştı")
    }

}
