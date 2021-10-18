package com.itesm.ic2007b.proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parse.GetCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_restauradores.*


class Restauradores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restauradores)

        var intent: Intent? = null

        initializeBackResta()



    }

    private fun initializeBackResta() {
        backRestauradores.setOnClickListener{
            finish()
        }
    }
}