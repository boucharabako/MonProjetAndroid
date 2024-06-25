package com.bouchara.projet

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MessageActivity : AppCompatActivity() {

    private lateinit var recyclerMessageView: RecyclerView
    lateinit var dbh: DBHelper
    private lateinit var newArray: ArrayList<DataMessageList>
    private lateinit var toolbarObj: Toolbar
    private lateinit var messageAdapter: MyMessageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_message)
        toolbarObj = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbarObj)

        // Activer le bouton de navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Définir l'écouteur pour le clic sur l'icône de navigation
        toolbarObj.setNavigationOnClickListener {
            println("************************************ZZZZZZZZZZZZZZZZZ")
            // Action de retour en arrière
            onBackPressed()
        }
        recyclerMessageView = findViewById(R.id.recyclerMessage)
        dbh = DBHelper(this)
        recyclerMessageView.layoutManager = LinearLayoutManager(this)
        recyclerMessageView.setHasFixedSize(true)
        displayuser()

    }
    private fun displayuser() {
        var newcursor: Cursor? = dbh!!.gettextMessage()
        newArray = ArrayList<DataMessageList>()
        while (newcursor!!.moveToNext()) {
            //val umessageNumber = newcursor.getString(1)
            val umessagePhoneName = newcursor.getString(2)
            val umessageText = newcursor.getString(3)
            val umessageDate = newcursor.getString(4)
            newArray.add(DataMessageList(umessageText, umessageDate,umessagePhoneName))
        }

        messageAdapter = MyMessageAdapter(newArray) // Initialise l'adaptateur avec la liste newArray.
        recyclerMessageView.adapter = messageAdapter // Associe l'adaptateur au RecyclerView.
        messageAdapter.onItemClickListener(object : MyMessageAdapter.onItemClickListener { // Définit un écouteur de clics sur les éléments de l'adaptateur.
            override fun onItemClick(position: Int) { // Méthode appelée lorsqu'un élément est cliqué.
                println("********************HMMMMMMMM111111111*****************")
                val intent = Intent(this@MessageActivity, TraitementMessage::class.java) // Crée une nouvelle intention pour démarrer TraitementContact.
                println("********************HMMMMMMMM 2222222*****************")
                intent.putExtra("message", newArray[position].message) // Ajoute le nom de l'élément cliqué à l'intention.
                startActivity(intent) // Démarre l'activité TraitementContact avec l'intention.
            }
        })
    }
    public fun redirect() {
        println("************** Est-ce rentré????????")
        //intent = Intent(this, MessageActivity::class.java)
        //startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Action de retour en arrière
        onBackPressed();
        return true;
    }
}