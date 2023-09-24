package com.ktln.kotlinpulse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ktln.kotlinpulse.databinding.TracksRecyclerRowBinding
import com.ktln.kotlinpulse.fragments.MusicListFragmentDirections
import com.ktln.kotlinpulse.model.chartTracks.Data
import com.ktln.kotlinpulse.utils.downloadImg


class TracksRecyclerAdapter(val trackList:ArrayList<Data>) : RecyclerView.Adapter<TracksRecyclerAdapter.TrackViewHolder>(){
    class TrackViewHolder(val binding: TracksRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding=TracksRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrackViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.binding.artistNameTv.text=trackList[position].artist.name
        holder.binding.trackNameTv.text=trackList[position].title

        //Glide.with(holder.itemView).load(musicList[position].contributors[position].picture).into(holder.binding.albumImg)
        //Glide.with(holder.itemView).load(musicList[position].album.coverMedium).into(holder.binding.albumImg)

        holder.binding.trackImg.downloadImg(trackList[position].album.coverMedium)

        holder.itemView.setOnClickListener {
            val action=MusicListFragmentDirections.actionMusicListFragmentToTrackDetailFragment(trackList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }


    }

    fun trackListesiniGuncelle(yeniTrackList:List<Data>){
        trackList.clear()
        trackList.addAll(yeniTrackList)
        notifyDataSetChanged()
    }
}

//android:defaultValue="0"