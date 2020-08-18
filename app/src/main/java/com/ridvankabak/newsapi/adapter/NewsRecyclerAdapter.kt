package com.ridvankabak.newsapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.databinding.CardViewBinding
import com.ridvankabak.newsapi.model.Article
import com.ridvankabak.newsapi.util.downloadImage
import com.ridvankabak.newsapi.util.makePlaceHolder
import com.ridvankabak.newsapi.view.HomeFragment
import com.ridvankabak.newsapi.view.HomeFragmentDirections
import kotlinx.android.synthetic.main.card_view.view.*

class NewsRecyclerAdapter(val context: HomeFragment, val newsList:ArrayList<Article>): RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    class NewsViewHolder(var view:CardViewBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CardViewBinding>(inflater,R.layout.card_view,parent,false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.view.news = newsList.get(position)

        holder.view.listener = context

        /*holder.itemView.textViewTitle.text = newsList.get(position).title
        holder.itemView.textViewDesc.text = newsList.get(position).description
        holder.itemView.textViewAuthor.text = newsList.get(position).author
        holder.itemView.textViewCalendar.text = newsList.get(position).publishedAt

        //Görsel eklenecek

        holder.itemView.imageViewNews.downloadImage(newsList.get(position).urlToImage,
            makePlaceHolder(holder.itemView.context))*/
    }

    fun newsListUptade(newNewsList:List<Article>){
        newsList.clear()
        newsList.addAll(newNewsList)
        notifyDataSetChanged()
    }

}