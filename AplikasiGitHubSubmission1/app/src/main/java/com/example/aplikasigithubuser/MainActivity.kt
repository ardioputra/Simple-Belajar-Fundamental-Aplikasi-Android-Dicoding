package com.example.aplikasigithubuser

import android.content.Intent
import android.content.Intent.EXTRA_USER
import android.content.res.TypedArray
import android.net.VpnService.prepare
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var list: ArrayList<User> = arrayListOf()
    private lateinit var adapter: UserAdapter
    private lateinit var dataUsername: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataPhoto: TypedArray
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataFollower: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataRepository: Array<String>
    private var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView: ListView = findViewById(R.id.list_user)
        adapter = UserAdapter(this)
        listView.adapter = adapter
        prepare()
        addItem()

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(this@MainActivity, users[position].name, Toast.LENGTH_SHORT).show()
            val PindahActivity = Intent(this@MainActivity, DetailUser::class.java)
            PindahActivity.putExtra(DetailUser.PROFIL, users[position])
            startActivity(PindahActivity)
        }
    }


    private fun prepare() {
        dataUsername = resources.getStringArray(R.array.data_username)
        dataName = resources.getStringArray(R.array.data_name)
        dataPhoto = resources.obtainTypedArray(R.array.data_avatar)
        dataFollowing = resources.getStringArray(R.array.data_following)
        dataFollower = resources.getStringArray(R.array.data_followers)
        dataLocation = resources.getStringArray(R.array.data_location)
        dataCompany = resources.getStringArray(R.array.data_company)
        dataRepository = resources.getStringArray(R.array.data_repository)

    }

    private fun addItem() {
        for (position: Int in dataUsername.indices) {
            val user = User(
                dataPhoto.getResourceId(position, -1),
                dataUsername[position],
                dataName[position],
                dataFollower[position],
                dataFollowing[position],
                dataLocation[position],
                dataCompany[position],
                dataRepository[position]
            )
            users.add(user)
        }
        adapter.users = users
    }


    }

