package com.ridvankabak.newsapi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ridvankabak.newsapi.BR
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.databinding.FragmentDetailBinding
import com.ridvankabak.newsapi.model.News
import com.ridvankabak.newsapi.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel:DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    var url: String = ""
    var news: News? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        url = arguments?.let { DetailFragmentArgs.fromBundle(it).url }.toString()

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        url.let { viewModel.roomGetData(it) }
        dataBinding.setVariable(BR.viewmodel,viewModel)

        observeLiveData()
        setListener()

    }

    private fun setListener(){
        imExit.setOnClickListener{
            val count: Int = requireActivity().supportFragmentManager.backStackEntryCount

            if(count == 0){
                super.requireActivity().onBackPressed()
            }else{
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        imSaves.setOnClickListener {
            if(news?.isSaved!=null){
                if(news?.isSaved == true){
                    //viewModel.updateData(0)
                    viewModel.deleteData()
                    imSaves.setImageResource(R.drawable.ic_saves_off)
                    Toast.makeText(context,"Kaydedilenlerden kaldırıldı",Toast.LENGTH_SHORT).show()
                }

            }else{
                Log.e("isSaved","null")
                viewModel.saveData()
                viewModel.updateData(1)
                imSaves.setImageResource(R.drawable.ic_saves_on)
                Toast.makeText(context,"Haber kaydedildi",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun observeLiveData() {

        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {data ->
                webView.loadUrl(data)
            }
        })

        viewModel.isSaved.observe(viewLifecycleOwner, Observer {
            if(it){
                imSaves.setImageResource(R.drawable.ic_saves_on)
            }
        })

        viewModel.saveNews.observe(viewLifecycleOwner, Observer {
            news = it
        })
    }
}
