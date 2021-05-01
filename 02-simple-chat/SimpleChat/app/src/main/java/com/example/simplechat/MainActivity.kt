package com.example.simplechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.drafts.Draft_17
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class MainActivity : AppCompatActivity() {
    inner class MyWebSocketClient(serverUri: URI?, draft: Draft?): WebSocketClient(serverUri, draft) {
        override fun onError(exception: Exception) {
            Log.d("error in websocket:", exception.toString())
        }

        override fun onOpen(handshakedata: ServerHandshake?) {
            this@MainActivity.runOnUiThread {
                Toast.makeText(this@MainActivity, "Opened connection", Toast.LENGTH_LONG).show()
            }
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {
            this@MainActivity.runOnUiThread {
                Toast.makeText(this@MainActivity, "Closed connection", Toast.LENGTH_LONG).show()
            }
        }

        override fun onMessage(message: String?) {
            this@MainActivity.runOnUiThread {
                val chatText = findViewById<TextView>(R.id.chatText)
                val oldText = chatText.text
                chatText.text = "$message\n$oldText"
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // alias URL for host machine of emulator
        val websocketURI = "ws://10.0.2.2:8080"
        val webSocketClient = MyWebSocketClient(URI(websocketURI), Draft_17())
        webSocketClient.connect()

        // click listener for send button
        val btnSend = findViewById<Button>(R.id.sendMessage)
        val textUsername = findViewById<EditText>(R.id.username)
        val textMessage = findViewById<EditText>(R.id.chatMessage)
        btnSend.setOnClickListener {
            val username = textUsername.text
            val message = textMessage.text
            webSocketClient.send("$username: $message")
        }
    }
}

