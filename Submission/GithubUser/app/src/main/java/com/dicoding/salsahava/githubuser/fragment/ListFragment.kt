package com.dicoding.salsahava.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.salsahava.githubuser.*
import com.dicoding.salsahava.githubuser.databinding.FragmentListBinding
import com.dicoding.salsahava.githubuser.viewmodel.UserListViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var userListViewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUsers.setHasFixedSize(true)

        binding.rvUsers.layoutManager = LinearLayoutManager(activity)

        val userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        binding.rvUsers.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {

            }
        })

        manageViewModel(userAdapter)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    private fun manageViewModel(adapter: UserAdapter) {
        userListViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserListViewModel::class.java)

        showLoading(true)
        userListViewModel.searchUser("salsa")

        userListViewModel.getUsers().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                adapter.setUserList(userItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


//    private fun showRecyclerList(view: View) {
//        binding.rvUsers.layoutManager = LinearLayoutManager(activity)
//        val userAdapter = UserAdapter(users)
//        binding.rvUsers.adapter = userAdapter
//
//        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: User) {
//                val mBundle = Bundle()
//                mBundle.putParcelable(DetailFragment.EXTRA_USER, data)
//
//                view.findNavController().navigate(R.id.action_listFragment_to_detailFragment, mBundle)
//            }
//        })
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}