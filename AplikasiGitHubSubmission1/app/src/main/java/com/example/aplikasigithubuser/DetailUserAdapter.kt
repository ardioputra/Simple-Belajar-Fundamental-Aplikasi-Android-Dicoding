package com.example.aplikasigithubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class DetailUserAdapter (private val listDetailUsers: ArrayList<User>): RecyclerView.Adapter<DetailUserAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: Any) {
        this.onItemClickCallback = onItemClickCallback as OnItemClickCallback
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var rwUsername: TextView = itemView.findViewById(R.id.username_txt)
        var rwName: TextView = itemView.findViewById(R.id.name_user)
        var rwAvatar: CircleImageView = itemView.findViewById(R.id.png_foto)
        var rwFollower: TextView = itemView.findViewById(R.id.follower)
        var rwFollowing: TextView = itemView.findViewById(R.id.following)
        var rwCompany: TextView = itemView.findViewById(R.id.company)
        var rwLocation: TextView = itemView.findViewById(R.id.location)
        var rwRepository: TextView = itemView.findViewById(R.id.repository)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.activity_detail_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDetailUsers.size
    }

    override fun onBindViewHolder(holder:DetailUserAdapter.ListViewHolder, position: Int) {
        val detailuserlist = listDetailUsers[position]
        Glide.with(holder.itemView.context)
            .load(detailuserlist.avatar)
            .apply(RequestOptions().override(55, 55))
            .into(holder.rwAvatar)
        holder.rwUsername.text = detailuserlist.username
        holder.rwName.text = detailuserlist.name
        holder.rwFollower.text = detailuserlist.follower
        holder.rwFollowing.text = detailuserlist.following
        holder.rwLocation.text = detailuserlist.location
        holder.rwCompany.text = detailuserlist.company
        holder.rwRepository.text = detailuserlist.respository
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                onItemClickCallback.onItemClicked(listDetailUsers[holder.adapterPosition])
                val moveactivity = Intent(
                    view.context.getApplicationContext(),
                    DetailUser::class.java
                )
                moveactivity.putExtra(DetailUser.PROFIL, detailuserlist)
                view.context.startActivity(moveactivity)
            }
        })
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }



}