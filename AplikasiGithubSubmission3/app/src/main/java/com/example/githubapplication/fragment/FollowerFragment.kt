package com.example.githubapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapplication.R
import com.example.githubapplication.activity.DetailActivity.Companion.USER_DETAIL
import com.example.githubapplication.activity.DetailActivity.Companion.USER_LIST
import com.example.githubapplication.adapter.UserAdapter
import com.example.githubapplication.entity.DataFavorite
import com.example.githubapplication.entity.DataUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_follower.*
import kotlinx.android.synthetic.main.fragment_follower.progressBar
import org.json.JSONArray
import org.json.JSONObject

class FollowerFragment : Fragment() {
    companion object {
        private val TAG = FollowerFragment::class.java.simpleName
    }

    private lateinit var dataUser: DataUser
    private lateinit var dataFavorite: DataFavorite
    private var favo: DataFavorite? = null
    private lateinit var adapter: UserAdapter
    private var listfollower: ArrayList<DataUser> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter(listfollower)
        showRecyclerList()
        listfollower.clear()

        favo = activity!!.intent.getParcelableExtra(USER_LIST)
        if(favo != null){
            dataFavorite = activity!!.intent.getParcelableExtra<DataFavorite>(USER_LIST) as DataFavorite
            getUserListFollower(dataFavorite.name.toString())
        } else {
            dataUser = activity!!.intent.getParcelableExtra<DataUser>(USER_DETAIL) as DataUser
            getUserListFollower(dataUser.name.toString())
        }
    }

    private fun getUserListFollower(username: String?){
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", "token dcdd5f2d070b9e476aa3da099c0fb37baa81a306")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val results = String(responseBody)
                    val items = JSONArray(results)
                    Log.d("jsonFollow", items.toString())
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        getUserDetail(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getUserDetail(username: String) {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token dcdd5f2d070b9e476aa3da099c0fb37baa81a306")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$username"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val item = JSONObject(result)
                    val username = item.getString("login")
                    val realname = item.getString("name")
                    val userpic = item.getString("avatar_url")
                    val usertype = item.getString("type")
                    val company = item.getString("company")
                    val location = item.getString("location")
                    val repos = item.getString("public_repos")
                    listfollower.add(
                        DataUser(
                            userpic,
                            username,
                            realname,
                            usertype,
                            location,
                            company,
                            repos
                        )
                    )
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                        .show()
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
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun showRecyclerList(){
        userlistfollower.layoutManager = LinearLayoutManager(activity)
        userlistfollower.adapter = UserAdapter(listfollower)
    }
}