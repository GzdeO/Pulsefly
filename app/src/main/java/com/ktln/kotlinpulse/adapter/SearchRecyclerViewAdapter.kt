package com.ktln.kotlinpulse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ktln.kotlinpulse.databinding.SearchRecyclerRowBinding
import com.ktln.kotlinpulse.fragments.MusicListFragmentDirections
import com.ktln.kotlinpulse.fragments.SearchFragmentDirections
import com.ktln.kotlinpulse.model.search.Data
import com.ktln.kotlinpulse.utils.downloadImg

class SearchRecyclerViewAdapter : RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(val binding:SearchRecyclerRowBinding) : ViewHolder(binding.root)

    private val diffUtil= object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem==newItem
        }

    }

    val differ=AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(SearchRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val music=differ.currentList[position]
        holder.binding.artistNameTv.text=music.artist.name
        holder.binding.trackNameTv.text=music.title
        holder.binding.trackImg.downloadImg(music.album.coverMedium)


        holder.itemView.setOnClickListener {
            val action=SearchFragmentDirections.actionSearchFragmentToSearchDetailFragment(differ.currentList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }



    }

}

//android:defaultValue="0"