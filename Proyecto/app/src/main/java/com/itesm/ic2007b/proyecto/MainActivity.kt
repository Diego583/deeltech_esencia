package com.itesm.ic2007b.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.FindCallback

import com.parse.ParseQuery




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
        val states = ParseObject("Estados") //NOMBRE DE TABLA
        states.put("Estados", "Baja California Sur")

        states.saveInBackground {
            if (it != null){ //SI HAY ERRROR
                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
            }else{//SI NO HAY ERROR
                Log.d("MainActivity","Object saved.")
            }

        }**/

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("username", "tiburon")
        query.findInBackground { user, e ->
            if (e == null) {

                val text = "SE encontró el usuario " + user[0]
                val duration = Toast.LENGTH_SHORT

                // Mensaje de exito con toast
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            } else {
                val text = "NO se encontró el usuario"
                val duration = Toast.LENGTH_SHORT

                // Mensaje de error con toast
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

            }
        }




    }

}