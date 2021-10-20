package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.itesm.ic2007b.proyecto.App.Companion.prefsUser
import com.parse.ParseObject
import com.parse.ParseQuery
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_otro_perfil.*

/**
 * Activity para mostrar perfiles
 * **/
class OtroPerfil : AppCompatActivity() {
    override fun onBackPressed() {
        //Revisamos si el perfil paso por la vista de favoritos
        if(prefsUser.statusFavoritos()){
            intent = Intent(this,favoritos::class.java)
            startActivity(intent)
            finish()
        }
        else{
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otro_perfil)

        // Encontrar Views y botones
        val fotoIV: ImageView = findViewById(R.id.fotoIV)
        val nombreTV: TextView = findViewById(R.id.nombreTV)
        val ubicacionTV: TextView = findViewById(R.id.ubicacionTV)
        val contact_btn: Button = findViewById(R.id.contactame_btn)
        val contact_layout: RelativeLayout = findViewById(R.id.contactInfoLayout)
        val phoneTV: TextView = findViewById(R.id.phoneTV)
        val emailTV: TextView = findViewById(R.id.emailTV)
        val descripcionTV: TextView = findViewById(R.id.descripcion_perfil)
        val docButton: Button = findViewById(R.id.abrir_pdf_btn)
        val guardarFavorito_btn: Button = findViewById(R.id.agregar_favoritos_btn)

        // Obtener datos accediendo a los extras
        val extras = intent.extras
        if (extras != null) {

            val fotoPerfil = extras.getString("fotoPerfil")
            val imageUri: Uri = Uri.parse(fotoPerfil)
            Picasso.with(this).load(imageUri.toString()).into(fotoIV)

            val objectId = extras.getString("objectId")

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

            //Validamos si el perfil ya esta en favoritos
            if(prefsUser.getFavoritos().contains(username)){
                guardarFavorito_btn.setText("QUITAR DE FAVORITOS")
                guardarFavorito_btn.setBackgroundColor(Color.parseColor("#09E5B2"))
                quitarFavoritos(username)
            }
            else{
                guardarFavorito_btn.setText("AGREGAR A FAVORITOS")
                guardarFavorito_btn.setBackgroundColor(Color.parseColor("#D83670"))
                agregarFavoritos(username)
            }
        }

        initializeGoBackBtn()
    }

    /**
     * Función para eliminar favorito de la base de datos
     * @param String?
     * **/
    private fun quitarFavoritos(favorito: String?) {
        agregar_favoritos_btn.setOnClickListener {
            //Obtenemos el objectId del favorito
            val query = ParseQuery.getQuery<ParseObject>("UsuarioFavoritos")
            query.whereEqualTo("username", prefsUser.getUserName())
            query.whereEqualTo("favorito", favorito)
            query.findInBackground { favoriteUser, e ->
                if (e == null) {
                    val objectId: String = favoriteUser[0].objectId.toString()

                    //Eliminamos
                    val UsuarioFavoritos = ParseObject("UsuarioFavoritos")
                    val query1 = ParseQuery.getQuery<ParseObject>("UsuarioFavoritos")
                    query1.whereEqualTo("objectId", objectId)
                    query1.getFirstInBackground { UsuarioFavoritos, e ->
                        if (favorito != null) {
                            prefsUser.removeUserFavoritos(favorito)
                        }
                        UsuarioFavoritos.deleteInBackground()

                        //Mensaje y cambio de boton
                        agregar_favoritos_btn.setText("AGREGAR A FAVORITOS")
                        agregar_favoritos_btn.setBackgroundColor(Color.parseColor("#D83670"))
                        val text = "Eliminado de favoritos"
                        val duration = Toast.LENGTH_LONG
                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                        agregarFavoritos(favorito)
                    }

                } else {
                    val text = "Error cargando datos"
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }
            }
        }
    }

    /**
     * Función para agregar favorito de la base de datos
     * @param String?
     * **/
    private fun agregarFavoritos(favorito: String?) {
        agregar_favoritos_btn.setOnClickListener {
            if (favorito != null) {
                prefsUser.saveUserFavoritos(favorito)

                //GUARDAMOS
                val UsuarioFavoritos = ParseObject("UsuarioFavoritos")
                UsuarioFavoritos.put("username", prefsUser.getUserName()) //Se guarda el usuario logeado
                UsuarioFavoritos.put("favorito", favorito) //Se guarda el usuario que le gusto
                UsuarioFavoritos.saveInBackground()

                //Mensaje y cambio de boton
                agregar_favoritos_btn.setText("QUITAR DE FAVORITOS")
                agregar_favoritos_btn.setBackgroundColor(Color.parseColor("#09E5B2"))
                //Mensaje y cambio de boton
                val text = "Añadido a favoritos"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
                quitarFavoritos(favorito)
            }
        }
    }

    /**
     * Función que inicializa el imageButton de regresar
     * para que termine el activity
     * **/
    private fun initializeGoBackBtn() {
        go_back_btn.setOnClickListener{
            //Revisamos si el perfil paso por la vista de favoritos
            if(prefsUser.statusFavoritos()){
                intent = Intent(this,favoritos::class.java)
                startActivity(intent)
                finish()
            }
            else{
                finish()
            }
        }
    }
}