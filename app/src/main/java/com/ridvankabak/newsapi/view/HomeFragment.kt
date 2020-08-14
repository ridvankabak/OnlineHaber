package com.ridvankabak.newsapi.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.adapter.NewsRecyclerAdapter
import com.ridvankabak.newsapi.common.CountryBottomSheetDialog
import com.ridvankabak.newsapi.common.LanguageBottomSheetDialog
import com.ridvankabak.newsapi.model.Country
import com.ridvankabak.newsapi.model.Language
import com.ridvankabak.newsapi.viewmodel.HomeViewModel
import com.ridvankabak.newsapi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(),CountryBottomSheetDialog.BSheetCountryListener,LanguageBottomSheetDialog.BSheetLanguageListener {

    private lateinit var viewModel : HomeViewModel
    private lateinit var mainViewModel:MainViewModel
    private val recyclerNewsAdapter = NewsRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewModel.refreshData()

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

        relativeLayoutSearch.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            Navigation.findNavController(it).navigate(action)
        }

        relativeLayoutFilter.setOnClickListener {
            linearLayoutChoose.visibility = View.GONE
            linearLayoutFilter.visibility = View.VISIBLE
        }

        relativeLayoutFilterAll.setOnClickListener {
            linearLayoutChoose.visibility = View.VISIBLE
            linearLayoutFilter.visibility = View.GONE
        }

        relativeLayoutLanguage.setOnClickListener {
            val dialog = LanguageBottomSheetDialog(Language.SupplierLanguage.language,this)
            dialog.show(parentFragmentManager, "dilBottomSheet")
        }

        relativeLayoutCountry.setOnClickListener {
            val dialog = CountryBottomSheetDialog(Country.SupplierCountry.country,this)
            dialog.show(parentFragmentManager, "ulkeBottomSheet")
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

        mainViewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
            viewModel.searchData(it)
        })


    }

    override fun onClickedCountry(countryText: String) {
        Log.e("country",countryText)
        viewModel.getBottomCountry(countryText)
    }

    override fun onClickedLanguage(languageText: String) {
        Log.e("language",languageText)
        viewModel.getBottomLanguage(languageText)
    }


}
