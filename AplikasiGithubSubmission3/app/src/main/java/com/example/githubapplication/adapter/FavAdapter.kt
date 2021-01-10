package com.example.githubapplication.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapplication.R
import com.example.githubapplication.activity.DetailActivity
import com.example.githubapplication.custom.CustomOnItemClickListener
import com.example.githubapplication.entity.DataFavorite
import kotlinx.android.synthetic.main.layout_user.view.useravatar
import kotlinx.android.synthetic.main.layout_user.view.username
import kotlinx.android.synthetic.main.layout_user.view.usertype

class FavAdapter(private val activity: Activity): RecyclerView.Adapter<FavAdapter.ListViewHolder>() {

    var listfav = ArrayList<DataFavorite>()
    set(listfav){
        if (listfav.size>0){
            this.listfav.clear()
        }
        this.listfav.addAll(listfav)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(view.context).inflate(R.layout.layout_user, view, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listfav[position])
    }

    override fun getItemCount(): Int = this.listfav.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav : DataFavorite){
            with(itemView){
                Glide.with(itemView.context)
                    .load(fav.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(useravatar)
                username.text = fav.name
                usertype.text = fav.type
                itemView.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object : CustomOnItemClickListener.OnItemClickCallback{
                            override fun onItemClicked(view:View, position:Int){
                                val intentObj = Intent(activity, DetailActivity::class.java)
                                intentObj.putExtra(DetailActivity.POS_USER, position)
                                intentObj.putExtra(DetailActivity.USER_LIST, fav)
                                activity.startActivity(intentObj)
                            }
                        }
                    )
                )

            }
        }

    }


}