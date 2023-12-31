package com.ktln.kotlinpulse.fragments

import android.animation.ObjectAnimator
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ktln.kotlinpulse.databinding.FragmentMusicDetailBinding
import com.ktln.kotlinpulse.utils.downloadImg
import com.ktln.kotlinpulse.viewModel.MusicDetailViewModel


class MusicDetailFragment : Fragment() {

    private lateinit var binding: FragmentMusicDetailBinding

    private var mediaPlayer: MediaPlayer? = null

    private lateinit var rotationAnimator: ObjectAnimator

    private lateinit var viewModel:MusicDetailViewModel
    private var artistId=96078


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            artistId= MusicDetailFragmentArgs.fromBundle(it).artistId
            println("music Id: $artistId")
        }

        viewModel=ViewModelProvider(this)[MusicDetailViewModel::class.java]
        viewModel.roomVerisiniAl(artistId)



        observeLiveData()




    }

    fun observeLiveData(){
        viewModel.musicDetailLiveData.observe(viewLifecycleOwner, Observer { music->
            music?.let {
                binding.apply {
                    albumName.text=music.album.title
                    artistName.text=music.artist.name
                    songName.text=music.title

                    albumImg.downloadImg(music.album.coverMedium)

                    rotationAnimator = ObjectAnimator.ofFloat(albumImgCardContainer, "rotation", 0f, 360f)
                    rotationAnimator.repeatCount = ObjectAnimator.INFINITE
                    rotationAnimator.duration = 5000 // Bir dönüş için geçen süre (milisaniye)
                    rotationAnimator.interpolator = LinearInterpolator()


                    mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                        )

                        setDataSource(music.preview)
                        prepare()

                    }

                    playButton.setOnClickListener {
                        playButton.visibility=View.GONE
                        pauseButton.visibility=View.VISIBLE
                        mediaPlayer?.start()
                        rotationAnimator.start()

                    }


                    pauseButton.setOnClickListener {
                        pauseButton.visibility=View.GONE
                        playButton.visibility=View.VISIBLE
                        mediaPlayer?.pause()
                        rotationAnimator.pause()
                    }


                    mediaPlayer?.setOnCompletionListener {

                        Handler().postDelayed({
                            rotationAnimator.end()
                        }, music.duration.toLong())

                        rotationAnimator.end()
                        albumImgCardContainer.rotation = 0f

                        binding.pauseButton.visibility = View.GONE
                        binding.playButton.visibility = View.VISIBLE
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




    override fun onDestroy() {
        super.onDestroy()
        rotationAnimator.end()
        mediaPlayer?.stop()
        mediaPlayer?.release()

    }


}