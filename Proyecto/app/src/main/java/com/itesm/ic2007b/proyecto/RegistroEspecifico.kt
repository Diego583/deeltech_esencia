package com.itesm.ic2007b.proyecto

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.databinding.ActivityRegistroEspecificoBinding
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_registro_especifico.*
import kotlinx.android.synthetic.main.activity_registro_especifico.spinner
import kotlinx.android.synthetic.main.activity_roles.*
import com.parse.ParseException;
import com.parse.SignUpCallback;


/**
 * Acivity para que el usuario suba sus archivos y su descripción
 * @author DeelTech
 **/
class RegistroEspecifico : AppCompatActivity() {

    /**
     * Funcion que registra al usuario con todos sus datos
     * @param username
     * @param password
     * @param email
     **/
    private fun signUp(username: String, password: String, email: String) {
        val user = ParseUser()

        user.username = username
        user.setPassword(password)
        user.email = email
        user.put("phone", prefsRegister.getNumero()) //numero, se crea la columna numero y se guarda ahí
        user.put("roles", prefsRegister.getRol()) //rol, se crea la columna roles y se guarda ahí
        user.put("descripcion", prefsRegister.getDescricpion()) //rol, se crea la columna roles y se guarda ahí
        user.put("ubicacion", prefsRegister.getEstado()) //rol, se crea la columna estado y se guarda ahí
        user.put("emailCopy", prefsRegister.getEmail())
        user.signUpInBackground(SignUpCallback {
            if (it == null) {

                //ARCHIVOS STRING
                val imageString = prefsRegister.getImage()
                val fileString = prefsRegister.getPortafolio()

                //ARCHIVOS BYTARRAY
                val imageByteArray: ByteArray = Base64.decode(imageString, Base64.DEFAULT)
                val fileByteArray: ByteArray = Base64.decode(fileString, Base64.DEFAULT)

                //Se crean archivos parce
                val file = ParseFile("file.pdf", fileByteArray)
                val image = ParseFile("imagen.jpg", imageByteArray)

                //GUARDAMOS
                val ArchivosUsuario = ParseObject("ArchivosUsuario")
                ArchivosUsuario.put("username", username)
                ArchivosUsuario.put("docPDF", file) //Se guarda el portafolio
                ArchivosUsuario.put("fotoPerfil", image) //Se guarda la foto de perfil
                ArchivosUsuario.saveInBackground()

                val  builder = AlertDialog.Builder(this)
                builder.setTitle("¡Cuenta creada exitosamente!")
                builder.setMessage("No olvides verificar tu correo para poder iniciar sesion.")
                builder.setPositiveButton("OK",{ dialogInterface: DialogInterface, i: Int ->
                    prefsRegister.clearAllData()
                    ParseUser.logOut();
                    //Regresa a Login
                    intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                })
                builder.show()

            } else {
                prefsRegister.clearAllData()
                ParseUser.logOut();
                val text = "Intentalo más tarde"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        })
    }

    /**
     * Variables globales, binding y botón
     **/
    private lateinit var binding : ActivityRegistroEspecificoBinding
    private lateinit var btnTerminar: Button

    /**
     * Lista de datos para el spinner
     **/
    val list = mutableListOf(
        "Aguascalientes",
        "Baja California",
        "Baja California Sur",
        "Campeche",
        "CDMX",
        "Chiapas",
        "Chihuahua",
        "Coahuila",
        "Colima",
        "Durango",
        "Guanajuato",
        "Guerrero",
        "Hidalgo",
        "Jalisco",
        "Edo. Mex.",
        "Michoacán",
        "Morelos",
        "Nayarit",
        "Nuevo León",
        "Oaxaca",
        "Puebla",
        "Querétaro",
        "Quintana Roo",
        "San Luis Potosí",
        "Sinaloa",
        "Sonora",
        "Tabasco",
        "Tamaulipas",
        "Tlaxcala",
        "Veracruz",
        "Yucatán",
        "Zacatecas"

    )

    var vacio: Boolean = false
    var estado_usuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEspecificoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonImagen.setOnClickListener { requestPermissionImage() }
        binding.buttonPDF.setOnClickListener { requestPermissionFile() }
        //binding.btnTerminar.setOnClickListener { saveRegister() }
        spinner()
        initializeComponents()
        listenerBtn()
    }


