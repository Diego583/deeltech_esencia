package com.itesm.ic2007b.proyecto

import android.content.Context
import com.parse.ParseUser

/**
 *Clase para persistencia de datos
 *AQUÍ PODEMOS MANTENER LOS DATOS GLOBALES del usuario logeado
 * se guarda y se obtiene el dato en cualquier archivo .kt
 * @param context
 **/
class PrefsUser(val context: Context) {

    val SHARED_NAME = "LoginUser"

    //VARIABLES QUE HAREMOS PERCISTENTES begins

    val SHARED_USER_NAME = "username"
    val SHARED_EMAIL = "email"
    val SHARED_PASS = "password"
    val SHARED_NUMERO = "numero"
    val SHARED_ROL = "Rol"

    //VARIABLES QUE HAREMOS PERCISTENTES ends

    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    val listFavoritos:ArrayList<String> = ArrayList()

    var isInFavoritos:Boolean = false

    /**
     * Libera todo lo guardado en storage
     **/
    fun clearAllData(){
        storage.edit().clear().apply()
    }

    /**
     * ¿El usuario esta en favoritos?
     **/
    fun entreFavoritos(){
        isInFavoritos = true
    }
    fun saliFavoritos(){
        isInFavoritos = false
    }
    fun statusFavoritos():Boolean{
        return isInFavoritos
    }


    /**
     * Aquí se guarda o se obtiene el valor del USUARIO
     **/
    fun saveUserName(name:String){
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }
    fun getUserName():String{
        return storage.getString(SHARED_USER_NAME, "")!!
    }

    /**
     * Aquí se guarda o se obtiene la lista de favoritos
     **/
    fun saveUserFavoritos(name:String){
        listFavoritos.add(name)
    }
    fun getFavoritos(): ArrayList<String> {
       return listFavoritos
    }
    fun removeUserFavoritos(name:String){
        listFavoritos.remove(name)
    }
    fun clearFavoritos(){
        listFavoritos.clear()
    }

    /**
     * Aquí se guarda o se obtiene el valor del EMAIL
     **/
    fun saveEmail(name:String){
        storage.edit().putString(SHARED_EMAIL, name).apply()
    }
    fun getEmail():String{
        return storage.getString(SHARED_EMAIL, "")!!
    }


    /**
     * Aquí se guarda o se obtiene el valor del CONTRASEÑA
     **/
    fun saveContra(name:String){
        storage.edit().putString(SHARED_PASS, name).apply()
    }
    fun getContra():String{
        return storage.getString(SHARED_PASS, "")!!
    }


    /**
     * Aquí se guarda o se obtiene el valor del NUMERO
     **/
    fun saveNumero(name:String){
        storage.edit().putString(SHARED_NUMERO, name).apply()
    }
    fun getNumero():String{
        return storage.getString(SHARED_NUMERO, "")!!
    }


    /**
     * Aquí se guarda o se obtiene el valor del ROL
     **/
    fun saveRol(name:String){
        storage.edit().putString(SHARED_ROL, name).apply()
    }
    fun getRol():String{
        return storage.getString(SHARED_ROL, "")!!
    }
}