package com.itesm.ic2007b.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_restauradores.*

import android.widget.GridView


class Restauradores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restauradores)

        var intent: Intent? = null

        val query: ParseQuery<ParseUser> = ParseQuery.getQuery(ParseUser::class.java)
        query.whereEqualTo("roles", "Restaurador")
        query.findInBackground { user, e ->
            if (e == null) {
                displayRestauradores(user)
            } else {
                val text = "Error cargando Restauradores"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

            initializeBackResta()
        }
    }


    private fun displayRestauradores(user: MutableList<ParseUser>) {
        val grid:GridView = findViewById(R.id.grid_res)
        val adapter = ProfileAdapter(this, user)
        grid.setAdapter(adapter)
    }

    private fun initializeBackResta() {
        backRestauradores.setOnClickListener{
            finish()
        }
    }
}