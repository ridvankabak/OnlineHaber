package com.ridvankabak.newsapi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.model.Article
import com.ridvankabak.newsapi.model.News
import com.ridvankabak.newsapi.util.downloadImage
import com.ridvankabak.newsapi.util.makePlaceHolder
import com.ridvankabak.newsapi.view.SaveFragmentDirections
import kotlinx.android.synthetic.main.save_card_view.view.*

class SavesAdapter(var news:ArrayList<News>): RecyclerView.Adapter<SavesAdapter.ViewHolder>() {

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.save_card_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.ivSavesIcon.downloadImage(news[position].urlToImage, makePlaceHolder(holder.itemView.context))
        holder.itemView.tvSavesAuthor.text = news[position].author.toString()

        var title:String = news[position].title.toString()
        if(title.contains("-")){
            title = title.substring(0,title.lastIndexOf("-"))
        }
        holder.itemView.tvSavesTitle.text = title

        holder.itemView.cardViewSaves.setOnClickListener {view->
            val uuid = news[position].uuid
            uuid.let {
                Log.e("uuid",uuid.toString())
                val action = SaveFragmentDirections.actionSaveFragmentToDetailFragment(uuid)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    fun newsListUptade(newses: List<News>) {
        news.clear()
        news.addAll(newses)
        notifyDataSetChanged()
    }
}