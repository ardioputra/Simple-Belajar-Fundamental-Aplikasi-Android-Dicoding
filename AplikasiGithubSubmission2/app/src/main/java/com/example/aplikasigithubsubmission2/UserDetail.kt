package com.example.aplikasigithubsubmission2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.activity_user_detail.progressBar
import kotlinx.android.synthetic.main.activity_user_detail.username
import org.json.JSONObject

class UserDetail : AppCompatActivity() {

    companion object {
        private val TAG = UserDetail::class.java.simpleName
        const val USER_DETAIL = "extra_user_items"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val userdetail = intent.getParcelableExtra<UserList>(USER_DETAIL) as UserList
        userdetail.name.let {
            if (it != null) {
                pagerAdapterSection(it)
                getUserDetail(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun pagerAdapterSection(userName: String?){
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = userName
        view_pager.adapter = sectionsPagerAdapter
        tabsfollow.setupWithViewPager(view_pager)
        supportActionBar?.apply{
            setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar?.elevation = 0f
    }

    private fun getUserDetail(userName: String?){
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$userName"
        client.addHeader("Authorization", "token db307936828ddaf0a42fe1a3a5c340443b7a5071")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    progressBar.visibility = View.INVISIBLE
                    val responseObject = JSONObject(result)
                    Glide.with(applicationContext)
                        .load(responseObject.getString("avatar_url"))
                        .into(userAvatar)
                    userrealname.text = responseObject.getString("name")
                    username.text = responseObject.getString("login")
                    usertype.text = responseObject.getString("type")
                    location.text = "Location: %s".format(responseObject.getString("location"))
                    company.text = "Company: %s".format(responseObject.getString("company"))
                    repository.text = "Repository Total: %,d".format(responseObject.getInt("public_repos"))
                } catch (e: Exception) {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@UserDetail, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@UserDetail, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}