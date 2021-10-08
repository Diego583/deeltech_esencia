package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.itesm.ic2007b.proyecto.App.Companion.prefsUser
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //prefsUser.clearAllData()
        cardRestauradores.setOnClickListener {
            var intent: Intent = Intent(this,Restauradores::class.java)
            startActivity(intent)
        }

        cardAgentesInmobiliarios.setOnClickListener {
            var intent: Intent = Intent(this,AgentesInmobiliarios::class.java)
            startActivity(intent)
        }

        cardProveedores.setOnClickListener {
            var intent: Intent = Intent(this,Proveedores::class.java)
            startActivity(intent)
        }

    }

}