package com.example.aplikasigithubsubmission2

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.layout_user.view.*
import kotlinx.android.synthetic.main.layout_user.view.username
import kotlinx.android.synthetic.main.layout_user.view.usertype

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ListViewHolder>(){

    private val listUser = ArrayList<UserList>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<UserList>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
        val specUserData = listUser[position]
        holder.itemView.setOnClickListener{
            val pindahIntent = Intent(it.context, UserDetail::class.java)
            pindahIntent.putExtra(UserDetail.USER_DETAIL,specUserData)
            it.context.startActivity(pindahIntent)
        }
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(user: UserList) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(useravatar)
                username.text = user.name
                usertype.text = "User"
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserList)
    }
}