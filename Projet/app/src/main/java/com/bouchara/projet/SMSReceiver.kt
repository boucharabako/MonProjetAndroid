package com.bouchara.projet

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SMSReceiver : BroadcastReceiver()   {

    lateinit var ma: MessageActivity

    override fun onReceive(context: Context, intent: Intent) {
        // Récupère les extras de l'intent, contenant les données du message SMS.
        val bundle = intent.extras
        // Crée une instance de DBHelper pour gérer la base de données.
        val dbHelper = DBHelper(context)
        // Ouvre la base de données en mode lecture/écriture.
        val db = dbHelper.writableDatabase

        // Vérifie que le bundle n'est pas null.
        if (bundle != null) {
            // Récupère le tableau de PDU contenant les données brutes du SMS.
            val pdus = bundle["pdus"] as Array<*>
            // Itère sur chaque PDU pour traiter chaque message SMS.
            for (pdu in pdus) {
                // Crée un objet SmsMessage à partir du PDU.
                val message = SmsMessage.createFromPdu(pdu as ByteArray)
                // Récupère le numéro de téléphone de l'expéditeur.
                val phoneNumber = message.originatingAddress
                println("**********************phoneNumber $phoneNumber")
                val messageBody = message.messageBody // Récupère le corps du message SMS.
                println("**********************MessageBody $messageBody")

                // Requête pour vérifier si le numéro de téléphone existe dans les contacts.
                val cursor = db.query("contacts", null, "contact=?", arrayOf(phoneNumber), null, null, null)
                println("*************************HELLOO * ${cursor.count}")
                // Si le curseur contient au moins un résultat, le contact existe.
                if (cursor.moveToFirst()) {
                    println("*************************HELLOO1111")
                    // Récupère le nom du contact à partir de la base de données locale.
                    val contactName = cursor.getString(cursor.getColumnIndexOrThrow("name"))

                    // Crée un objet ContentValues pour stocker les données du message.
                    val values = ContentValues().apply {
                        // Ajoute le numéro de téléphone de l'expéditeur.
                        put("contact_phone", phoneNumber)
                        // Ajoute le nom du contact.
                        put("contact_name", contactName)
                        // Ajoute le corps du message.
                        put("message", messageBody)
                    }
                    // Insère les données du message dans la table messages.
                    db.insert("messages", null, values)
                    // Affiche une notification Toast pour informer l'utilisateur de la réception du message.
                    Toast.makeText(context, "Message reçu de $contactName", Toast.LENGTH_SHORT).show()
                }
                cursor.close() // Ferme le curseur pour libérer les ressources.
            }
        }
    }

}
