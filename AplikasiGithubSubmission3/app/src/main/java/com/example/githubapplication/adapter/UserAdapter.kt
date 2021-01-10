package com.example.githubapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapplication.R
import com.example.githubapplication.activity.DetailActivity
import com.example.githubapplication.activity.DetailActivity.Companion.USER_DETAIL
import com.example.githubapplication.activity.DetailActivity.Companion.USER_FAV
import com.example.githubapplication.entity.DataUser
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.layout_user.view.*
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter(private val datalist: ArrayList<DataUser>): RecyclerView.Adapter<UserAdapter.ListViewHolder>(), Filterable {
    private var onItemClickCallback: OnItemClickCallback? = null
    var filterlist = ArrayList<DataUser>()
    lateinit var mcontext: Context

    init {
        filterlist = datalist
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): UserAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(view.context).inflate(R.layout.layout_user, view, false)
        val sc = ListViewHolder(view)
        mcontext = view.context
        return sc
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userpic: CircleImageView = view.useravatar
        var username: TextView = view.username
        var usertype: TextView = view.usertype
    }

    override fun onBindViewHolder(holder: UserAdapter.ListViewHolder, position: Int) {
        val list = filterlist[position]
        holder.username.text = list.name
        holder.usertype.text = list.type
        Glide.with(holder.itemView.context)
            .load(list.avatar)
            .apply(RequestOptions().override(55, 55))
            .into(holder.userpic)
        holder.itemView.setOnClickListener{
            val listdata = DataUser(
                list.avatar,
                list.name,
                list.realName,
                list.type,
                list.company,
                list.location,
                list.repos,
                list.favorite
            )
            val pindahActivity = Intent(mcontext, DetailActivity::class.java)
            pindahActivity.putExtra(USER_DETAIL, listdata)
            pindahActivity.putExtra(USER_FAV, listdata)
            mcontext.startActivity(pindahActivity)
        }
    }

    override fun getItemCount(): Int = filterlist.size

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSearch = constraint.toString()
                filterlist = if (charSearch.isEmpty()) {
                    datalist
                } else {
                    val result = ArrayList<DataUser>()
                    for (row in filterlist) {
                        if ((row.name.toString().toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)))
                        ) {
                            result.add(
                                DataUser(
                                    row.avatar,
                                    row.name,
                                    row.realName,
                                    row.type,
                                    row.company,
                                    row.location,
                                    row.repos,
                                )
                            )
                        }
                    }
                    result
                }
                val filterResults = FilterResults()
                filterResults.values = filterlist
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filterlist = results.values as ArrayList<DataUser>
                notifyDataSetChanged()
            }
        }
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user:DataUser)
    }


}