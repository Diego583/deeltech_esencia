package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_agentes_inmobiliarios.*

class AgentesInmobiliarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agentes_inmobiliarios)

        var intent: Intent? = null

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("roles", "Agente inmobiliario")
        query.findInBackground { user, e ->
            if (e == null) {
                displayAgentesInmobiliarios(user)
            } else {
                val text = "Error cargando Agentes inmobiliarios"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }

        initializeBackAgent()
    }

    private fun displayAgentesInmobiliarios(user: MutableList<ParseUser>) {
        val grid: GridView = findViewById(R.id.grid_ai)
        val adapter = ProfileAdapter(this, user)
        grid.setAdapter(adapter)
    }

    private fun initializeBackAgent() {
        backAgentesInmobiliarios.setOnClickListener{
            finish()
        }
    }
}