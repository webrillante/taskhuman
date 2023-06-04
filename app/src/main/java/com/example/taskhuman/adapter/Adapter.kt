package com.example.taskhuman.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskhuman.R
import com.example.taskhuman.adapter.item.ItemViewHolder
import com.example.taskhuman.data.FavouriteResponse
import com.example.taskhuman.data.Skill
import kotlinx.coroutines.*

class Adapter(): RecyclerView.Adapter<ItemViewHolder>() {

    private var list = mutableListOf<Skill>()
    private var listener: FavouriteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemData = list[position]
        holder.onFavouriteClick = {
            holder.updateFavourite(list[position].isFavorite)
            if(!list[position].isFavorite) {
                listener?.addFavourite(list[position].tileName, list[position].dictionaryName, position)
            } else {
                listener?.removeFavourite(list[position].tileName, position)
            }
        }
        holder.updateView()
    }

    fun reload(list: List<Skill>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setListener(listener: FavouriteListener) {
        this.listener = listener
    }

    fun setAddedFavourite(position: Int) {
        list[position].isFavorite = true
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                notifyItemChanged(position)
            }
        }

    }

    fun setRemoveFavourite(position: Int) {
        list[position].isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                notifyItemChanged(position)
            }
        }

    }

    interface FavouriteListener {
        fun addFavourite(tileName: String, dictionaryName: String?, position: Int)
        fun removeFavourite(tileName: String, position: Int)
    }
}