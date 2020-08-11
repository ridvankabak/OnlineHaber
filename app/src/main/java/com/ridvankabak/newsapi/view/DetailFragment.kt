package com.ridvankabak.newsapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {
    private lateinit var viewModel:DetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var uuid = arguments?.let { DetailFragmentArgs.fromBundle(it).uuid }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        uuid?.let { viewModel.roomGetData(it) }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { data ->

            data?.let {
                webView.loadUrl(data.url)
            }
        })
    }
}
