package com.itesm.ic2007b.proyecto

object LoginCheck {

    /**
     * Recibe datos de login y verifica que no estén vacíos
     *
     * @param contra -> Contraseña
     * @param usuario -> Nombre de usuario
     * @return boolean -> verdadero si no está ningún campo vacío, de lo contrario false
     * */
    fun checkValues(contra: String, usuario: String): Boolean{
        return contra.isNotEmpty() && usuario.isNotEmpty()
    }

}