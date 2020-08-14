package com.ridvankabak.newsapi.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.model.Country
import com.ridvankabak.newsapi.view.HomeFragment
import com.ridvankabak.newsapikotlin.Adapter.CountryAdapter
import kotlinx.android.synthetic.main.bottom_sheet_ulke.*

class CountryBottomSheetDialog(var countryList:ArrayList<Country>,var callback:HomeFragment) : BottomSheetDialogFragment() {

    var adapter: CountryAdapter? = null
    var selectedIndex: Int = null ?: 0
    var selectedItem: Country? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.bottom_sheet_ulke, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryList.get(selectedIndex).isSelected = true
        adapter = CountryAdapter(countryList)
        listViewCountry.adapter = adapter

        listViewCountry.setOnItemClickListener { parent, view, position, id ->
            countryList.get(selectedIndex).isSelected = false
            countryList.get(position).isSelected = true
            selectedItem = countryList.get(position)
            selectedIndex = position
            adapter?.notifyDataSetChanged()
        }

        buttonCountryClick.setOnClickListener{
            selectedItem?.let { item ->
                callback.onClickedCountry((switch(item.name)))
                this.dismiss()
            }
        }
    }

    private fun switch(countryName: String): String {
        when(countryName){
            "Türkiye" -> return "tr"
            "Almanya" -> return "de"
            "Amerika" -> return "us"
            "İspanya" -> return "es"
            "İngiltere" -> return "gb"
            "Fransa" -> return "fr"
        }
        return ""
    }

    interface BSheetCountryListener {
        fun onClickedCountry(countryText: String)
    }
}