package com.climus.climeet.presentation.ui.main.home.recycler.popularshorts

import android.R.color
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.climus.climeet.databinding.ItemPopularShortsBinding
import com.climus.climeet.presentation.ui.main.home.model.PopularShorts


class ShortsRVAdapter (private val shortsList: ArrayList<PopularShorts>) : RecyclerView.Adapter<ShortsRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsRVAdapter.ViewHolder {
        val binding: ItemPopularShortsBinding = ItemPopularShortsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortsRVAdapter.ViewHolder, position: Int) {
        holder.bind(shortsList[position])
    }

    override fun getItemCount(): Int = shortsList.size

    inner class ViewHolder(val binding: ItemPopularShortsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(shorts: PopularShorts){
            if(shorts.thumbnailImg != null) {
                Glide.with(binding.root.context)
                    .load(shorts.thumbnailImg)
                    .into(binding.ivShortsThumbnail)
            }
            binding.tvShortsCragName.text = shorts.cragName
            val circleColorCode = shorts.shortsCircle
            val circleColor: Int = Color.parseColor(circleColorCode)
            binding.ivPopularShortsCircle.setColorFilter(circleColor)

            binding.tvPopularShortsLevel.text = shorts.level
            val levelColorCode = shorts.levelColor
            val levelColor: Int = Color.parseColor(levelColorCode)
            val gradientDrawable = GradientDrawable()
            gradientDrawable.shape = GradientDrawable.OVAL
            gradientDrawable.setColor(Color.TRANSPARENT)
            gradientDrawable.setStroke(6, levelColor) // 테두리의 너비와 색상을 설정합니다.

            binding.tvPopularShortsLevel.background = gradientDrawable
            binding.tvPopularShortsLevel.setTextColor(levelColor)
        }
    }

}