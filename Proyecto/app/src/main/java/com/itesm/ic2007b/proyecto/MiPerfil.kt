package com.itesm.ic2007b.proyecto

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.parse.*
import kotlinx.android.synthetic.main.activity_mi_perfil.*
import kotlinx.android.synthetic.main.activity_mi_perfil.LogOut
import com.squareup.picasso.Picasso
import com.parse.ParseObject

import com.parse.FindCallback

import com.parse.ParseQuery




class MiPerfil : AppCompatActivity() {
    private lateinit var btnEdit: Button

    override fun onBackPressed() {
        intent = Intent(this,Home::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_perfil)

        var intent: Intent? = null

        val nombreG = App.prefsUser.getUserName()

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("username", nombreG)
        query.findInBackground { user, e ->
            if (e == null) {
                val ubicacion: String = user[0].ubicacion.toString()
                val descripcion: String = user[0].descripcion.toString()

                val query1 = ParseQuery.getQuery<ParseObject>("ArchivosUsuario")
                query1.whereEqualTo("username", nombreG)
                query1.findInBackground { userFiles, e ->
                    if (e == null) {
                        val docPDF : String? = userFiles[0].getParseFile(LLAVE_DOCPDF)?.url
                        val foto: String? = userFiles[0].getParseFile(LLAVE_FOTOPERFIL)?.url

                        displayData(foto, nombreG, ubicacion, descripcion, docPDF)
                    } else {
                        val text = "Error cargando datos"
                        val duration = Toast.LENGTH_LONG
                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    }
                }
            } else {
                val text = "Error cargando datos"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

        }


        initButton()
        editarPerfil()
        initializeNavbarMiPerfil()
        logOut()
        donate()
    }

    private fun displayData(fotoUrl:String?, nombre:String, ubicacion:String,
                            descripcion:String, docUrl:String?) {
        val fotoPerfilImageView: ImageView = findViewById(R.id.foto_perfil)
        val miNombreTextView: TextView = findViewById(R.id.mi_nombre)
        val ubicacionTextView: TextView = findViewById(R.id.ubicacion)
        val descripcionTextView: TextView = findViewById(R.id.descripcion_perfil)
        val docButton: Button = findViewById(R.id.button_abrir_pdf)


        val imageUri: Uri = Uri.parse(fotoUrl)
        Picasso.with(this).load(imageUri.toString()).into(fotoPerfilImageView)

        miNombreTextView.text = nombre
        ubicacionTextView.text = ubicacion
        descripcionTextView.text = descripcion


        val docName: String? = docUrl?.substringAfter("_")
        docButton.text = docName

        button_abrir_pdf.setOnClickListener {
            intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("key",docUrl);
            startActivity(intent)
        }
    }

    private fun initializeNavbarMiPerfil() {
        bottomNavigationViewMiPerfil.selectedItemId = R.id.perfilItem

        bottomNavigationViewMiPerfil.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inicioItem -> {
                    intent = Intent(this,Home::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.favoritosItem -> {
                    intent = Intent(this,favoritos::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    fun logOut(){
        LogOut.setOnClickListener{

            val  builder = AlertDialog.Builder(this)
            builder.setTitle("Atención")
            builder.setMessage("¿Deseas cerrar sesión?")
            builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int ->

                ParseUser.logOutInBackground { e: ParseException? ->
                    if (e == null)
                        App.prefsUser.clearAllData()
                    val text = "Vuelve pronto y recuerda donar!!!"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()

                    intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                }


            }

            builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
            builder.show()


        }
    }


    fun initButton(){
        btnEdit = findViewById(R.id.editar_perfil_btn)
    }

    fun editarPerfil(){
        btnEdit.setOnClickListener{
            prefsRegister.clearAllData()
            var intent: Intent = Intent(this,EditarRegister::class.java)
            startActivity(intent)
        }
    }

    fun donate(){
        donar.setOnClickListener {
            var intent: Intent = Intent(this,Donativos::class.java)
            startActivity(intent)
        }
    }
}