package com.bouchara.projet

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AjoutContact : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var save: Button
    private lateinit var db: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajout_contact)
        name = findViewById(R.id.et_nom)
        phone = findViewById(R.id.et_phone)
        save = findViewById(R.id.btn_save)
        db = DBHelper(this)

        save.setOnClickListener {
            val names = name.text.toString()
            val numbers = phone.text.toString()
            val savedata = db.saveuserdata(names, numbers)
            if (TextUtils.isEmpty(names) || TextUtils.isEmpty(numbers)) {
                Toast.makeText(
                    this,
                    "Veuillez saisir le nom et le numéro de télephone",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (savedata) {
                    Toast.makeText(this, "Contact enrégistré", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ListContactActivity::class.java)
                    println("*******************WHY*************1111111")
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Erreur lors de l'ajout du contact", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}