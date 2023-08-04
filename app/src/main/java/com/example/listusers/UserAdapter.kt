package com.example.listusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listusers.databinding.UserItemBinding

class UserAdapter(
    private var usersList: List<User>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class UserViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            Glide.with(binding.profileImage.context)
                .load(user.image)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .circleCrop()
                .into(binding.profileImage)

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedUser = usersList[position]
                    listener.onItemClick(clickedUser)
                }
            }

            binding.executePendingBindings()
        }
    }

    fun updateData(newData: List<User>) {
        usersList = newData
        notifyDataSetChanged()
    }
}

