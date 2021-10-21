package com.itesm.ic2007b.proyecto

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LoginCheckTest{

    @Test
    fun `Campos vacios returns false`(){
        val result = LoginCheck.checkValues(
            "",
            ""
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `Campo de usuario vacio returns false`(){
        val result = LoginCheck.checkValues(
            "Aa123#",
            ""
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `Campo de contraseña vacio returns false`(){
        val result = LoginCheck.checkValues(
            "",
            "User"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `Campos llenos returns true`(){
        val result = LoginCheck.checkValues(
            "User",
            "Aa123#"
        )

        assertThat(result).isTrue()
    }
}