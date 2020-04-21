package com.example.examentecnicoelektraapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.bumptech.glide.Glide
import com.example.examentecnicoelektraapp.adapter.CustomAdapter
import com.example.examentecnicoelektraapp.pojos.Producto
import com.example.examentecnicoelektraapp.slider.PagerAdapter
import com.synnapps.carouselview.ImageListener

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var productos : List<ProductsQuery.Product>? = null


    // https://stage.ektdevelopers.com/_graphql
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lupabuscar.setOnClickListener{
            Log.e("Entrada: ", buscarproducto.text.toString())
            loadProducts(buscarproducto.text.toString())
        }

        loadProducts(null)
    }

    private fun loadProducts(nameProduct: String?) {
        val activity_ = this
        val apolloClient =  ApolloClient.builder().serverUrl("https://stage.ektdevelopers.com/_graphql").build()
        apolloClient.query(
            ProductsQuery.builder()
                .skus(skuls )
                .build()
        ).enqueue(object: ApolloCall.Callback<ProductsQuery.Data>() {

            override fun onResponse(response: Response<ProductsQuery.Data>) {
                // Aqui agrego todas las tareas, cuando nos response el servidor  Log.e("Respues Remote: ${response.data()?.viewer()?.products?.size}", response.data().toString())
                if (nameProduct != null) productos = response.data()?.viewer()?.products()?.filter { it.name!!.toLowerCase().contains(nameProduct!!.toLowerCase()) }
                else productos = response.data()?.viewer()?.products()
                Log.e("Longutud ", productos?.size.toString())
                Thread(kotlinx.coroutines.Runnable {
                    this@MainActivity.runOnUiThread(Runnable(fun() {
                        listview.adapter = CustomAdapter(activity_, productos)
                        val adapterSlide = PagerAdapter(activity_, productos)
                        slider.adapter = adapterSlide
                    }))
                }).start()

            }
            override fun onFailure(e: ApolloException) {
                Snackbar.make(listview, "Error Productos: ${e.message}", Snackbar.LENGTH_LONG).setAction("Error", null).show()
            }

        })
    }

    var imageListener : ImageListener = object : ImageListener{

        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            productos?.map {
                if (imageView != null) {
                    Glide.with(applicationContext).load(it.image()).into(imageView)
                }
            }
        }

    }

    private val skuls = listOf(
        "321323142",
        "321323076",
        "45025344",
        "45024957",
        "1003678",
        "321321586",
        "45025337",
        "321323141",
        "45035651",
        "45048926",
        "1007046",
        "1006062",
        "1007073",
        "321324716",
        "1006025",
        "1005116",
        "1006593",
        "1006581",
        "1006945",
        "321321538"
    )

}
