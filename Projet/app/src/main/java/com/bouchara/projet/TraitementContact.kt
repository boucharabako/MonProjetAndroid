package com.bouchara.projet

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TraitementContact : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var delete: ImageView
    private lateinit var edit: ImageView
    private lateinit var db: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_traitement_contact)

        name = findViewById(R.id.editName)
        phone = findViewById(R.id.editContact)
        delete = findViewById(R.id.imageDelete)
        edit = findViewById(R.id.imageEdit)

        db = DBHelper(this)
        name.setText(intent.getStringExtra("name"))
        phone.setText(intent.getStringExtra("phone"))

        delete.setOnClickListener {
            val names = name.text.toString()
            val deletedata = db.deleteuserdata(names)
            if (deletedata == true) {
                Toast.makeText(this, "Contact supprimé", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ListContactActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Erreur de suppression", Toast.LENGTH_SHORT).show()
            }
        }

        edit.setOnClickListener {
            val names = name.text.toString()
            val numbers = phone.text.toString()
            val updatedata = db.updateuserdata(names, numbers)
            if (updatedata == true) {
                Toast.makeText(this, "Contact mis à jour", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ListContactActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Pas de mise à jour", Toast.LENGTH_SHORT).show()
            }

        }
    }
}