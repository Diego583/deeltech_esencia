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
val ParseUser.fotoPerfil: ParseFile?
    get() = getParseFile(LLAVE_FOTOPERFIL)

val LLAVE_DOCPDF: String = "docPDF"
val ParseUser.docPDF: ParseFile?
    get() = getParseFile(LLAVE_DOCPDF)

val LLAVE_ROLES: String = "roles"
val ParseUser.roles: String?
    get() = getString(LLAVE_ROLES)

val LLAVE_PHONE: String = "phone"
val ParseUser.phone: String?
    get() = getString(LLAVE_PHONE)