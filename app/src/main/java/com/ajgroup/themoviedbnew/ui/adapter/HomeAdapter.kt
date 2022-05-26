package com.ajgroup.themoviedbnew.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.ajgroup.themoviedbnew.data.api.model.Result
import com.ajgroup.themoviedbnew.databinding.ItemContentBinding
import com.bumptech.glide.Glide

class HomeAdapter(private val onClick:(Result)->Unit)
    : ListAdapter<Result, HomeAdapter.ViewHolder>(ResultComparator()) {
    class ViewHolder(private val binding: ItemContentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            currentResult: Result,
            onClick: (Result) -> Unit) {
            binding.apply {
                tvJudul.text = currentResult.title
                tvRelease.text = currentResult.releaseDate
                tvPopularity.text = currentResult.popularity.toString()
                tvVote.text = currentResult.voteAverage.toString()
                Glide.with(binding.ivHeader)
                    .load("https://image.tmdb.org/t/p/w500"+currentResult.posterPath)
                    .into(ivHeader)
                root.setOnClickListener {
                    onClick(currentResult)
                }
            }
        }
    }
    class ResultComparator : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContentBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}