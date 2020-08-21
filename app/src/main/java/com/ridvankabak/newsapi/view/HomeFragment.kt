package com.ridvankabak.newsapi.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.appbar.AppBarLayout
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.adapter.NewsClickListener
import com.ridvankabak.newsapi.adapter.NewsRecyclerAdapter
import com.ridvankabak.newsapi.common.CountryBottomSheetDialog
import com.ridvankabak.newsapi.common.LanguageBottomSheetDialog
import com.ridvankabak.newsapi.model.Country
import com.ridvankabak.newsapi.model.Language
import com.ridvankabak.newsapi.service.ResultService
import com.ridvankabak.newsapi.viewmodel.HomeViewModel
import com.ridvankabak.newsapi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.card_view.view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), CountryBottomSheetDialog.BSheetCountryListener,
    LanguageBottomSheetDialog.BSheetLanguageListener, NewsClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel
    private val recyclerNewsAdapter = NewsRecyclerAdapter(this, arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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

    fun setListener() {

        swipeRefreshLayout.setOnRefreshListener {
            pb_loading.visibility = View.VISIBLE
            linearlayoutNoNews.visibility = View.GONE
            recyclerview_home.visibility = View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing = false
        }

        constraintLayoutSearch.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            Navigation.findNavController(it).navigate(action)
        }

        constraintLayoutFilter.setOnClickListener {

            if (linearLayoutFilter.visibility == View.GONE) {
                constraintLayoutSearch.visibility = View.GONE
                appbarhome.layoutParams.height = AppBarLayout.LayoutParams.WRAP_CONTENT
                val param = constraintLayoutFilter.layoutParams as LinearLayout.LayoutParams
                param.weight = 10.0f
                constraintLayoutFilter.layoutParams = param
                linearLayoutFilter.visibility = View.VISIBLE
            } else {
                linearLayoutFilter.visibility = View.GONE
                val param = constraintLayoutFilter.layoutParams as LinearLayout.LayoutParams
                param.weight = 5.0f
                constraintLayoutFilter.layoutParams = param
                constraintLayoutSearch.visibility = View.VISIBLE
                appbarhome.layoutParams.height = 150
            }

        }

        constraintLayoutLanguage.setOnClickListener {
            val dialog = LanguageBottomSheetDialog(Language.SupplierLanguage.language, this)
            dialog.show(parentFragmentManager, "dilBottomSheet")
        }

        constraintLayoutCountry.setOnClickListener {
            val dialog = CountryBottomSheetDialog(Country.SupplierCountry.country, this)
            dialog.show(parentFragmentManager, "ulkeBottomSheet")
        }

        floatingActionButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSaveFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    fun observeLiveData() {

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->

            when (result) {

                is ResultService.GetNewsSuccess -> {
                    recyclerview_home.visibility = View.VISIBLE
                    recyclerNewsAdapter.newsListUptade(result.newses)
                }
                is ResultService.GetNewsFail -> {
                    if (result.e) {
                        recyclerview_home.visibility = View.GONE
                        linearlayoutNoNews.visibility = View.VISIBLE
                    } else {
                        linearlayoutNoNews.visibility = View.GONE
                    }
                }
                is ResultService.GetNewsLoading -> {
                    if (result.showLoading) {
                        recyclerview_home.visibility = View.GONE
                        linearlayoutNoNews.visibility = View.GONE
                        pb_loading.visibility = View.VISIBLE
                    } else {
                        pb_loading.visibility = View.GONE
                    }
                }
            }

        })

        mainViewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
            viewModel.searchData(it)
        })


    }

    override fun onClickedCountry(countryText: String) {
        Log.e("country", countryText)
        viewModel.getBottomCountry(countryText)
    }

    override fun onClickedLanguage(languageText: String) {
        Log.e("language", languageText)
        viewModel.getBottomLanguage(languageText)
    }

    override fun onClickListener(view: View) {
        val uuid = view.uuid_card.text.toString().toIntOrNull()
        uuid?.let {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }
}
