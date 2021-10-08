package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_agentes_inmobiliarios.*
import kotlinx.android.synthetic.main.activity_proveedores.*

class AgentesInmobiliarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agentes_inmobiliarios)

        var intent: Intent? = null

        initializeBackAgent()
    }

    private fun initializeBackAgent() {
        backAgentesInmobiliarios.setOnClickListener{
            finish()
        }
    }
}