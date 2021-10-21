package com.itesm.ic2007b.proyecto

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserRegisterCheckTest{

    // Tests características individuales de la contraseña
    @Test
    fun `Contrasena sin digitos returns false`(){
        val result = UserRegisterCheck.checkDigit("Aa#")

        assertThat(result).isFalse()
    }

    @Test
    fun `Contrasena con digitos returns true`(){
        val result = UserRegisterCheck.checkDigit("Aa123#")

        assertThat(result).isTrue()
    }

    @Test
    fun `Contrasena sin mayusculas returns false`(){
        val result = UserRegisterCheck.checkMayus("a123#")

        assertThat(result).isFalse()
    }

    @Test
    fun `Contrasena con mayusculas returns true`(){
        val result = UserRegisterCheck.checkMayus("Aa123#")

        assertThat(result).isTrue()
    }

    @Test
    fun `Contrasena sin minusculas returns false`(){
        val result = UserRegisterCheck.checkMinus("A123#")

        assertThat(result).isFalse()
    }

    @Test
    fun `Contrasena con minusculas returns true`(){
        val result = UserRegisterCheck.checkMinus("Aa123#")

        assertThat(result).isTrue()
    }

    @Test
    fun `Contrasena sin simbolos returns false returns true`(){
        val result = UserRegisterCheck.checkSymbol("Aa123")

        assertThat(result).isFalse()
    }

    @Test
    fun `Contrasena con simbolos returns true`(){
        val result = UserRegisterCheck.checkSymbol("Aa123#")

        assertThat(result).isTrue()
    }

    @Test
    fun `Contrasena que no tiene 4 caracteres minimos returns false`(){
        val result = UserRegisterCheck.checkChar("A1#")

        assertThat(result).isFalse()
    }

    @Test
    fun `Contrasena que tiene 4 caracteres o mas returns true`(){
        val result = UserRegisterCheck.checkChar("Aa123#")

        assertThat(result).isTrue()
    }

    //--------------------------------------------------------------------

    // Tests para registro completo
    @Test
    fun `Registro con campos vacios returns true`(){
        val result = UserRegisterCheck.emptyRegister(
            "",
            "",
            "",
            "")

        assertThat(result).isTrue()
    }

    @Test
    fun `Registro con campos llenos returns false`(){
        val result = UserRegisterCheck.emptyRegister(
            "Aa123#",
            "correo@gmail.com",
            "Aa123#",
            "Usuario")

        assertThat(result).isFalse()
    }

    @Test
    fun `Contrasena debil que no cumple todas las caracteristicas returns false`(){
        val result = UserRegisterCheck.strongPass("a")

        assertThat(result).isFalse()
    }

    @Test
    fun `Contrasena fuerte que cumple todas las caracteristicas returns true`(){
        val result = UserRegisterCheck.strongPass("Aa123#")

        assertThat(result).isTrue()
    }

    @Test
    fun `Contrasena no repetida correctamente returns true`(){
        val result = UserRegisterCheck.eqPass("Aa123#", "Aa321#")

        assertThat(result).isTrue()
    }

    @Test
    fun `Contrasena repetida correctamente returns false`(){
        val result = UserRegisterCheck.eqPass("Aa123#", "Aa123#")

        assertThat(result).isFalse()
    }
}