package com.bouchara.projet

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListContactActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var button: FloatingActionButton
    lateinit var dbh: DBHelper
    private lateinit var newArray: ArrayList<DataList>
    private lateinit var adapter: MyAdapter
    private lateinit var toolbarObj: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_contact)
        toolbarObj = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbarObj)
        // Activer le bouton de navigation
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Définir l'écouteur pour le clic sur l'icône de navigation
        toolbarObj.setNavigationOnClickListener {
            println("************************************ZZZZZZZZZZZZZZZZZ")
            // Action de retour en arrière
            onBackPressed()
        }
        recyclerView = findViewById(R.id.recycler)
        button = findViewById(R.id.btn_add)
        button.setOnClickListener {
            intent = Intent(this, AjoutContact::class.java)
            startActivity(intent)
        }


        dbh = DBHelper(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        displayuser()

    }

    private fun displayuser() {
        var newcursor: Cursor? = dbh!!.gettext() // Récupère un curseur à partir de la méthode gettext() de l'objet dbh.
        newArray = ArrayList<DataList>() // Initialise une nouvelle liste ArrayList pour stocker les objets DataList.
        while (newcursor!!.moveToNext()) { // Parcourt chaque ligne du curseur.
            val uname = newcursor.getString(1) // Récupère la valeur de la deuxième colonne (index 1) de la ligne actuelle.
            val unumber = newcursor.getString(2) // Récupère la valeur de la troisième colonne (index 2) de la ligne actuelle.
            newArray.add(DataList(uname, unumber)) // Ajoute un nouvel objet DataList à la liste newArray avec les valeurs récupérées.
        }
        adapter = MyAdapter(newArray) // Initialise l'adaptateur avec la liste newArray.
        recyclerView.adapter = adapter // Associe l'adaptateur au RecyclerView.
        adapter.onItemClickListener(object : MyAdapter.onItemClickListener { // Définit un écouteur de clics sur les éléments de l'adaptateur.
            override fun onItemClick(position: Int) { // Méthode appelée lorsqu'un élément est cliqué.
                println("********************HMMMMMMMM111111111*****************")
                val intent = Intent(this@ListContactActivity, TraitementContact::class.java) // Crée une nouvelle intention pour démarrer TraitementContact.
                println("********************HMMMMMMMM 2222222*****************")
                intent.putExtra("name", newArray[position].name) // Ajoute le nom de l'élément cliqué à l'intention.
                intent.putExtra("phone", newArray[position].contact) // Ajoute le numéro de téléphone de l'élément cliqué à l'intention.
                startActivity(intent) // Démarre l'activité TraitementContact avec l'intention.
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        // Action de retour en arrière
        onBackPressed();
        return true;
    }
}