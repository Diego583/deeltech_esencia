package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_mi_perfil.*
import kotlinx.android.synthetic.main.activity_otro_perfil.*

class OtroPerfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otro_perfil)

        val fotoIV: ImageView = findViewById(R.id.fotoIV)
        val nombreTV: TextView = findViewById(R.id.nombreTV)
        val ubicacionTV: TextView = findViewById(R.id.ubicacionTV)
        val contact_btn: Button = findViewById(R.id.contactame_btn)
        val contact_layout: RelativeLayout = findViewById(R.id.contactInfoLayout)
        val phoneTV: TextView = findViewById(R.id.phoneTV)
        val emailTV: TextView = findViewById(R.id.emailTV)
        val descripcionTV: TextView = findViewById(R.id.descripcion_perfil)
        val docButton: Button = findViewById(R.id.abrir_pdf_btn)

        val extras = intent.extras
        if (extras != null) {

            val fotoPerfil = extras.getString("fotoPerfil")
            val imageUri: Uri = Uri.parse(fotoPerfil)
            Picasso.with(this).load(imageUri.toString()).into(fotoIV)

            val username = extras.getString("username")
            nombreTV.text = username

            val ubicacion = extras.getString("ubicacion")
            ubicacionTV.text = ubicacion

            val phone = extras.getString("phone")
            val email = extras.getString("email")
            phoneTV.text = phone
            emailTV.text = email
            contactame_btn.setOnClickListener {
                if (contact_layout.visibility == View.GONE) {
                    contact_layout.visibility = View.VISIBLE
                } else {
                    contact_layout.visibility = View.GONE
                }
            }

            val descripcion = extras.getString("descripcion")
            descripcionTV.text = descripcion

            val docPDF = extras.getString("docPDF")
            val docName: String? = docPDF?.substringAfter("_")
            docButton.text = docName
            abrir_pdf_btn.setOnClickListener {
                intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("key",docPDF);
                startActivity(intent)
            }
        }

        initializeGoBackBtn()
    }

    private fun initializeGoBackBtn() {
        go_back_btn.setOnClickListener{
            finish()
        }
    }
}