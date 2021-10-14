package com.itesm.ic2007b.proyecto

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity() {
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        backForgot.setOnClickListener{
            finish()
        }

        enviar.setOnClickListener {
            val user = ParseUser()
            ParseUser.requestPasswordReset(resetContraEmail.text.toString());

            val  builder = AlertDialog.Builder(this)
            builder.setTitle("¡Listo!")
            builder.setMessage("Revisa tu correo para restablecer tu contraseña.")
            builder.setPositiveButton("OK",{ dialogInterface: DialogInterface, i: Int ->
                finish()
            })
            builder.show()
        }
    }
}