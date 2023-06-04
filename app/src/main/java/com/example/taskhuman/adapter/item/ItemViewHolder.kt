package com.example.taskhuman.adapter.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.taskhuman.R
import com.example.taskhuman.data.Skill
import com.example.taskhuman.utils.ImageLoader
import com.google.android.material.imageview.ShapeableImageView
import java.lang.ref.WeakReference

class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)
    private lateinit var itemHeading: TextView
    private lateinit var provider1: ShapeableImageView
    private lateinit var provider2: ShapeableImageView
    private lateinit var provider3: ShapeableImageView
    private lateinit var provider4: ShapeableImageView
    private lateinit var heart: ImageView
    private lateinit var heartTick: ImageView
    private lateinit var favouriteText: TextView
    private lateinit var heartLayout: ConstraintLayout
    private lateinit var dot: ImageView

    var itemData: Skill? = null

    var onFavouriteClick: ((RecyclerView.ViewHolder) -> Unit)? = null

    init {
        view.get()?.let {
            it.setOnClickListener {
                if(view.get()?.scrollX != 0) {
                    view.get()?.scrollTo(0,0)
                }
            }
            itemHeading = it.findViewById(R.id.itemHeading)
            provider1 = it.findViewById(R.id.provider1)
            provider2 = it.findViewById(R.id.provider2)
            provider3 = it.findViewById(R.id.provider3)
            provider4 = it.findViewById(R.id.provider4)
            heart = it.findViewById(R.id.heart)
            heartTick = it.findViewById(R.id.heartTick)
            favouriteText = it.findViewById(R.id.favouriteText)
            heartLayout = it.findViewById(R.id.heartLayout)
            dot = it.findViewById(R.id.dot)

            heartLayout.setOnClickListener {
                onFavouriteClick?.let {onFavouriteClick ->
                    onFavouriteClick(this)
                }
            }
        }
    }

    fun updateFavourite(favorite: Boolean) {
        if(!favorite) {
            heart.setImageDrawable(heart.context.getDrawable(R.drawable.heart))
            heartTick.setImageDrawable(heartTick.context.getDrawable(R.drawable.tick))
            heartTick.visibility = View.VISIBLE
            favouriteText.text = favouriteText.context.getString(R.string.added)
        } else {
            favouriteText.text = favouriteText.context.getString(R.string.add_to_favourite)
            heartTick.visibility = View.GONE
            heart.setImageDrawable(heart.context.getDrawable(R.drawable.heart))
        }
    }

    fun updateView() {
        view.get()?.scrollTo(0,0)
        itemData?.run {
            itemHeading.text = tileName
            if(!providerInfo.isNullOrEmpty()) {
                if (providerInfo.size > 0) {
                    provider1.visibility = View.VISIBLE
                    ImageLoader.loadImage(provider1, providerInfo[0].profileImage)
                }
                if (providerInfo.size > 1) {
                    provider2.visibility = View.VISIBLE
                    ImageLoader.loadImage(provider2, providerInfo[1].profileImage)
                }
                if (providerInfo.size > 2) {
                    provider3.visibility = View.VISIBLE
                    ImageLoader.loadImage(provider3, providerInfo[2].profileImage)
                }
                if (providerInfo.size > 3) {
                    provider4.visibility = View.VISIBLE
                    ImageLoader.loadImage(provider4, providerInfo[3].profileImage)
                }
            }

            if(isFavorite) {
                heart.setImageDrawable(heart.context.getDrawable(R.drawable.heart_cross))
                heartTick.visibility = View.GONE
                favouriteText.text = favouriteText.context.getString(R.string.remove_favourite)
            } else {
                favouriteText.text = favouriteText.context.getString(R.string.add_to_favourite)
                heart.setImageDrawable(heart.context.getDrawable(R.drawable.heart))
            }

            when(availability?.status) {
                1 -> {
                    dot.visibility = View.VISIBLE
                    dot.setImageDrawable(dot.context.getDrawable(R.drawable.green_dot))
                }
                2 -> {
                    dot.visibility = View.VISIBLE
                    dot.setImageDrawable(dot.context.getDrawable(R.drawable.grey_dot))
                }
                3 -> {
                    dot.visibility = View.VISIBLE
                    dot.setImageDrawable(dot.context.getDrawable(R.drawable.yellow_dot))
                }
            }
        }

    }
}