package com.example.submissionbfaa.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.submissionbfaa.R
import com.example.submissionbfaa.data.local.entity.UserEntity
import com.example.submissionbfaa.databinding.ItemListHomeBinding
import com.example.submissionbfaa.ui.UserAdapter.UserHolder

class UserAdapter : ListAdapter<UserEntity, UserHolder>(DIFF_CALLBACK) {

    inner class UserHolder(val binding: ItemListHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(userGithub: UserEntity){
                binding.apply {
                    textUser.text = userGithub.login

                    userCl.load(userGithub.avatarUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_person_24)
                    }

                    cardItem.setOnClickListener {
                        root.context.startActivity(Intent(root.context,DetailActivity::class.java))
                    }
                }
            }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> =
            object : DiffUtil.ItemCallback<UserEntity>() {
                override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem.login == newItem.login
                }

                override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding =
            ItemListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
       val user = getItem(position)
        holder.bind(user)
    }
}