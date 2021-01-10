package com.example.favoriteapp.activity

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favoriteapp.R
import com.example.favoriteapp.adapter.FavAdapter
import com.example.favoriteapp.db.DatabaseContract
import com.example.favoriteapp.db.DatabaseContract.CONTENT_URI
import com.example.favoriteapp.entity.DataFavorite
import com.example.favoriteapp.mapping.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FavAdapter

    companion object{
        private const val STATE_LIST = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Favorite User"
        showRecyclerList()
        handleThread()
        if(savedInstanceState == null){
            loadNoteAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<DataFavorite>(STATE_LIST)
            if(list != null){
                adapter.listfav = list
            }
        }
    }

    fun showRecyclerList(){
        favuserlist.layoutManager = LinearLayoutManager(this)
        favuserlist.setHasFixedSize(true)
        adapter = FavAdapter()
        favuserlist.adapter = adapter
    }

    private fun loadNoteAsync(){
        GlobalScope.launch (Dispatchers.Main){
            progressBar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO){
                val cursor = contentResolver?.query(DatabaseContract.CONTENT_URI,null,null,null,null)
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
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNoteAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
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
