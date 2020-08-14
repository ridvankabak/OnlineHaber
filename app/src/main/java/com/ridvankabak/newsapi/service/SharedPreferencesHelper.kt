package com.ridvankabak.newsapi.service

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {

    companion object{
        private val TIME = "time"
        private var sharedPreferences:SharedPreferences? = null

        @Volatile private var instance : SharedPreferencesHelper? = null
        private val lock = Any()
        operator fun invoke(context: Context): SharedPreferencesHelper = instance
            ?: synchronized(lock){
            instance
                ?: makePrivateSharedPreferences(
                    context
                ).also {
                instance = it
            }
        }

        private fun makePrivateSharedPreferences(context: Context): SharedPreferencesHelper {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveTime(time:Long){
        sharedPreferences?.edit(commit = true){
            putLong(TIME,time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(
        TIME,0)


}