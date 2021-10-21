package com.itesm.ic2007b.proyecto

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.squareup.picasso.Picasso

/**
 * Adapter para insertar elementos de tipo Card en el Grid.
 * Los elementos que se insertan se obtiene de un array de objetos obtenidos de la base de datos.
 * La función principal del adaptador en GridView es obtener datos del array
 * e insertar cada dato en un elemento Card que se mostrará en GridView.
 * @param Context
 * @param MutableList<ParseUser>
 * **/
class ProfileAdapter(context: Context, ParseUserList: MutableList<ParseUser>) :
    ArrayAdapter<ParseUser?>(context, 0, ParseUserList!! as MutableList<ParseUser?>) {


    /**
     * Función principal del adaptador.
     * Devuelve el View que se inserta en el grid usando el elemento adaptador.
     * @param Int
     * @param View?
     * @param ViewGroup
     * @return View
     * **/
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.v("pos", position.toString())
        var listitemView: View? = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(
                com.itesm.ic2007b.proyecto.R.layout.card_grid_item, parent, false)
        }
        val user: ParseUser? = getItem(position)
        val card: CardView? = listitemView?.findViewById(
            com.itesm.ic2007b.proyecto.R.id.card)
        val cardTVnombre: TextView? = listitemView?.findViewById(
            com.itesm.ic2007b.proyecto.R.id.idTV_card_nombre)
        val cardTVdesc: TextView? = listitemView?.findViewById(
            com.itesm.ic2007b.proyecto.R.id.idTV_card_desc)
        val cardIV: ImageView? = listitemView?.findViewById(
                com.itesm.ic2007b.proyecto.R.id.idIV_card)

        if (cardTVnombre != null) {
            if (user != null) {
                cardTVnombre.setText(user.username)
            }
        }
        if (cardTVdesc != null) {
            if (user != null) {
                cardTVdesc.setText(user.descripcion)
            }
        }

        val query1 = ParseQuery.getQuery<ParseObject>("ArchivosUsuario")
        query1.whereEqualTo("username", user?.username)
        query1.findInBackground { userFiles, e ->
            if (e == null) {
                val docPDF: String? = userFiles[0].getParseFile(LLAVE_DOCPDF)?.url
                val foto: String? = userFiles[0].getParseFile(LLAVE_FOTOPERFIL)?.url

                if (cardIV != null) {
                    val imageUri: Uri = Uri.parse(foto)
                    Picasso.with(context).load(imageUri.toString()).into(cardIV)
                }


                if (card != null) {
                    card.setOnClickListener {

                        //context.startActivity(Intent(context, OtroPerfil::class.java))
                        var intent = Intent(context, OtroPerfil::class.java)
                        if (user != null) {
                            intent.putExtra("objectId", user.objectId)
                            intent.putExtra("fotoPerfil", foto)
                            intent.putExtra("username", user.username)
                            intent.putExtra("ubicacion", user.ubicacion)
                            intent.putExtra("descripcion", user.descripcion)
                            intent.putExtra("phone", user.phone)
                            intent.putExtra("email", user.emailCopy)
                            intent.putExtra("docPDF", docPDF)
                        }
                        context.startActivity(intent)
                    }
                }


            } else {
                val text = "Error cargando datos"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
        }

        return listitemView!!
    }
}