package com.example.submissionbfaa.ui.detail_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.example.submissionbfaa.R
import com.example.submissionbfaa.data.UserViewModel
import com.example.submissionbfaa.data.ViewModelFactory
import com.example.submissionbfaa.data.local.entity.UserEntity
import com.example.submissionbfaa.databinding.ActivityDetailBinding
import com.example.submissionbfaa.ui.main_activity.MainActivity
import com.example.submissionbfaa.ui.main_activity.UserAdapter
import com.example.submissionbfaa.utils.Constant
import com.example.submissionbfaa.utils.Status
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {
    companion object {
        val TAG: String = DetailActivity::class.java.simpleName
        const val empty = "empty"
    }

    var name: String = ""

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra(UserAdapter.NAME)!!
        val userEntity = intent.getParcelableExtra<UserEntity>(UserAdapter.USER_ENTITY)

        val title = listOf(resources.getString(R.string.follower1), resources.getString(R.string.following1))

        binding.apply {
            groupDetail.visibility = View.GONE
            binding.pbLoading.visibility = View.VISIBLE
            setSupportActionBar(detailToolbar)
        }

        val factory: ViewModelFactory by inject()
        val userViewModel: UserViewModel by viewModels { factory }

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, name)
        binding.followVP.adapter = adapter
        TabLayoutMediator(binding.followTL, binding.followVP) { tab, position ->
            tab.text = title[position]
        }.attach()

        userEntity?.isMarked.let { status ->
            if (status != null) {
                setMarked(status)
                binding.btnFavorite.setOnClickListener {
                    if (status){
                        userEntity?.let { it1 -> userViewModel.deleteUser(it1) }
                        Toast.makeText(this@DetailActivity, getString(R.string.berhasil_dihapus_dari_favorite), Toast.LENGTH_SHORT).show()
                    }else{
                        userEntity?.let { it1 -> userViewModel.saveUser(it1) }
                        Toast.makeText(this@DetailActivity, getString(R.string.berhasil_ditambahkan_dari_favorite), Toast.LENGTH_SHORT).show()
                    }
                    startActivity(Intent(this@DetailActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }

        }

        userViewModel.getDetailUser(name).observe(this) { status ->
            if (status != null) {
                when (status) {
                    is Status.Loading -> {
                        binding.groupDetail.visibility = View.GONE
                        binding.pbLoading.visibility = View.VISIBLE
                    }

                    is Status.Success -> {
                        binding.groupDetail.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE

                        val data = status.data

                        binding.apply {

                            userGithubIv.load(data.avatarUrl) {
                                crossfade(true)
                                placeholder(R.drawable.ic_person_24)
                            }
                            nameTv.text = data.name ?: empty
                            usernameTv.text = data.login ?: empty
                            followerTv.text =
                                getString(R.string.follower, data.followers.toString())
                            locationTv.text = data.location ?: empty
                            textCompany.text = data.company ?: empty
                            followingTv.text =
                                getString(R.string.following, data.following.toString())
                        }
                    }

                    is Status.Error -> {
                        binding.groupDetail.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE
                        Log.e(TAG, status.error)
                    }
                }
            }
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    private fun setMarked(isMark: Boolean){
        if (isMark){
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_favorite_24
                )
            )
        }else{
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_favorite_border_24
                )
            )
        }
    }



}