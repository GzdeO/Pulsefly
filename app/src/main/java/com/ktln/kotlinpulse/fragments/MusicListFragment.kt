package com.ktln.kotlinpulse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ktln.kotlinpulse.adapter.MusicRecyclerAdapter
import com.ktln.kotlinpulse.databinding.FragmentMusicListBinding
import com.ktln.kotlinpulse.viewModel.MusicListViewModel


class MusicListFragment : Fragment() {
    private lateinit var binding: FragmentMusicListBinding

    private lateinit var viewModel: MusicListViewModel
    private val recyclerMusicAdapter = MusicRecyclerAdapter(arrayListOf())



    private var artistId = 96078




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)






    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicListBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(this)[MusicListViewModel::class.java]
        viewModel.refreshData(artistId)

        binding.apply {
            topAlbumsRV.layoutManager= LinearLayoutManager(context)
            topAlbumsRV.adapter=recyclerMusicAdapter
            topAlbumsRV.set3DItem(true)
            topAlbumsRV.setInfinite(true)
        }

        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                prgBarPulse.visibility=View.VISIBLE
                errorAnimation.visibility=View.GONE
                topAlbumsRV.visibility=View.GONE
                viewModel.refreshFromInternet(artistId)
                swipeRefreshLayout.isRefreshing=false
            }
        }



        observeLiveData()





    }



    fun observeLiveData(){
        viewModel.musics.observe(viewLifecycleOwner, Observer {music->
            music?.let {
                binding.topAlbumsRV.visibility=View.VISIBLE
                binding.lineAnimation.visibility=View.VISIBLE
                recyclerMusicAdapter.musicListesiniGuncelle(music)
            }
        })

        viewModel.musicLoading.observe(viewLifecycleOwner, Observer {loading->
            loading?.let {
                if (it){
                    binding.prgBarPulse.visibility=View.VISIBLE
                    binding.errorAnimation.visibility=View.GONE
                    binding.topAlbumsRV.visibility=View.GONE
                    binding.lineAnimation.visibility=View.GONE
                }else{
                    binding.prgBarPulse.visibility=View.GONE
                }
            }
        })

        viewModel.musicError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if (it){
                    binding.errorAnimation.visibility=View.VISIBLE
                    binding.topAlbumsRV.visibility=View.GONE
                    binding.lineAnimation.visibility=View.GONE
                }else{
                    binding.errorAnimation.visibility=View.GONE
                }
            }
        })
    }
}