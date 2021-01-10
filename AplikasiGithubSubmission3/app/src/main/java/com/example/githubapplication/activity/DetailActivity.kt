package com.example.githubapplication.activity

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubapplication.R
import com.example.githubapplication.adapter.SectionPagerAdapter
import com.example.githubapplication.db.DatabaseContract.CONTENT_URI
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_COMPANY
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_ID
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_LOCATION
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_NAME
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_PIC
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_REAL
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_REPOS
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_TYPE
import com.example.githubapplication.db.UserHelper
import com.example.githubapplication.entity.DataFavorite
import com.example.githubapplication.entity.DataUser
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private var selectedFav = false
    private var userfav: DataFavorite? = null
    private var userdef: DataUser? = null
    private lateinit var usHelper: UserHelper
    private lateinit var favImage: String

    companion object{
        const val USER_DETAIL = "extra_detail"
        const val USER_FAV = "extra_fav"
        const val POS_USER = "extra_pos"
        const val USER_LIST = "extra_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        usHelper = UserHelper.getInstance(applicationContext)
        usHelper.open()
        userdef = intent.getParcelableExtra(USER_DETAIL)
        userfav = intent.getParcelableExtra(USER_LIST)

        if(userfav!=null){
            setObj()
            selectedFav=true
            val fav: Int = R.drawable.ic_favorite_black
            favButton.setImageResource(fav)
        } else {
            val cursor: Cursor = usHelper.queryById(userdef?.name.toString())
            if(cursor.count == 1){
                cursor.moveToNext()
                userfav?.id = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID))
                selectedFav = true
                SetFavorite(selectedFav)
            }
            setData()
        }


        pagerAdapterSection()
        favButton.setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun SetFavorite(check: Boolean){
        if (check) {
            favButton.setImageResource(R.drawable.ic_favorite_black)
        } else {
            favButton.setImageResource(R.drawable.ic_favorite_white)
        }
    }

    private fun pagerAdapterSection(){
        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabsfollow.setupWithViewPager(view_pager)
        supportActionBar?.apply{
            setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar?.elevation = 0f
    }

    fun setData(){
        val data = intent.getParcelableExtra<DataUser>(USER_DETAIL) as DataUser
        supportActionBar?.title = data.name
        Glide.with(this)
            .load(data.avatar)
            .into(useravatar)
        username.text = data.name
        userrealname.text = data.realName
        usertype.text = data.type
        company.text = data.company
        location.text = data.location
        repository.text = data.repos
        favImage = data.avatar.toString()
    }

    fun setObj(){
        val favdata = intent.getParcelableExtra<DataFavorite>(USER_LIST) as DataFavorite
        supportActionBar?.title = favdata.name
        Glide.with(this)
            .load(favdata.avatar)
            .into(useravatar)
        username.text = favdata.name
        userrealname.text = favdata.realName
        usertype.text = favdata.type
        company.text = favdata.company
        location.text = favdata.location
        repository.text = favdata.repos
        favImage = favdata.avatar.toString()
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.favButton){
            if(selectedFav){
                usHelper.deleteById(username.text.toString())
                selectedFav=false
            } else {
                val values = ContentValues()
                values.put(USER_PIC, favImage)
                values.put(USER_NAME, username.text.toString())
                values.put(USER_REAL, userrealname.text.toString())
                values.put(USER_TYPE, usertype.text.toString())
                values.put(USER_LOCATION, location.text.toString())
                values.put(USER_COMPANY, company.text.toString())
                values.put(USER_REPOS, repository.text.toString())
                selectedFav = true
                contentResolver.insert(CONTENT_URI, values)
            }
            SetFavorite(selectedFav)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        usHelper.close()
    }
}