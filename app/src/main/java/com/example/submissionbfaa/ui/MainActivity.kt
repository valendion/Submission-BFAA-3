package com.example.submissionbfaa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionbfaa.data.UserViewModel
import com.example.submissionbfaa.data.ViewModelFactory
import com.example.submissionbfaa.databinding.ActivityMainBinding
import com.example.submissionbfaa.utils.Status
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    companion object{
         val TAG: String = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val factory: ViewModelFactory by inject()
        val userViewModel: UserViewModel by viewModels {factory}
        val adapterUser: UserAdapter by inject()

        userViewModel.getUserGithub().observe(this){status ->
            if (status != null){
                when(status){
                    is Status.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                    }

                    is Status.Success -> {
                        binding.pbLoading.visibility = View.GONE
                        val newData = status.data
                        adapterUser.submitList(newData)
                    }

                    is Status.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        Log.e(TAG, status.error)
                        Toast.makeText(this@MainActivity, "Terjadi Kesalahan : ${status.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.mainRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = adapterUser

            binding.apply {
                Log.e(TAG, "Size List : ${adapterUser.itemCount}")
                if (adapterUser.itemCount == 0){
                    emptyGroupMain.visibility = View.VISIBLE
                }else{
                    emptyGroupMain.visibility = View.GONE
                }
            }
        }


    }
}