package com.example.randomcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val TAG = "MainActivity"
        val url = "https://loremflickr.com/1080/720/cat"
        val catView = findViewById<ImageView>(R.id.catView)
        val btnNextCat = findViewById<Button>(R.id.nextCat)
        btnNextCat.setOnClickListener {
            // Picasso somehow never loads the new picture into the view
            Picasso.get().load(url).placeholder(R.drawable.refresh).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(catView, object: com.squareup.picasso.Callback {
                override fun onSuccess() {
                    Toast.makeText(this@MainActivity, "Showing next cat", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: java.lang.Exception?) {
                    Log.d(TAG, "Error while loading new picture")
                }
            })
        }
    }
}