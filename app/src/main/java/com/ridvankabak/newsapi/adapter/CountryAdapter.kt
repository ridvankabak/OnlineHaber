package com.ridvankabak.newsapikotlin.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.model.Country


class CountryAdapter(val countryList: ArrayList<Country>) : BaseAdapter(){
    var viewHolder:ViewHolder? = null

    class ViewHolder(row:View?){
        var textViewName: TextView
        var imageViewCountryFlag: ImageView
        var imageViewCheck: ImageView


        init {
            this.textViewName = row?.findViewById(R.id.textViewCountryName) as TextView
            this.imageViewCountryFlag = row?.findViewById(R.id.imageViewCountryFlag) as ImageView
            this.imageViewCheck = row?.findViewById(R.id.imageViewCheck)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view:View?

        if(convertView == null){
            var layout = LayoutInflater.from(parent.context)
            view = layout.inflate(R.layout.list_country,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var country = getItem(position) as Country

        viewHolder?.textViewName?.text = country.name
        viewHolder?.imageViewCountryFlag?.setImageResource(country.flag)
        if (country.isSelected ) {
            viewHolder!!.imageViewCheck.setImageResource(R.drawable.ic_switch_on)
        } else {
            viewHolder!!.imageViewCheck.setImageResource(R.drawable.ic_switch_off)
        }

        return view as View

    }

    override fun getItem(position: Int): Any {
        return countryList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return countryList.size
    }


}