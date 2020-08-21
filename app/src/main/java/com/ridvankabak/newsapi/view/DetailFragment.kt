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
import com.ridvankabak.newsapi.R
import com.ridvankabak.newsapi.databinding.FragmentDetailBinding
import com.ridvankabak.newsapi.model.News
import com.ridvankabak.newsapi.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel:DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    var uuid:Int? = null
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

        uuid = arguments?.let { DetailFragmentArgs.fromBundle(it).uuid }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        uuid?.let { viewModel.roomGetData(it) }

        setListener()
        observeLiveData()

    }

    private fun setListener(){
        imExit.setOnClickListener{
            //viewModel.deleteData()
            val count: Int = requireActivity().supportFragmentManager.backStackEntryCount

            if(count == 0){
                super.requireActivity().onBackPressed()
            }else{
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        imSaves.setOnClickListener {
            Log.e("durum",news?.isSaved.toString())
            if(news?.isSaved == false || viewModel.news?.isSaved == false){
                viewModel.saveData()
                imSaves.setImageResource(R.drawable.ic_saves_on)
                Toast.makeText(context,"Haber kaydedildi",Toast.LENGTH_SHORT).show()
                viewModel.updateData(1)
            }else{
                viewModel.updateData(0)
                //viewModel.deleteData()
                imSaves.setImageResource(R.drawable.ic_saves_off)
                Toast.makeText(context,"Kaydedilenlerden kaldırıldı",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun observeLiveData() {
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                dataBinding.news = data
            }
        })

        viewModel.newsSaveLiveData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                news=data
                Log.e("isSaveLiveData",data.isSaved.toString())
                if(data.isSaved){
                    imSaves.setImageResource(R.drawable.ic_saves_on)
                }
                webView.loadUrl(data.url)
            }
        })
    }
}
