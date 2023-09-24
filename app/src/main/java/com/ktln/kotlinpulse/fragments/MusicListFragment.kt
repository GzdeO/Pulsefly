package com.ktln.kotlinpulse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ktln.kotlinpulse.R
import com.ktln.kotlinpulse.adapter.MusicRecyclerAdapter
import com.ktln.kotlinpulse.adapter.TracksRecyclerAdapter
import com.ktln.kotlinpulse.databinding.FragmentMusicListBinding
import com.ktln.kotlinpulse.viewModel.MusicListViewModel
import com.ktln.kotlinpulse.viewModel.TracksListViewModel


class MusicListFragment : Fragment() {
    private lateinit var binding: FragmentMusicListBinding

    private lateinit var viewModel: MusicListViewModel
    private val recyclerMusicAdapter = MusicRecyclerAdapter(arrayListOf())

    private lateinit var tracksListViewModel : TracksListViewModel
    private val tracksRecyclerAdapter=TracksRecyclerAdapter(arrayListOf())



    private var artistId = 4011
    private var trackId="0"

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
            topAlbumsRV.layoutManager=LinearLayoutManager(context)
            topAlbumsRV.adapter=recyclerMusicAdapter
            topAlbumsRV.set3DItem(true)
            topAlbumsRV.setInfinite(true)
        }


        tracksListViewModel=ViewModelProvider(this)[TracksListViewModel::class.java]
        tracksListViewModel.refreshTrackData(trackId)

        binding.apply {
            tracksRV.layoutManager=LinearLayoutManager(context)
            tracksRV.adapter=tracksRecyclerAdapter
        }





        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                prgBarPulse.visibility=View.VISIBLE
                errorAnimation.visibility=View.GONE
                topAlbumsRV.visibility=View.GONE
                tracksRV.visibility=View.GONE
                viewModel.refreshFromInternet(artistId)
                tracksListViewModel.refreshFromInternet(trackId)
                swipeRefreshLayout.isRefreshing=false
            }
        }

        observeLiveData()
        observeTrackLiveData()

        binding.searchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_musicListFragment_to_searchFragment)
        }

    }





    fun observeLiveData(){
        viewModel.musics.observe(viewLifecycleOwner, Observer {music->
            music?.let {
                binding.apply {
                    topAlbumsRV.visibility=View.VISIBLE
                    tracksRV.visibility=View.VISIBLE
                    lineAnimation.visibility=View.VISIBLE
                }
                recyclerMusicAdapter.musicListesiniGuncelle(music)
            }
        })

        viewModel.musicLoading.observe(viewLifecycleOwner, Observer {loading->
            loading?.let {
                if (it){
                    binding.apply {
                        prgBarPulse.visibility=View.VISIBLE
                        errorAnimation.visibility=View.GONE
                        topAlbumsRV.visibility=View.GONE
                        lineAnimation.visibility=View.GONE
                        tracksRV.visibility=View.GONE
                    }
                }else{
                    binding.prgBarPulse.visibility=View.GONE
                }
            }
        })

        viewModel.musicError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if (it){
                    binding.apply {
                        errorAnimation.visibility=View.VISIBLE
                        prgBarPulse.visibility=View.GONE
                        topAlbumsRV.visibility=View.GONE
                        lineAnimation.visibility=View.GONE
                        tracksRV.visibility=View.GONE
                    }
                }else{
                    binding.errorAnimation.visibility=View.GONE
                }
            }
        })
    }


    private fun observeTrackLiveData() {
        tracksListViewModel.tracks.observe(viewLifecycleOwner, Observer { tracks->
            tracks?.let {

               binding.apply {
                   tracksRV.visibility=View.VISIBLE
                   topAlbumsRV.visibility=View.VISIBLE
                   lineAnimation.visibility=View.VISIBLE
               }
                tracksRecyclerAdapter.trackListesiniGuncelle(tracks)
            }

        })

        tracksListViewModel.trackError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if (it){
                    binding.apply {
                        errorAnimation.visibility=View.VISIBLE
                        prgBarPulse.visibility=View.GONE
                        topAlbumsRV.visibility=View.GONE
                        lineAnimation.visibility=View.GONE
                        tracksRV.visibility=View.GONE

                    }
                }else{
                    binding.errorAnimation.visibility=View.GONE
                }
            }
        })

        tracksListViewModel.trackLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it){
                    binding.apply {
                        prgBarPulse.visibility=View.VISIBLE
                        errorAnimation.visibility=View.GONE
                        topAlbumsRV.visibility=View.GONE
                        lineAnimation.visibility=View.GONE
                        tracksRV.visibility=View.GONE
                    }
                }else{
                    binding.prgBarPulse.visibility=View.GONE
                }
            }
        })
    }

}

