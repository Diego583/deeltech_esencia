package com.itesm.ic2007b.proyecto

import android.graphics.drawable.Drawable
import com.parse.ParseFile
import com.parse.ParseUser

val LLAVE_UBICACION : String = "ubicacion"
val ParseUser.ubicacion: String?
    get() = getString(LLAVE_UBICACION)

val LLAVE_DESCRIPCION : String = "descripcion"
val ParseUser.descripcion: String?
    get() = getString(LLAVE_DESCRIPCION)

val LLAVE_FOTOPERFIL: String = "fotoPerfil"
val ParseUser.fotoPerfil: String?
    get() = getString(LLAVE_FOTOPERFIL)

val LLAVE_DOCPDF: String = "docPDF"
val ParseUser.docPDF: String?
    get() = getString(LLAVE_DOCPDF)