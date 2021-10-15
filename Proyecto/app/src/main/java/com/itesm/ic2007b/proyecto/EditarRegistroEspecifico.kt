package com.itesm.ic2007b.proyecto

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.itesm.ic2007b.proyecto.App.Companion.prefsUser
import com.itesm.ic2007b.proyecto.databinding.ActivityEditarRegistroEspecificoBinding
import com.parse.*
import kotlinx.android.synthetic.main.activity_registro_especifico.*


import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.databinding.ActivityRegistroEspecificoBinding
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_mi_perfil.*
import kotlinx.android.synthetic.main.activity_registro_especifico.*
import kotlinx.android.synthetic.main.activity_registro_especifico.spinner
import kotlinx.android.synthetic.main.activity_roles.*


import kotlinx.android.synthetic.main.activity_mi_perfil.*


class EditarRegistroEspecifico : AppCompatActivity() {

    //Variable para poder conectar .XML a .KT
    private lateinit var binding : ActivityEditarRegistroEspecificoBinding

    // list of spinner items
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
    var estado_usuario: String = "" //Aquí se guarda el estado del usuario en SPINNER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarRegistroEspecificoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonImagen.setOnClickListener { requestPermissionImage() }
        binding.buttonPDF.setOnClickListener { requestPermissionFile() }

        datosRegistrados()
        spinner()
        listenerBtn()

    }


    /**
     * IMAGENES
     */

    //Permisos de la imagen
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

    //Elegir foto
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
     */

    //Permisos de la imagen
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

    // Elegir archivo
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
                    Toast.makeText(this@EditarRegistroEspecifico, "Entonces seras un "+list[position], Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
                vacio = false
            }
        }
    }


    /**
     *AQUÍ SE AGREGA EL PEDO
     **/
    fun listenerBtn(){
        binding.btnTerminar.setOnClickListener{

            intent = Intent(this, MiPerfil::class.java)
            startActivity(intent)
            finish()

        }
    }


    /**
     *Se hace una query para revisar cuales fueron
     *los datos que el usuario puso en su registro
     **/
    fun datosRegistrados(){
        val nombreG = prefsUser.getUserName()

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("username", nombreG)
        query.findInBackground { user, e ->
            if (e == null) {
                val foto: String? = user[0].getParseFile(LLAVE_FOTOPERFIL)?.url
                var descripcion: String = user[0].descripcion.toString()
                val docPDF : String? = user[0].getParseFile(LLAVE_DOCPDF)?.url
                val contra: String = user[0].password.toString()
                val numero: String = user[0].phone.toString()

                displayData(foto, nombreG, descripcion, docPDF, contra, numero)
            } else {
                val text = "Error cargando datos"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

        }
    }


    /**
     * Se muestran los datos resultantes de la funcion datosRegistrados()
     */
    fun displayData(fotoUrl:String?, nombre:String, descripcion:String, docUrl:String?,
                    contra:String, numero:String) {

        val fotoPerfilImageView: ImageView = findViewById(R.id.imageViewRegister)
        val descripcionTextView: EditText = findViewById(R.id.descripcion)
        //val docButton: Button = findViewById(R.id.button_abrir_pdf)


        val imageUri: Uri = Uri.parse(fotoUrl)
        Picasso.with(this).load(imageUri.toString()).into(fotoPerfilImageView)

        binding.descripcion.setText(descripcion)

    }



}