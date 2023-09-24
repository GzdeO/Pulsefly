package com.ktln.kotlinpulse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ktln.kotlinpulse.R
import com.ktln.kotlinpulse.adapter.SearchRecyclerViewAdapter
import com.ktln.kotlinpulse.databinding.FragmentMusicListBinding
import com.ktln.kotlinpulse.databinding.FragmentSearchBinding
import com.ktln.kotlinpulse.model.search.Data
import com.ktln.kotlinpulse.model.search.SearchResponse
import com.ktln.kotlinpulse.viewModel.MusicListViewModel
import com.ktln.kotlinpulse.viewModel.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var searchedViewModel: SearchViewModel
    private lateinit var searchRecyclerViewAdapter : SearchRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchedViewModel=ViewModelProvider(this)[SearchViewModel::class.java]  // hata alırsan, bu kısmı onViewCreated içine al.

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        prepareSearchRecyclerView()

        binding.searchImg.setOnClickListener { searchMusics() }

        observeSearchedMusicsLiveData()


        var searchJob:Job?=null
        binding.searchEditText.addTextChangedListener {trackName->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(100)
                searchedViewModel.verileriInternettenAl(trackName.toString())

            }
        }

    }

    private fun searchMusics() {
        val trackName=binding.searchEditText.text.toString()
        if (trackName.isNotEmpty()){
            searchedViewModel.refreshData(trackName)
        }
    }

    private fun prepareSearchRecyclerView() {
        searchRecyclerViewAdapter= SearchRecyclerViewAdapter()
        binding.searchRV.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=searchRecyclerViewAdapter
        }
    }


    fun observeSearchedMusicsLiveData(){
        searchedViewModel.searchedMusicLiveData.observe(viewLifecycleOwner, Observer {musicList->
            searchRecyclerViewAdapter.differ.submitList(musicList)
        })
    }

    override fun onResume() {
        super.onResume()
        binding.searchEditText.text.clear()
    }

}