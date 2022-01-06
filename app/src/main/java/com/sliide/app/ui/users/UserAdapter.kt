package com.sliide.app.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sliide.app.data.model.User
import com.sliide.app.data.model.User.Companion.DIFF_CALLBACK
import com.sliide.app.databinding.ItemUserBinding

class UserAdapter(
    private val longClickCallback: ((User) -> Unit),
) : ListAdapter<User, UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder.create(parent, longClickCallback)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val longClickCallback: (User) -> Unit
) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {

    private lateinit var item: User

    init {
        binding.card.setOnLongClickListener(this)
    }

    override fun onLongClick(view: View?): Boolean {
        longClickCallback.invoke(item)
        return true
    }

    fun bind(item: User) {
        this.item = item
        binding.tvName.text = item.name
        binding.tvEmailAddress.text = item.email
    }

    companion object {
        fun create(
            parent: ViewGroup,
            longClickCallback: (User) -> Unit
        ): UserViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemUserBinding.inflate(inflater, parent, false)
            return UserViewHolder(binding, longClickCallback)
        }
    }
}