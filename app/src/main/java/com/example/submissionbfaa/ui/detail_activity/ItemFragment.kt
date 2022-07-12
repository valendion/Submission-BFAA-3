package com.example.submissionbfaa.ui.detail_activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionbfaa.data.UserViewModel
import com.example.submissionbfaa.data.ViewModelFactory
import com.example.submissionbfaa.databinding.FragmentItemBinding
import com.example.submissionbfaa.utils.Status
import org.koin.android.ext.android.inject


class ItemFragment() : Fragment() {

    private var _binding: FragmentItemBinding? = null

    private val binding get() = _binding!!

    companion object {
        val TAG: String = ItemFragment::class.java.simpleName
    }

    fun newInstance(index: Int, username: String): ItemFragment {
        val fragment = ItemFragment()
        var args = Bundle()
        args.putInt("index", index)
        args.putString("name", username)
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.get("index") as Int
        val name = arguments?.get("name") as String

        val factory: ViewModelFactory by inject()
        val userViewModel: UserViewModel by viewModels { factory }
        val followAdapter: FollowAdapter by inject()

        binding.apply {
            detailRecycler.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = followAdapter

                when (index) {
                    0 -> {
                        userViewModel.getFollower(name).observe(viewLifecycleOwner) { status ->

                            if (status != null) {
                                when (status) {
                                    is Status.Loading -> {
                                        binding.detailRecycler.visibility = View.GONE
                                        pbLoading.visibility = View.VISIBLE
                                    }

                                    is Status.Success -> {
                                        binding.detailRecycler.visibility = View.VISIBLE
                                        pbLoading.visibility = View.GONE
                                        val data = ArrayList(status.data)
                                        Log.e(TAG, "Data Follower : $data")
                                        followAdapter.setList(data)
                                    }

                                    is Status.Error -> {
                                        binding.detailRecycler.visibility = View.VISIBLE
                                        pbLoading.visibility = View.GONE

                                        Log.e(TAG, status.error)
                                        Toast.makeText(
                                            activity,
                                            "Terjadi Kesalahan : ${status.error}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                            }
                        }

                    }

                    1 -> {
                        userViewModel.getFollowing(name).observe(viewLifecycleOwner) { status ->

                            if (status != null) {
                                when (status) {
                                    is Status.Loading -> {
                                        binding.detailRecycler.visibility = View.GONE
                                        pbLoading.visibility = View.VISIBLE
                                    }

                                    is Status.Success -> {
                                        binding.detailRecycler.visibility = View.VISIBLE
                                        pbLoading.visibility = View.GONE
                                        val data = ArrayList(status.data)
                                        Log.e(TAG, "Data Follower : $data")
                                        followAdapter.setList(ArrayList(data))
                                    }

                                    is Status.Error -> {
                                        binding.detailRecycler.visibility = View.VISIBLE
                                        pbLoading.visibility = View.GONE

                                        Log.e(TAG, status.error)
                                        Toast.makeText(
                                            activity,
                                            "Terjadi Kesalahan : ${status.error}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}