package com.example.aplikasigithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class DetailUser : AppCompatActivity() {

    companion object{
        val PROFIL = "extra_profil"
        /*val USERNAME = "extra_username"
        val NAME = "extra_name"
        val AVATAR = "extra_avatar"
        val FOLLOWING = "extra_following"
        val FOLLOWER = "extra_follower"
        val LOCATION = "extra_location"
        val COMPANY = "extra_company"
        val REPOSITORY = "extra_repository"*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val profil: User = intent.getParcelableExtra<User>(PROFIL) as User
        val layoutavatar: CircleImageView = findViewById(R.id.png_foto)
        val DataUsername:TextView = findViewById<TextView>(R.id.username_txt)
        val DataLocation:TextView = findViewById<TextView>(R.id.location)
        val DataFollowing:TextView = findViewById<TextView>(R.id.following)
        val DataName:TextView = findViewById<TextView>(R.id.name_user)
        val DataFollower:TextView = findViewById<TextView>(R.id.follower)
        val DataCompany:TextView = findViewById<TextView>(R.id.company)
        val DataRepository:TextView = findViewById<TextView>(R.id.repository)
        Glide.with(this)
            .load(profil.avatar)
            .apply(RequestOptions().override(100,100))
            .into(layoutavatar)
        DataUsername.text = profil.username
        DataName.text = profil.name
        DataFollower.text = "Follower: ${profil.follower}"
        DataFollowing.text = "Following: ${profil.following}"
        DataLocation.text = "Location: ${profil.location}"
        DataCompany.text = "Company: ${profil.company}"
        DataRepository.text = "Repository: ${profil.respository}"
    }
}