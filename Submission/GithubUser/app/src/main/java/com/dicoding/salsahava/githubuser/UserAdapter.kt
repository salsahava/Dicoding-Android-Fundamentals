package com.dicoding.salsahava.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.salsahava.githubuser.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private val userList = ArrayList<UserItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setUserList(userItems: ArrayList<UserItem>) {
        userList.clear()
        userList.addAll(userItems)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAdapter.ListViewHolder {

        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.ListViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class ListViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem) {
            with (binding) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions().override(64, 64))
                    .into(imgItemPhoto)

                tvItemUsername.text = user.username

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)
    }
}