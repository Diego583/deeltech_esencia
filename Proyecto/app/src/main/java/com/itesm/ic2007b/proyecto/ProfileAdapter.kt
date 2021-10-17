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
import androidx.cardview.widget.CardView
import com.parse.ParseUser
import com.squareup.picasso.Picasso


class ProfileAdapter(context: Context, ParseUserList: MutableList<ParseUser>) :
    ArrayAdapter<ParseUser?>(context, 0, ParseUserList!! as MutableList<ParseUser?>) {



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
        if (cardIV != null) {
            val imageUri: Uri = Uri.parse(user?.fotoPerfil?.url)
            Picasso.with(context).load(imageUri.toString()).into(cardIV)
        }


        if (card != null) {
            card.setOnClickListener {

                //context.startActivity(Intent(context, OtroPerfil::class.java))
                var intent = Intent(context, OtroPerfil::class.java)
                if (user != null) {
                    intent.putExtra("fotoPerfil",user?.fotoPerfil?.url)
                    intent.putExtra("username",user.username)
                    intent.putExtra("ubicacion",user.ubicacion)
                    intent.putExtra("descripcion",user.descripcion)
                    intent.putExtra("phone",user.phone)
                    intent.putExtra("email", user.email)
                    intent.putExtra("docPDF",user?.docPDF?.url)
                }
                context.startActivity(intent)
            }
        }
        return listitemView!!
    }
}