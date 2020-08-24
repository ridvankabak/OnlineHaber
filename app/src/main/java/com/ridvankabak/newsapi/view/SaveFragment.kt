package com.ridvankabak.newsapi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.adapter.SavesAdapter
import com.ridvankabak.newsapi.service.ResultRoom
import com.ridvankabak.newsapi.service.ResultService
import com.ridvankabak.newsapi.viewmodel.SaveViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_save.*

class SaveFragment : Fragment() {

    private lateinit var viewmodel: SaveViewModel
    private lateinit var adapter: SavesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.e("onCreateView","Çalıştı")
        return inflater.inflate(R.layout.fragment_save, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(SaveViewModel::class.java)
        viewmodel.getData()

        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvSaves.layoutManager = manager
        adapter = SavesAdapter(arrayListOf())
        rvSaves.adapter = adapter
        observeLiveData()
        setListener()

        Log.e("onViewCreated","Çalıştı")
    }

    private fun observeLiveData() {
        viewmodel.result.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultRoom.GetNewsSuccess -> {
                    rvSaves.visibility = View.VISIBLE
                    adapter.newsListUptade(result.newses)
                }
                is ResultRoom.GetNewsFail -> {
                    if (result.e) {
                        rvSaves.visibility = View.GONE
                        linearlayoutNoNewsSaves.visibility = View.VISIBLE
                    } else {
                        linearlayoutNoNewsSaves.visibility = View.GONE
                    }
                }
                is ResultRoom.GetNewsLoading -> {
                    if (result.showLoading) {
                        rvSaves.visibility = View.GONE
                        linearlayoutNoNewsSaves.visibility = View.GONE
                        pbLoadingSaves.visibility = View.VISIBLE
                    } else {
                        pbLoadingSaves.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setListener(){
        imSavesExit.setOnClickListener {
            val count: Int = requireActivity().supportFragmentManager.backStackEntryCount
            if(count == 0){
                super.requireActivity().onBackPressed()
            }else{
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("onResume","Çalıştı")
    }
}
