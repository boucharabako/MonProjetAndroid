package com.bouchara.projet

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class ToolbarActivity : AppCompatActivity() {

    private lateinit var toolbarObj: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_toolbar)
        toolbarObj = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbarObj)

        // Activer le bouton de navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Définir l'écouteur pour le clic sur l'icône de navigation
        toolbarObj.setNavigationOnClickListener {
            println("Alooo");
            // Action de retour en arrière
            onBackPressed()
        }
    }

        override fun onSupportNavigateUp(): Boolean {
        // Action de retour en arrière
        onBackPressed();
        return true;
    }
}