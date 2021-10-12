package com.itesm.ic2007b.proyecto

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.itesm.ic2007b.proyecto.App.Companion.prefsRegister
import com.itesm.ic2007b.proyecto.databinding.ActivityRegistroEspecificoBinding
import com.parse.ParseFile
import com.parse.ParseObject

class RegistroEspecifico : AppCompatActivity() {
    // Instancia del view binding
    private lateinit var binding: ActivityRegistroEspecificoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEspecificoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonImagen.setOnClickListener { requestPermissionImage() }
        binding.buttonPDF.setOnClickListener { requestPermissionFile() }
        binding.buttonRegistrarse.setOnClickListener { saveRegister() }
        initializeBackRegistroEspecifico()
    }

    //Guarda el registro en la base de datos
    private fun saveRegister() {
        val imageString = prefsRegister.getImage()
        val fileString = prefsRegister.getPortafolio()

        val imageByteArray: ByteArray = Base64.decode(imageString, Base64.DEFAULT)
        val fileByteArray: ByteArray = Base64.decode(fileString, Base64.DEFAULT)

        //Se guarda archivo en la base de datos
        val file = ParseFile("file.pdf", fileByteArray)
        val image = ParseFile("imagen.jpg", imageByteArray)
        val jobApplication = ParseObject("JobApplication")
        jobApplication.put("applicantName", "emilio")
        jobApplication.put("Portafolio", file)
        jobApplication.put("FotoPerfil", image)
        jobApplication.saveInBackground()

        //Limpiamos el storage de la aplicacion
        prefsRegister.clearAllData()

        //Cerramos la aplicacion
        finish()
    }

    private fun initializeBackRegistroEspecifico() {
        binding.backRegister.setOnClickListener{
            finish()
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
}