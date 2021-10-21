package com.itesm.ic2007b.proyecto

import java.util.regex.Pattern

object UserRegisterCheck {

    // Código para verificar características de la contraseña en el XML
    val ReglaDigit = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +             //Al menos un digito
                ".{1,}" +
                "$"
    )
    val ReglaMinus = Pattern.compile(
        "^" +
                "(?=.*[a-z])" +             //Al menos 1 letra minuscula
                ".{1,}" +
                "$"
    )
    val ReglaMayus = Pattern.compile(
        "^" +
                "(?=.*[A-Z])" +             //Al menos 1 letra mayuscula
                ".{1,}" +
                "$"
    )
    val ReglaSimbolo = Pattern.compile(
        "^" +
                "(?=.*[@#$%^&+=])" +        //Al menos 1 simbolo especial
                ".{1,}" +
                "$"
    )
    val ReglaChar = Pattern.compile(
        "^" +
                ".{4,}" +                   //al menos 4 caracteres
                "$"
    )

    /**
     * Verifica que la contraseña contenga al menos un dígito
     *
     * @param pass -> Contraseña
     * @return boolean -> verdadero si contiene un dígito, de lo contrario false
     * */
    fun checkDigit(pass: String): Boolean{
        if(ReglaDigit.matcher(pass).matches()){
            return true
        }
        if (!ReglaDigit.matcher(pass).matches()){
            return false
        }

        return false
    }

    /**
     * Verifica que la contraseña contenga al menos una mayúscula
     *
     * @param pass -> Contraseña
     * @return boolean -> verdadero si contiene una mayúscula, de lo contrario false
     * */
    fun checkMayus(pass: String): Boolean{
        if(ReglaMayus.matcher(pass).matches()){
            return true
        }
        if (!ReglaMayus.matcher(pass).matches()){
            return false
        }

        return false
    }

    /**
     * Verifica que la contraseña contenga al menos una minúscula
     *
     * @param pass -> Contraseña
     * @return boolean -> verdadero si contiene una minúscula, de lo contrario false
     * */
    fun checkMinus(pass: String): Boolean{
        if(ReglaMinus.matcher(pass).matches()){
            return true
        }
        if (!ReglaMinus.matcher(pass).matches()){
            return false
        }

        return false
    }

    /**
     * Verifica que la contraseña contenga al menos un símbolo especial
     *
     * @param pass -> Contraseña
     * @return boolean -> verdadero si contiene un símbolo, de lo contrario false
     * */
    fun checkSymbol(pass: String): Boolean{
        if(ReglaSimbolo.matcher(pass).matches()){
            return true
        }
        if (!ReglaSimbolo.matcher(pass).matches()){
            return false
        }

        return false
    }

    /**
     * Verifica que la contraseña sea de al menos 4 caracteres
     *
     * @param pass -> Contraseña
     * @return boolean -> verdadero si contiene los caracteres mínimos, de lo contrario false
     * */
    fun checkChar(pass: String): Boolean{
        if(ReglaChar.matcher(pass).matches()){
            return true
        }
        if(!ReglaChar.matcher(pass).matches()){
            return false
        }

        return false
    }

    //--------------------------------------------------------------------

    // Código para comprobar el registro

    //Requisitos para las contraseñas
    val passwordRegex = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +             //Al menos un digito
                "(?=.*[a-z])" +             //Al menos 1 letra minuscula
                "(?=.*[A-Z])" +             //Al menos 1 letra mayuscula
                "(?=.*[@#$%^&+=])" +        //Al menos 1 simbolo especial
                "(?=\\S+$)" +               //no espacios
                ".{4,}" +                   //al menos 4 caracteres
                "$"

    )

    /**
     * Revisa si el registro contiene campos vacíos
     *
     * @param pass -> Contraseña
     * @param email -> Contraseña
     * @param pass2 -> Contraseña
     * @param user -> Contraseña
     * @return boolean -> verdadero si hay campos vacíos, de lo contrario false
     * */
    fun emptyRegister(pass:String, email:String, pass2:String, user:String): Boolean{
        if(pass == "" || email == "" || pass2 == "" || user == ""){
            return true
        }
        return false
    }

    /**
     * Verifica que la contraseña cumpla con todas las características mínimas
     *
     * @param pass -> Contraseña
     * @return boolean -> verdadero si cumple las características, de lo contrario false
     * */
    fun strongPass(pass: String): Boolean{
        if(passwordRegex.matcher(pass).matches()){
            return true
        }
        return false
    }

    /**
     * Revisa si la contraseña y su verificación son diferentes
     *
     * @param pass -> Contraseña
     * @param pass2 -> Contraseña de verificación
     * @return boolean -> verdadero si no son idénticas, de lo contrario false
     * */
    fun eqPass(pass:String, pass2:String): Boolean{
        if (pass != pass2){
            return true
        }
        return false
    }


}