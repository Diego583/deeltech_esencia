package com.itesm.ic2007b.proyecto

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.databinding.ActivityUserEditBinding
import com.parse.*
import kotlinx.android.synthetic.main.activity_mi_perfil.*
import kotlinx.android.synthetic.main.activity_user_edit.*

import com.parse.ParseFile
import com.parse.ParseObject

import com.parse.ParseQuery
import com.squareup.picasso.Picasso

/**
 * Acivity para editar el perfil del usuario
 * @author DeelTech
 **/
class UserEdit : AppCompatActivity() {

    /**
     * lista de estados para el spinner
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


    //Variable para poder conectar .XML a .KT
    private lateinit var binding : ActivityUserEditBinding

    override fun onBackPressed() {
        prefsRegister.clearAllData()
        intent = Intent(this,MiPerfil::class.java)
        startActivity(intent)
        finish()
    }

    lateinit var dataUser:ArrayList<ParseUser>

    //onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Aqui se conecta ek .XML con el .KT
        binding = ActivityUserEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Seleccionar una imagen
        binding.buttonImagen3.setOnClickListener { requestPermissionImage() }
        binding.buttonPDF3.setOnClickListener { requestPermissionFile() }

        //Spiner de estados
        spinner()

        //Regresar a perfil
        initializeBackEditPerfil()


        //Usuario actual
        val nombreG = App.prefsUser.getUserName()

        /**
         * Querie para poder editar los datos del usuario resultante
         **/
        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("username", nombreG)
        query.findInBackground { user, e ->
            if (e == null) {
                val ubicacion: String = user[0].ubicacion.toString()
                val descripcion: String = user[0].descripcion.toString()
                val phone : String = user[0].phone.toString()
                val email : String = user[0].email.toString()

                val query1 = ParseQuery.getQuery<ParseObject>("ArchivosUsuario")
                query1.whereEqualTo("username", nombreG)
                query1.findInBackground { userFiles, e ->
                    if (e == null) {
                        val docPDF : String? = userFiles[0].getParseFile(LLAVE_DOCPDF)?.url
                        val foto: String? = userFiles[0].getParseFile(LLAVE_FOTOPERFIL)?.url
                        val userID : String = userFiles[0].objectId.toString()
                        //Desplegamos lo que esta guardado
                        displayData(nombreG, descripcion, phone, ubicacion, docPDF, foto)
                        //Validamos los datos del usuario
                        checkEditUser(nombreG,userID)
                        //Manda correo para cambiar contraseña
                        cambiarContraseñaCorreo(email)
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

    }

    private fun displayData(
        nombreG: String,
        descripcion: String,
        phone: String,
        ubicacion: String,
        docPDF: String?,
        foto: String?
    ) {
        /**
        //Mostrar imagen
        val imageUri: Uri = Uri.parse(foto)
        binding.imageViewRegister3.setImageURI(imageUri)
        val ImageInputData = imageUri?.let { contentResolver.openInputStream(it)?.readBytes() }
        prefsRegister.saveImage(ImageInputData)
        **/
        //Mostrar nombre de usuario
        binding.usuario.setText(nombreG)
        //Mostrar numero de telefono
        binding.numero.setText(phone)
        //Mostrar descripcion
        binding.descripcion3.setText(descripcion)
        //Mostar ubicacion
        estado_usuario = ubicacion
        /**
        //Guardamos PDF inicial
        val docUri: Uri = Uri.parse(docPDF)
        val docInputData = docUri?.let { contentResolver.openInputStream(it)?.readBytes() }
        App.prefsRegister.saveImage(docInputData)**/
    }

    /**
     * Aquí se llama la funcion de parse para poder cambiar la contraseña
     * posteriormente se manda un correo al usuario
     * @param email
     **/
    private fun cambiarContraseñaCorreo(email: String) {
        cambiarContraseña.setOnClickListener {
            ParseUser.requestPasswordReset(email);

            //Mensaje
            val  builder = AlertDialog.Builder(this)
            builder.setTitle("¡Listo!")
            builder.setMessage("Revisa tu correo para cambiar tu contraseña.")
            builder.setPositiveButton("OK",{ dialogInterface: DialogInterface, i: Int ->
                finish()
            })
            builder.show()
        }
    }



    /**
     * Se inicializa el botón de back
     **/
    private fun initializeBackEditPerfil() {
        binding.backEditarPerfil.setOnClickListener{
            prefsRegister.clearAllData()
            intent = Intent(this, MiPerfil::class.java)
            startActivity(intent)
            finish()
        }
    }


    /**
     * Con esta querie se saca si es que el usuario está en uso
     * @param nombreG
     * @param userID
     **/
    fun checkEditUser(nombreG: String, userID: String) {
        binding.buttonRegistrarse.setOnClickListener {
            //Nombre de usuario actual
            val nombreUsuario = binding.usuario.text.toString()
            dataUser = ArrayList()

            val query2: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
            query2.whereEqualTo("username", nombreUsuario)
            query2.findInBackground { user, e ->
                if (e == null) {

                    for(temp in user){
                        dataUser.add(temp)
                    }
                    verificarUsuario(dataUser,nombreG,nombreUsuario,userID)
                    //actualizarDatos(nombreG,email,userID)
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
     * Se verifican las restricciones qeu se tienen
     * @param UsuarioEncontrado
     * @param nombreG
     * @param nombreUsuario
     * @param userID
     **/
    fun verificarUsuario(
        UsuarioEncontrado: ArrayList<ParseUser>,
        nombreG: String,
        nombreUsuario: String,
        userID: String
    )
    {

        //val image = prefsRegister.getImage()
        //val file = prefsRegister.getPortafolio()

        if((UsuarioEncontrado.size > 0) && (nombreUsuario != nombreG)){
            val text = nombreUsuario + " no disponible, intenta con otro"
            val duration = Toast.LENGTH_SHORT

            binding.usuario.error = "Usuario no disponible, intenta con otro"
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
        /*else if(image.isEmpty() || file.isEmpty()){
            val text = "Te falta tu FOTO/PDF"
            val duration = Toast.LENGTH_SHORT

            // Mensaje de error con toast
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }*/
        else if (!vacio){
            val text = "Te seleccionar tu estado"
            val duration = Toast.LENGTH_SHORT

            // Mensaje de error con toast
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
        else{
            //Variables
            val descripcion = binding.descripcion3.text.toString()
            val numero = binding.numero.text.toString()
            val estado = estado_usuario

            /**val text = numero + "/" + descripcion + "/" + estado
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()**/

            val user = ParseUser.getCurrentUser()

            user.username = nombreUsuario
            user.put("phone", numero) //numero, se crea la columna numero y se guarda ahí
            user.put("descripcion", descripcion) //rol, se crea la columna roles y se guarda ahí
            user.put("ubicacion", estado) //rol, se crea la columna estado y se guarda ahí

            user.saveInBackground {
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
                    /**val ArchivosUsuario = ParseObject("ArchivosUsuario")

                    ArchivosUsuario.put("username", nombreUsuario)
                    ArchivosUsuario.put("docPDF", file) //Se guarda el portafolio
                    ArchivosUsuario.put("fotoPerfil", image) //Se guarda la foto de perfil
                    ArchivosUsuario.saveInBackground()**/

                    val query = ParseQuery.getQuery<ParseObject>("ArchivosUsuario")
                    query.whereEqualTo("objectId", userID)
                    query.getFirstInBackground { ArchivosUsuario, e ->
                        ArchivosUsuario.put("username", nombreUsuario)
                        ArchivosUsuario.put("docPDF", file) //Se guarda el portafolio
                        ArchivosUsuario.put("fotoPerfil", image) //Se guarda la foto de perfil
                        ArchivosUsuario.saveInBackground()
                    }


                    val  builder = AlertDialog.Builder(this)
                    builder.setTitle("¡Cuenta editada exitosamente!")
                    builder.setMessage("Vuelve a inciar sesión.")
                    builder.setPositiveButton("OK",{ dialogInterface: DialogInterface, i: Int ->
                        logOut()
                    })
                    builder.show()

                } else {
                    prefsRegister.clearAllData()

                    val text = "Intentalo más tarde" + it
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()

                    logOut()
                }
            }
        }
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
            binding.imageViewRegister3.setImageURI(data)

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
            binding.confirmacionPDF.setText("PDF SELECCIONADO")
        }
    }

    /**
     * Confiruacion del spinner dónde el usuario escoge su estado
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
                if (position == spinner1.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#f25f8d"))
                }

                // set selected item style
                if (position != spinner1.selectedItemPosition && position !=0 ){
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
        spinner1.adapter = adapter


        // spinner on item selected listener
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

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

    /**
     * Si el usuario logró editar su perfil sin problema se deberá logear de nuevo
     **/
    fun logOut(){
        ParseUser.logOutInBackground { e: ParseException? ->
            if (e == null)
                App.prefsUser.clearAllData()
            val text = "Vuelve pronto y recuerda donar!!!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()

            prefsRegister.clearAllData()
            App.prefsUser.clearFavoritos()
            intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}