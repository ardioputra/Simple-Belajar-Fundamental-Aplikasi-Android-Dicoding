package com.example.githubapplication.activity

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapplication.R
import com.example.githubapplication.adapter.FavAdapter
import com.example.githubapplication.db.DatabaseContract.CONTENT_URI
import com.example.githubapplication.mapping.MappingHelper
import kotlinx.android.synthetic.main.activity_fav_activitiy.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavActivitiy : AppCompatActivity() {

    private lateinit var adapter: FavAdapter

    companion object{
        private const val STATE_LIST = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_activitiy)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite User"
        showRecyclerList()
        handleThread()
    }

    fun showRecyclerList(){
        favuserlist.layoutManager = LinearLayoutManager(this)
        favuserlist.setHasFixedSize(true)
        adapter = FavAdapter(this)
        favuserlist.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadNoteAsync(){
        GlobalScope.launch (Dispatchers.Main){
            progressBar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO){
                val cursor = contentResolver?.query(CONTENT_URI,null,null,null,null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favData = deferredNotes.await()
            progressBar.visibility = View.INVISIBLE
            if(favData.size>0){
                adapter.listfav = favData
            } else {
                adapter.listfav = ArrayList()
            }
        }
    }

    private fun handleThread(){
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val Observer = object : ContentObserver(handler){
            override fun onChange(self: Boolean) {
                loadNoteAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true,Observer)
    }

    override fun onResume() {
        super.onResume()
        loadNoteAsync()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE_LIST, adapter.listfav)
    }
}