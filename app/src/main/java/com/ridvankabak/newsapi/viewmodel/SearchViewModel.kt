package com.ridvankabak.newsapi.viewmodel

import android.app.DatePickerDialog
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ridvankabak.newsapi.model.ExpandableSearch
import com.ridvankabak.newsapi.model.Language
import com.ridvankabak.newsapi.view.SearchFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SearchViewModel :ViewModel(){
    val header = MutableLiveData<ArrayList<String>>()
    val hashValue = MutableLiveData<HashMap<String,ArrayList<ExpandableSearch>>>()
    val flags = MutableLiveData<ArrayList<Language>>()
    var myCalendar: Calendar = Calendar.getInstance()

    var globalDateTo = MutableLiveData<String>()
    var globalDateFrom = MutableLiveData<String>()

    var selectedIndex: Int = 0
    var selectedItem: ExpandableSearch? = null

    fun getData(){
        header.value = ExpandableSearch.SupplierExpandable.header
        hashValue.value = ExpandableSearch.SupplierExpandable.hashExpandable
        flags.value = Language.SupplierLanguage.language
    }

    val dateTo = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, month)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        globalDateTo.value = updateLabel(myCalendar)
        Log.e("from",globalDateTo.value)

    }

    val dateFrom = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, month)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        globalDateFrom.value = updateLabel(myCalendar)
        Log.e("from",globalDateFrom.value)


    }

    private fun updateLabel(myCalendar: Calendar): String {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat)
        val date = sdf.format(myCalendar.time)
        return date
    }

    fun onClickChild(groupPosition:Int,childPosition:Int) {
        ExpandableSearch.SupplierExpandable.hashExpandable?.get(ExpandableSearch.SupplierExpandable.header?.get(groupPosition))?.get(selectedIndex)!!.isSelected =
            false
        ExpandableSearch.SupplierExpandable.hashExpandable?.get(ExpandableSearch.SupplierExpandable.header?.get(groupPosition))?.get(childPosition)!!.isSelected =
            true
        selectedItem = ExpandableSearch.SupplierExpandable.hashExpandable!!.get(ExpandableSearch.SupplierExpandable.header?.get(groupPosition))?.get(childPosition)
        selectedIndex = childPosition
    }

    fun switch(title: String):String {
        when(title){
            "Yeni Haberler" ->  return "publishedAt"
            "Manşetler" -> return "relevancy"
            "En Çok Okunan" -> return "populerity"
        }
        return ""
    }
}