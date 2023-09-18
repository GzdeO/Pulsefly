package com.ktln.kotlinpulse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ktln.kotlinpulse.databinding.MusicRecyclerRowBinding
import com.ktln.kotlinpulse.fragments.MusicListFragmentDirections
import com.ktln.kotlinpulse.model.Data
import com.ktln.kotlinpulse.utils.downloadImg

class MusicRecyclerAdapter(val musicList:ArrayList<Data>) : RecyclerView.Adapter<MusicRecyclerAdapter.MusicViewHolder>(){
    class MusicViewHolder(val binding: MusicRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding=MusicRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MusicViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.binding.albumName.text=musicList[position].album.title
        //Glide.with(holder.itemView).load(musicList[position].contributors[position].picture).into(holder.binding.albumImg)
        //Glide.with(holder.itemView).load(musicList[position].album.coverMedium).into(holder.binding.albumImg)

        holder.binding.albumImg.downloadImg(musicList[position].album.coverMedium)

        holder.itemView.setOnClickListener {
            val action=MusicListFragmentDirections.actionMusicListFragmentToMusicDetailFragment(musicList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }


    }

    fun musicListesiniGuncelle(yeniMusicList:List<Data>){
        musicList.clear()
        musicList.addAll(yeniMusicList)
        notifyDataSetChanged()
    }
}

//android:defaultValue="0"