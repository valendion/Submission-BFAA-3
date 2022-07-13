package com.example.submissionbfaa.ui.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionbfaa.R
import com.example.submissionbfaa.data.UserViewModel
import com.example.submissionbfaa.data.ViewModelFactory
import com.example.submissionbfaa.databinding.ActivityMainBinding
import com.example.submissionbfaa.ui.favorite_activity.FavoriteActivity
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

        binding.apply {
            setSupportActionBar(mainToolbar)
            emptyGroupMain.visibility = View.GONE
            binding.pbLoading.visibility = View.VISIBLE
            mainRv.visibility = View.GONE
        }

        userViewModel.getUserGithub().observe(this){status ->

            if (status != null){
                when(status){
                    is Status.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                    }

                    is Status.Success -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.mainRv.visibility = View.VISIBLE
                        val newData = status.data
                        adapterUser.submitList(newData)

                        binding.apply {
                            if (adapterUser.itemCount == 0){
                                emptyGroupMain.visibility = View.VISIBLE
                            }else{
                                emptyGroupMain.visibility = View.GONE
                            }
                        }
                    }

                    is Status.Error -> {
                        binding.mainRv.visibility = View.VISIBLE
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
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}