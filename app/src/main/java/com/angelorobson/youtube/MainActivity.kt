package com.angelorobson.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        val videos = mutableListOf<Video>()

        videoAdapter = VideoAdapter(videos) {
            println(videos)
        }

        rv_main?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = videoAdapter
        }

        CoroutineScope(Dispatchers.IO).launch {
            val res = async { getVideo() }
            val listVideo = res.await()
            withContext(Dispatchers.Main) {
                listVideo?.let {
                    videos.clear()
                    videos.addAll(it.data)
                    videoAdapter.notifyDataSetChanged()
                    progress_recycler.visibility = View.GONE
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun getVideo(): ListVideo? {
        val client = OkHttpClient.Builder().build()

        val request = Request.Builder()
            .get()
            .url("https://tiagoaguiar.co/api/youtube-videos")
            .build()

        return try {
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                GsonBuilder().create()
                    .fromJson(response.body()?.string(), ListVideo::class.java)
            } else {
                null
            }

        } catch (e: Exception) {
            null
        }
    }
}
