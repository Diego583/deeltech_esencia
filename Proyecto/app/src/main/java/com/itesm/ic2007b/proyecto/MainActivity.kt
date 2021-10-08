package com.itesm.ic2007b.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.ParseObject
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstObject = ParseObject("FirstClass") //NOMBRE DE TABLA

        firstObject.put("message","Hey ! First message from android. Parse is now connected") //COLUMNA Y DATO DE COLUMNA

        firstObject.saveInBackground {
            if (it != null){ //SI HAY ERRROR
                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
            }else{//SI NO HAY ERROR
                Log.d("MainActivity","Object saved.")
            }

        }
    }

}