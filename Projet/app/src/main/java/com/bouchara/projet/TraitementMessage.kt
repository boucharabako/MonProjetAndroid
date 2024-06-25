package com.bouchara.projet

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TraitementMessage : AppCompatActivity() {

    private lateinit var message: TextView
    private lateinit var delete: ImageView
    private lateinit var db: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_traitement_message)

        message = findViewById(R.id.textMessage)
        delete = findViewById(R.id.imageDeleteMessage)

        db = DBHelper(this)
        message.setText(intent.getStringExtra("message"))

        delete.setOnClickListener {
            val message = message.text.toString()
            val deletedata = db.deleteuserMessagedata(message)
            if (deletedata == true) {
                Toast.makeText(this, "message supprimé", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MessageActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Erreur de suppression", Toast.LENGTH_SHORT).show()
            }
        }
    }
}