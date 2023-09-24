package com.ktln.kotlinpulse.fragments

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ktln.kotlinpulse.R
import com.ktln.kotlinpulse.databinding.FragmentMusicListBinding
import com.ktln.kotlinpulse.databinding.FragmentTrackDetailBinding
import com.ktln.kotlinpulse.utils.downloadImg
import com.ktln.kotlinpulse.viewModel.TrackDetailViewModel

class TrackDetailFragment : Fragment() {

    private lateinit var binding: FragmentTrackDetailBinding

    private var mediaPlayer: MediaPlayer? = null

    private lateinit var trackDetailViewModel: TrackDetailViewModel

    private var trackId=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            trackId=TrackDetailFragmentArgs.fromBundle(it).trackId
            println("track Id: $trackId")
        }

        trackDetailViewModel=ViewModelProvider(this)[TrackDetailViewModel::class.java]
        trackDetailViewModel.roomVerisiniAl(trackId)




        observeTrackDetailLiveData()
        binding.pulseAnimation.pauseAnimation()

    }

    fun observeTrackDetailLiveData(){
        trackDetailViewModel.trackDetailLiveData.observe(viewLifecycleOwner, Observer {track->
            track?.let {
                binding.apply {
                    songName.text=track.title
                    albumName.text=track.album.title
                    artistName.text=track.artist.name

                    binding.albumImg.downloadImg(track.album.coverMedium)


                    mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                        )

                        setDataSource(track.preview)
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
                        }, track.duration.toLong())

                        binding.pauseButton.visibility = View.GONE
                        binding.playButton.visibility = View.VISIBLE
                    }


                    forwardButton.setOnClickListener {
                        val currentPosition = mediaPlayer?.currentPosition ?: 0
                        val duration = mediaPlayer?.duration ?: 0
                        val forwardTime = 10000
                        val newPosition = currentPosition + forwardTime
                        if (newPosition < duration) {
                            mediaPlayer?.seekTo(newPosition)
                        }
                    }



                    rewindButton.setOnClickListener {
                        val currentPosition = mediaPlayer?.currentPosition ?: 0
                        val rewindTime = 10000
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
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}