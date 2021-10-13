package com.itesm.ic2007b.proyecto

import android.content.Context
import java.nio.charset.StandardCharsets
import android.R.array
import android.util.Base64


/**
 *Clase para persistencia de datos
 *AQUÍ PODEMOS MANTENER LOS DATOS GLOBALES del usuario que se registra
 * se guarda y se obtiene el dato en cualquier archivo .kt
 **/

class PrefsRegister(val context:Context) {


    val SHARED_NAME = "MyDB1"

    //VARIABLES QUE HAREMOS PERCISTENTES begins

    val SHARED_USER_NAME = "username"
    val SHARED_EMAIL = "email"
    val SHARED_PASS = "password"
    val SHARED_NUMERO = "numero"
    val SHARED_IMAGE = "imagen"
    val SHARED_PDF = "archivo"
    val SHARED_ROL = "Rol"
    val SHARED_DESCRIPCION = "descripcion"
    val SHARED_ESTADO = "estado"

    //VARIABLES QUE HAREMOS PERCISTENTES ends

    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    /**
     * Libera todo lo guardao en storage
     **/
    fun clearAllData(){
        storage.edit().clear().apply()
    }


    /**
     * Aquí se guarda o se obtiene el valor del USUARIO
     * @param name
     * @return name
     **/
    fun saveUserName(name:String){
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }
    fun getUserName():String{
        return storage.getString(SHARED_USER_NAME, "")!!
    }


    /**
     * Aquí se guarda o se obtiene el valor del EMAIL
     * @param email
     * @return email
     **/
    fun saveEmail(name:String){
        storage.edit().putString(SHARED_EMAIL, name).apply()
    }
    fun getEmail():String{
        return storage.getString(SHARED_EMAIL, "")!!
    }


    /**
     * Aquí se guarda o se obtiene el valor del CONTRASEÑA
     * @param contraseña
     * @return contraseña
     **/
    fun saveContra(name:String){
        storage.edit().putString(SHARED_PASS, name).apply()
    }
    fun getContra():String{
        return storage.getString(SHARED_PASS, "")!!
    }


    /**
     * Aquí se guarda o se obtiene el valor del NUMERO
     * @param numero
     * @return numero
     **/
    fun saveNumero(name:String){
        storage.edit().putString(SHARED_NUMERO, name).apply()
    }
    fun getNumero():String{
        return storage.getString(SHARED_NUMERO, "")!!
    }


    /**
     * Aquí se guarda o se obtiene la imagen
     **/
    fun saveImage(image: ByteArray?){
        val imageString: String = Base64.encodeToString(image, Base64.DEFAULT)
        storage.edit().putString(SHARED_IMAGE, imageString).apply()
    }
    fun getImage():String{
        return storage.getString(SHARED_IMAGE, "")!!
    }

    /**
     * Aquí se guarda o se obtiene el PDF del portafolio
     **/
    fun savePortafolio(archivo: ByteArray?){
        val archivoString: String = Base64.encodeToString(archivo, Base64.DEFAULT)
        storage.edit().putString(SHARED_PDF, archivoString).apply()
    }
    fun getPortafolio():String{
        return storage.getString(SHARED_PDF, "")!!
    }


    /**
     * Aquí se guarda o se obtiene el valor del ROL
     * @param rol
     * @return rol
     **/
    fun saveRol(name:String){
        storage.edit().putString(SHARED_ROL, name).apply()
    }
    fun getRol():String{
        return storage.getString(SHARED_ROL, "")!!
    }


    /**
     * Aquí se guarda o se obtiene el valor de la DESCRIPCION
     * @param descripcion
     * @return descripcion
     **/
    fun saveDescricpion(name:String){
        storage.edit().putString(SHARED_DESCRIPCION, name).apply()
    }
    fun getDescricpion():String{
        return storage.getString(SHARED_DESCRIPCION, "")!!
    }

    /**
     * Aquí se guarda o se obtiene el valor de la ESTADO
     * @param estado
     * @return estado
     **/
    fun saveEstado(name:String){
        storage.edit().putString(SHARED_ESTADO, name).apply()
    }
    fun getEstado():String{
        return storage.getString(SHARED_ESTADO, "")!!
    }
}