    /**
     * IMAGENES
     * Permisos de las imagenes
     */
    private fun requestPermissionImage() {
        // Verificaremos el nivel de API para solicitar los permisos
        // en tiempo de ejecución
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {

                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    pickPhotoFromGallery()
                }

                else -> requestPermissionLauncherImage.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else {
            // Se llamará a la función para APIs 22 o inferior
            // Esto debido a que se aceptaron los permisos
            // al momento de instalar la aplicación
            pickPhotoFromGallery()
        }
    }

    private val requestPermissionLauncherImage = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->

        if (isGranted){
            pickPhotoFromGallery()
        }else{
            Toast.makeText(
                this,
                "Permission denied",
                Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Elegir la foto
     **/
    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityResultImage.launch(intent)
    }

    private val startForActivityResultImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data?.data
            binding.imageViewRegister.setImageURI(data)

            //Cnvierte archivo Ui a ByteArray
            val inputData = data?.let { contentResolver.openInputStream(it)?.readBytes() }
            prefsRegister.saveImage(inputData)
        }
    }


    /**
     * Archivos
     * Permisos de la imagen
     */

    private fun requestPermissionFile() {
        // Verificaremos el nivel de API para solicitar los permisos
        // en tiempo de ejecución
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {

                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    pickFileFromFiles()
                }

                else -> requestPermissionLauncherFile.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else {
            // Se llamará a la función para APIs 22 o inferior
            // Esto debido a que se aceptaron los permisos
            // al momento de instalar la aplicación
            pickFileFromFiles()
        }
    }

    private val requestPermissionLauncherFile = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->

        if (isGranted){
            pickFileFromFiles()
        }else{
            Toast.makeText(
                this,
                "Permission denied",
                Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Elegir archivo
     **/
    private fun pickFileFromFiles() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startForActivityResultFile.launch(intent)
    }

    private val startForActivityResultFile = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data?.data

            //Cnvierte archivo Ui a byteArray
            val inputData = data?.let { contentResolver.openInputStream(it)?.readBytes() }
            prefsRegister.savePortafolio(inputData)
        }
    }

    /**
     * Spiner para que el usuario seleccione su estado
     **/
    fun spinner(){
        val context = this

        // add a hint to spinner
        // list first item will show as hint
        list.add(0,"Estado...")

        // initialize an array adapter for spinner
        val adapter: ArrayAdapter<String> = object: ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            list
        ){
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {



                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == spinner.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#f25f8d"))
                }

                // set selected item style
                if (position != spinner.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#FFFFFF"))
                    view.setTextColor(Color.parseColor("#000000"))
                }

                // make hint item color gray
                if(position == 0){
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }

        // finally, data bind spinner with adapter
        spinner.adapter = adapter


        // spinner on item selected listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                // by default spinner initial selected item is first item
                if (position != 0){
                    vacio = true
                    estado_usuario = list[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
                vacio = false
            }
        }
    }

    fun initializeComponents(){
        btnTerminar = findViewById(R.id.btnTerminar)

    }

    /**
     *Botón, verifica las restricciones que se tienen
     **/
    fun listenerBtn(){

        btnTerminar.setOnClickListener{
            //Variables
            val descripcion = binding.descripcion.text.toString()
            var estado = estado_usuario
            val image = prefsRegister.getImage()
            val file = prefsRegister.getPortafolio()

            if(descripcion.isEmpty()){
                val text = "Te falta escribir una descripcion"
                val duration = Toast.LENGTH_SHORT

                // Mensaje de error con toast
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }else if(image.isEmpty() || file.isEmpty()){
                val text = "Te falta tu FOTO/PDF"
                val duration = Toast.LENGTH_SHORT

                // Mensaje de error con toast
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }else if (!vacio){
                val text = "Te seleccionar tu estado"
                val duration = Toast.LENGTH_SHORT

                // Mensaje de error con toast
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
            else{
                /**
                 *Aquí se guardan las variables con PREFS
                 **/
                prefsRegister.saveEstado(estado)
                prefsRegister.saveDescricpion(descripcion)

                //Registramos la sesion
                signUp(prefsRegister.getUserName(),prefsRegister.getContra(),prefsRegister.getEmail())
            }


        }


    }

}