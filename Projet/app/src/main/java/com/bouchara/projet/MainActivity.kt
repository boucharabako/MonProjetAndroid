package com.bouchara.projet

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.content.IntentFilter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var ajoutContact: TextView
    private lateinit var voirMessages: TextView
    private lateinit var listContact: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ajoutContact = findViewById(R.id.ajout_contact_edittext)
        voirMessages = findViewById(R.id.voir_messages_edittext)
        listContact = findViewById(R.id.list_contact)
        ajoutContact.setOnClickListener {
            intent = Intent(this, AjoutContact::class.java)
            startActivity(intent)
        }
        voirMessages.setOnClickListener {
            intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
        }
        listContact.setOnClickListener {
            intent = Intent(this, ListContactActivity::class.java)
            startActivity(intent)
        }

        // Crée un filtre d'intention pour intercepter les messages SMS entrants
        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")

       // Enregistre un récepteur de diffusion (BroadcastReceiver) pour les événements correspondant au filtre
        registerReceiver(SMSReceiver(), filter)

        if (checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.RECEIVE_SMS,
                    android.Manifest.permission.READ_SMS,
                    android.Manifest.permission.READ_CONTACTS
                ), 1
            )
        }
    }

    // Surcharge de la méthode onRequestPermissionsResult pour gérer les résultats des demandes d'autorisation
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // Appel de la méthode de la superclasse pour maintenir le comportement par défaut
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Vérifie si la demande d'autorisation correspond au code de requête spécifié (1 dans ce cas)
        if (requestCode == 1) {
            // Vérifie si les résultats des autorisations ne sont pas vides et si la première autorisation est accordée
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Affiche un message indiquant que les permissions ont été accordées
                Toast.makeText(this, "Permissions accordées", Toast.LENGTH_SHORT).show()
            } else {
                // Affiche un message indiquant que les permissions ont été refusées
                Toast.makeText(this, "Permissions refusées", Toast.LENGTH_SHORT).show()
            }
        }
    }


}