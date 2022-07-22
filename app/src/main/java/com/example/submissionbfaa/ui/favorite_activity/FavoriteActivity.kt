package com.example.submissionbfaa.ui.favorite_activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionbfaa.data.UserViewModel
import com.example.submissionbfaa.data.ViewModelFactory
import com.example.submissionbfaa.databinding.ActivityFavoriteBinding
import com.example.submissionbfaa.ui.main_activity.UserAdapter
import com.example.submissionbfaa.utils.Status
import org.koin.android.ext.android.inject

class FavoriteActivity : AppCompatActivity() {

    companion object{
        val TAG: String = FavoriteActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val factory: ViewModelFactory by inject()
        val userViewModel: UserViewModel by viewModels {factory}
        val adapterUser: UserAdapter by inject()

        binding.apply {
            emptyGroupDetail.visibility = View.GONE
            pbLoading.visibility = View.VISIBLE
            favoriteRv.visibility = View.GONE
            setSupportActionBar(favoriteToolbar)
        }

        userViewModel.getFavoriteUser().observe(this){status ->
            if (status != null){
                when(status){
                    is Status.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                    }

                    is Status.Success -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.favoriteRv.visibility = View.VISIBLE
                        val newData = status.data
                        adapterUser.submitList(newData)

                        Log.e(TAG, newData.toString())

                        binding.apply {
                            if (adapterUser.itemCount == 0){
                                emptyGroupDetail.visibility = View.VISIBLE
                            }else{
                                emptyGroupDetail.visibility = View.GONE
                            }
                        }

                    }
                    is Status.Error -> {
                        binding.favoriteRv.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE
                        Log.e(TAG, status.error)
                        Toast.makeText(this@FavoriteActivity, "Terjadi Kesalahan : ${status.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        binding.favoriteRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterUser
        }

    }
}