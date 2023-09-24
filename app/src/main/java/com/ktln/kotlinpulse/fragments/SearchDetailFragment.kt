package com.ktln.kotlinpulse.fragments

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ktln.kotlinpulse.databinding.FragmentSearchDetailBinding
import com.ktln.kotlinpulse.utils.downloadImg
import com.ktln.kotlinpulse.viewModel.SearchDetailViewModel


class SearchDetailFragment : Fragment() {

    private lateinit var binding: FragmentSearchDetailBinding
    private lateinit var searchDetailViewModel : SearchDetailViewModel


    private var mediaPlayer: MediaPlayer? = null



    private var searchId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            searchId=SearchDetailFragmentArgs.fromBundle(it).searchId
            println("search Id: $searchId")
        }

        searchDetailViewModel=ViewModelProvider(this)[SearchDetailViewModel::class.java]
        searchDetailViewModel.roomVerisiniAl(searchId)

        observeSearchDetailLiveData()

        binding.pulseAnimation.pauseAnimation()
    }


    fun observeSearchDetailLiveData(){
        searchDetailViewModel.searchLiveData.observe(viewLifecycleOwner, Observer {search->
            search?.let {
                binding.apply {
                    albumName.text=search.album.title
                    artistName.text=search.artist.name
                    songName.text=search.title
                    albumImg.downloadImg(search.album.coverMedium)

                    mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                        )

                        setDataSource(search.preview)
                        prepare()

                    }

                    playButton.setOnClickListener {
                        playButton.visibility=View.GONE
                        pauseButton.visibility=View.VISIBLE
                        mediaPlayer?.start()
                        startAnimation()

                    }

                    pauseButton.setOnClickListener {
                        pauseButton.visibility=View.GONE
                        playButton.visibility=View.VISIBLE
                        mediaPlayer?.pause()
                        stopAnimation()

                    }

                    mediaPlayer?.setOnCompletionListener {
                        Handler().postDelayed({
                            stopAnimation()
                        }, search.duration.toLong())
                        pauseButton.visibility = View.GONE
                        playButton.visibility = View.VISIBLE
                    }


                    forwardButton.setOnClickListener {
                        val currentPosition = mediaPlayer?.currentPosition ?: 0
                        val duration = mediaPlayer?.duration ?: 0
                        val forwardTime = 10000 // İleri sar 10 saniye (örnek olarak)
                        val newPosition = currentPosition + forwardTime
                        if (newPosition < duration) {
                            mediaPlayer?.seekTo(newPosition)
                        }
                    }


                    rewindButton.setOnClickListener {
                        val currentPosition = mediaPlayer?.currentPosition ?: 0
                        val rewindTime = 10000 // Geri sar 10 saniye (örnek olarak)
                        val newPosition = currentPosition - rewindTime
                        if (newPosition >= 0) {
                            mediaPlayer?.seekTo(newPosition)
                        } else {
                            mediaPlayer?.seekTo(0)
                        }
                    }


                }

            }
        })
    }

    private fun startAnimation() {
        binding.pulseAnimation.playAnimation()
    }

    private fun stopAnimation() {
        binding.pulseAnimation.pauseAnimation()
        binding.pulseAnimation.progress = 0f
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()

    }


}