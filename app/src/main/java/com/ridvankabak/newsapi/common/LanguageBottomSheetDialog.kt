package com.ridvankabak.newsapi.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.model.Language
import com.ridvankabak.newsapi.view.HomeFragment
import com.ridvankabak.newsapikotlin.Adapter.LanguageAdapter
import kotlinx.android.synthetic.main.bottom_sheet_dil.*

class LanguageBottomSheetDialog(var languageList:ArrayList<Language>,var callback:HomeFragment) : BottomSheetDialogFragment() {

    var adapter: LanguageAdapter? = null

    var selectedIndex: Int = 0
    var selectedItem: Language? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.bottom_sheet_dil, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setlistener()
    }
    private fun setlistener() {

        listViewLanguage?.setOnItemClickListener { parent, view, position, id ->
            languageList.forEach{
                it.isSelected = false
            }
            languageList.get(selectedIndex).isSelected = false
            languageList.get(position).isSelected = true
            selectedItem = languageList.get(position)
            selectedIndex = position
            adapter?.notifyDataSetChanged()
        }

        buttonLanguage?.setOnClickListener {
            selectedItem?.let { item ->
                callback.onClickedLanguage(switch(item.name))
                this.dismiss()
            }
        }
    }

    private fun switch(languageName: String): String {
        when(languageName){
            "Türkçe" -> return "tr"
            "Almanca" -> return "de"
            "İngilizce" -> return "en"
            "İspanyolca" -> return "es"
            "İngilizcee" -> return "en"
            "Fransızca" -> return "fr"
        }
        return ""
    }

    private fun initUi() {
        languageList.get(selectedIndex).isSelected = false
        adapter = LanguageAdapter(languageList)
        listViewLanguage?.adapter = adapter
    }

    interface BSheetLanguageListener {
        fun onClickedLanguage(languageText: String)
    }

}