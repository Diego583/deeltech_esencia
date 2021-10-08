package com.itesm.ic2007b.proyecto

import android.app.Application
import com.parse.Parse

class App:Application() {

    //Instancia de objeto de preferencias
    //Para guardar variables globales
    companion object{
        lateinit var prefs:Prefs
    }

    override fun onCreate() {
        super.onCreate()

        App.prefs = Prefs(applicationContext)

        //Se guardan los datos de la base de datos
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }
}