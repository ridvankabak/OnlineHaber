package com.ridvankabak.newsapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.model.Language
import com.ridvankabak.newsapi.view.SearchFragment
import kotlinx.android.synthetic.main.grid_list_language.view.*

class LanguageAdapterSearch(var languageList:ArrayList<Language>,var callback:SearchFragment) : RecyclerView.Adapter<LanguageAdapterSearch.LanguageViewHolder>(){

    var selectedIndex: Int = 0
    var selectedItem: Language? = null

    inner class LanguageViewHolder(view:View): RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.grid_list_language, parent, false)
        return LanguageViewHolder(view)

    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.itemView.imageViewLanguageFlag.setImageResource(languageList[position].flag)
        if (languageList[position].isSelected) {
            holder.itemView.imageViewLanguageCheck.visibility = View.VISIBLE
            holder.itemView.imageViewLanguageCheck.setImageResource(R.drawable.ic_check)
        } else {
            holder.itemView.imageViewLanguageCheck.visibility = View.INVISIBLE
        }

        holder.itemView.rvLanguage.setOnClickListener{
            languageList.forEach {
                it.isSelected = false
            }
            languageList.get(selectedIndex).isSelected = false
            languageList.get(position).isSelected = true
            selectedItem = languageList.get(position)
            selectedIndex = position

            callback.onClickListener(switch(languageList.get(position).name),position)

            notifyDataSetChanged()
        }
    }

    private fun switch(languageDil: String): String {
        when(languageDil){
            "Türkçe" -> return "tr"
            "Almanca" -> return "de"
            "İngilizce" -> return "en"
            "İspanyolca" -> return "es"
            "İngilizcee" -> return "en"
            "Fransızca" -> return "fr"
        }

        return ""
    }

    fun flagListUptade(newFlagList: List<Language>) {
        languageList.clear()
        languageList.addAll(newFlagList)
        notifyDataSetChanged()
    }

    interface FlagClickListener{
        fun onClickListener(title:String,position:Int?)
    }
}