package com.example.submissionbfaa.ui.detail_activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.submissionbfaa.R
import com.example.submissionbfaa.data.remote.model.Follower
import com.example.submissionbfaa.databinding.ItemListHomeBinding

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.FollowViewModel>() {

    private var _followers = arrayListOf<Follower>()

    inner class FollowViewModel(private val binding: ItemListHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Follower) {
            binding.apply {
                textUser.text = data.login

                userCl.load(data.avaterUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_person_24)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(follows: ArrayList<Follower>) {
        _followers.clear()
        follows.forEach {
            if (it != null) {
                add(it)
            }
        }
        notifyDataSetChanged()
    }

    private fun add(follower: Follower) {
        _followers.add(follower)
        notifyItemInserted(_followers.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewModel {
        val itemBinding =
            ItemListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FollowViewModel(itemBinding)
    }

    override fun onBindViewHolder(holder: FollowViewModel, position: Int) {
        holder.bind(_followers[position])
    }

    override fun getItemCount(): Int = _followers.size
}