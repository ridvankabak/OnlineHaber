package com.ridvankabak.newsapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.model.ExpandableSearch

class ExpandableListAdapter(var listDataHeader: ArrayList<String>,var listHashMap: HashMap<String, ArrayList<ExpandableSearch>>):BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return listDataHeader.get(groupPosition)
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val headerTitle = getGroup(groupPosition) as String
        var convertView = convertView

        if (convertView == null) {
            val inflater =
                (parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            convertView = inflater.inflate(R.layout.list_group, null)
        }

        val textView = convertView?.findViewById<TextView>(R.id.lblListHeader)
        textView?.text = headerTitle

        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listHashMap.get(listDataHeader.get(groupPosition))!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return listHashMap.get(listDataHeader.get(groupPosition))!!.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val childText = getChild(groupPosition, childPosition) as ExpandableSearch
        var convertView = convertView

        if (convertView == null) {
            val inflater =
                parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_item, null)
        }

        val textView = convertView?.findViewById<TextView>(R.id.lblListItem)
        textView?.text = childText.title

        val imageView = convertView?.findViewById<ImageView>(R.id.imageviewcheckkk)

        if (childText.isSelected) {
            imageView?.visibility = View.VISIBLE
        } else {
            imageView?.visibility = View.GONE
        }

        //callback.onClickList(childText.title)

        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return listDataHeader.size
    }

    fun addHeaderList(newListDataHeader:ArrayList<String>){
        listDataHeader.clear()
        listDataHeader.addAll(newListDataHeader)
        notifyDataSetChanged()
    }

    fun addHashMap(newListHaspMap:HashMap<String, ArrayList<ExpandableSearch>>){
        listHashMap.clear()
        listHashMap.putAll(newListHaspMap)
        notifyDataSetChanged()
    }
}