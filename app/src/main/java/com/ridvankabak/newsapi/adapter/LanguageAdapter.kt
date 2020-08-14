package com.ridvankabak.newsapikotlin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.model.Language

class LanguageAdapter(var languageList: ArrayList<Language>):BaseAdapter() {

    var viewHolder:ViewHolder? = null

    class ViewHolder(row:View?){
        var textViewName: TextView
        var imageViewLanguageFlag: ImageView
        var imageViewCheck: ImageView


        init {
            this.textViewName = row?.findViewById(R.id.textViewLanguageName) as TextView
            this.imageViewLanguageFlag = row?.findViewById(R.id.imageViewLanguageFlag) as ImageView
            this.imageViewCheck = row?.findViewById(R.id.imageViewCheckLan)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View? = null

        if(convertView == null){
            var layout = LayoutInflater.from(parent?.context)
            view = layout.inflate(R.layout.list_language,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var language = getItem(position) as Language

        viewHolder?.let {viewHolder ->  viewHolder.textViewName.text = language.name }
        viewHolder?.let { viewHolder -> viewHolder.imageViewLanguageFlag.setImageResource(language.flag) }
        if (language.isSelected ) {
            viewHolder?.let { viewHolder -> viewHolder.imageViewCheck.setImageResource(R.drawable.ic_switch_on) }
        } else {
            viewHolder?.let { viewHolder -> viewHolder.imageViewCheck.setImageResource(R.drawable.ic_switch_off) }
        }

        return view as View
    }

    override fun getItem(position: Int): Any {
        return languageList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return languageList.size
    }
}