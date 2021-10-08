package com.itesm.ic2007b.proyecto

import android.app.Application
import com.parse.Parse

class App:Application() {

    //Instancia de objetos de preferencias
    //Para guardar variables globales
    companion object{
        lateinit var prefsRegister:PrefsRegister
        lateinit var prefsUser: PrefsUser
    }

    override fun onCreate() {
        super.onCreate()

        App.prefsRegister = PrefsRegister(applicationContext)
        App.prefsUser = PrefsUser(applicationContext)

        //Se guardan los datos de la base de datos
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }
}