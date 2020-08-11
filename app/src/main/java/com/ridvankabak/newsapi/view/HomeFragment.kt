package com.ridvankabak.newsapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.adapter.NewsRecyclerAdapter
import com.ridvankabak.newsapi.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel : HomeViewModel
    private val recyclerNewsAdapter = NewsRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.refreshData()

        recyclerview_home.layoutManager = LinearLayoutManager(context)
        recyclerview_home.adapter = recyclerNewsAdapter

        setListener()

        observeLiveData()

    }

    fun setListener(){

        swipeRefreshLayout.setOnRefreshListener {
            pb_loading.visibility = View.VISIBLE
            linearlayoutNoNews.visibility = View.GONE
            recyclerview_home.visibility = View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing = false
        }


    }

    fun observeLiveData(){
        viewModel.news.observe(viewLifecycleOwner, Observer {news ->
            news?.let {
                recyclerview_home.visibility = View.VISIBLE
                recyclerNewsAdapter.newsListUptade(it)
            }

        })

        viewModel.newsErrorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it){
                    recyclerview_home.visibility = View.GONE
                    linearlayoutNoNews.visibility = View.VISIBLE
                }else{
                    linearlayoutNoNews.visibility = View.GONE
                }
            }
        })

        viewModel.newsLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    recyclerview_home.visibility = View.GONE
                    linearlayoutNoNews.visibility = View.GONE
                    pb_loading.visibility = View.VISIBLE
                }else{
                    pb_loading.visibility = View.GONE
                }
            }
        })


    }

}
