package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_perfil_otro.*

class PerfilOtro : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_otro)

        val buttonAssets: Button = findViewById(R.id.buttonAssets)
        setUpOnClickListener()
    }
    private fun setUpOnClickListener() {
        buttonAssets.setOnClickListener {
            val intent = Intent(this, PdfViewActivity::class.java)
            intent.putExtra("ViewType", "assets")
            startActivity(intent)
        }
    }

}