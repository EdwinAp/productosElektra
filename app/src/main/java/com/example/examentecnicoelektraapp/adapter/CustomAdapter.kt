package com.example.examentecnicoelektraapp.adapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.examentecnicoelektraapp.ProductsQuery
import com.example.examentecnicoelektraapp.R
import com.example.examentecnicoelektraapp.pojos.Producto

class CustomAdapter (private val context: Activity, private val productos : List<ProductsQuery.Product>?) : ArrayAdapter<ProductsQuery.Product>(context, R.layout.productoitem, productos){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //return super.getView(position, convertView, parent)

        val inflater = context.layoutInflater

        val rowView = inflater.inflate(R.layout.productoitem, null, true)
        val name = rowView.findViewById(R.id.nameproduct) as TextView
        val price = rowView.findViewById(R.id.currentprice) as TextView
        val lasprice = rowView.findViewById(R.id.lastprice) as TextView
        var image = rowView.findViewById(R.id.fotoproducto) as ImageView
        val semanal = rowView.findViewById(R.id.semanal) as TextView
        name.text = productos?.get(position)?.name()
        price.text = "$${productos?.get(position)?.price().toString()}"
        if (productos?.get(position)?.lastPrice() != null) lasprice.text = "De $${productos?.get(position)?.lastPrice().toString()}"
        else lasprice.text = "De $${productos?.get(position)?.price().toString()}"
        if (productos?.get(position)?.lastPrice() != null) semanal.text = "$${String.format("%.2f", productos?.get(position)?.lastPrice()!!/48)}/48 Semanas"
        else semanal.text = "$${String.format("%.2f", productos?.get(position)?.price()!!/48)}/48 Semanas"
        Glide.with(context).load(productos?.get(position)?.image().toString()).into(image)
        //Log.e("Objecto: ", productos?.get(position).toString())
        return rowView
    }

}