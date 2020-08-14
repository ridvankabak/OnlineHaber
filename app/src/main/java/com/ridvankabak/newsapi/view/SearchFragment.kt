package com.ridvankabak.newsapi.view

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.adapter.ExpandableListAdapter
import com.ridvankabak.newsapi.adapter.LanguageAdapterSearch
import com.ridvankabak.newsapi.databinding.FragmentSearchBinding
import com.ridvankabak.newsapi.model.Search
import com.ridvankabak.newsapi.viewmodel.HomeViewModel
import com.ridvankabak.newsapi.viewmodel.MainViewModel
import com.ridvankabak.newsapi.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

class SearchFragment : Fragment(),LanguageAdapterSearch.FlagClickListener {
    private lateinit var viewModel: SearchViewModel
    private val expandableAdapter =
        context?.let { ExpandableListAdapter(arrayListOf(), hashMapOf()) }
    private val recyclerAdapterLanguage = LanguageAdapterSearch(arrayListOf(),this)

    private lateinit var mainViewModel: MainViewModel

    private var search = Search()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.getData()

        setListener()
        observerLiveData()

        recyclerviewSearchLanguage.layoutManager = GridLayoutManager(activity, 4)
        recyclerviewSearchLanguage.setHasFixedSize(true)
        recyclerviewSearchLanguage.adapter = recyclerAdapterLanguage

        expandableListView.setAdapter(expandableAdapter)

    }

    private fun observerLiveData() {
        viewModel.header.observe(viewLifecycleOwner, Observer { header ->
            expandableAdapter?.addHeaderList(header)
        })

        viewModel.hashValue.observe(viewLifecycleOwner, Observer {
            expandableAdapter?.addHashMap(it)
        })

        viewModel.flags.observe(viewLifecycleOwner, Observer {
            recyclerAdapterLanguage?.flagListUptade(it)
        })

        viewModel.globalDateFrom.observe(viewLifecycleOwner, Observer {
            editTextDateFrom.setText(it)
            search.from = it
        })

        viewModel.globalDateTo.observe(viewLifecycleOwner, Observer {
            editTextDateTo.setText(it)
            search.to = it
        })

    }

    private fun setListener() {
        buttonSearch?.setOnClickListener { view ->
            if(getData()){
                val action = SearchFragmentDirections.actionSearchFragmentToHomeFragment()
                Navigation.findNavController(view).navigate(action)
            }
        }

        expandableListView.setOnGroupExpandListener {
            expandableListView.layoutParams.height = 350
        }

        expandableListView.setOnGroupCollapseListener {
            expandableListView.layoutParams.height = 100
        }

        editTextDateTo?.setOnClickListener {

            context?.let { c ->
                DatePickerDialog(
                    c, viewModel.dateTo,
                    viewModel.myCalendar.get(Calendar.YEAR),
                    viewModel.myCalendar.get(Calendar.MONTH),
                    viewModel.myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        }

        editTextDateFrom?.setOnClickListener {

            context?.let { c ->
                DatePickerDialog(
                    c, viewModel.dateFrom,
                    viewModel.myCalendar.get(Calendar.YEAR),
                    viewModel.myCalendar.get(Calendar.MONTH),
                    viewModel.myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    private fun getData():Boolean {
        //val search = Search("title","content","tr","12-12-2020","21-2-2020","sort")
        search.title = editTextTitleSearch.text.toString()
        search.content = editTextContentSearch.text.toString()
        if(search.content.isNullOrEmpty()){
            Toast.makeText(context,"Araman yapmak için istenilen alan doldurulmalı",Toast.LENGTH_LONG).show()
            return false
        }else{
            mainViewModel.updateSearchValue(search)
            return true
        }
    }

    override fun onClickListener(title: String?, position: Int?) {
        search.language = title
    }
}
