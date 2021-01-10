package com.example.aplikasigithubuser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter internal constructor(private val context:Context): BaseAdapter() {

    private inner class ViewHolder internal constructor(view:View){
        private val pngPhoto: CircleImageView = view.findViewById(R.id.png_foto)
        private val txtUsername: TextView = view.findViewById(R.id.username_txt)
        private val txtName: TextView = view.findViewById(R.id.name_user)

        internal fun bind(user:User){
            txtName.text = user.name
            txtUsername.text = user.username
            pngPhoto.setImageResource(user.avatar)
        }

    }


    internal var users = arrayListOf<User>()
    override fun getCount(): Int {
        return users.size
    }

    override fun getItem(p0: Int): Any {
        return users[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View? {
        var itemView = view
        if (itemView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.user_layout, viewGroup,false)
        }
        val viewHolder = ViewHolder(itemView as View)
        val user = getItem(position) as User
        viewHolder.bind(user)
        return itemView
    }

}