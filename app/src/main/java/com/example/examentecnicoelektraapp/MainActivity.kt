package com.example.examentecnicoelektraapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.bumptech.glide.Glide
import com.example.examentecnicoelektraapp.adapter.CustomAdapter
import com.example.examentecnicoelektraapp.pojos.Producto
import com.example.examentecnicoelektraapp.slider.PagerAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var productos : List<ProductsQuery.Product>? = null
    private var count : Int = 1

    // https://stage.ektdevelopers.com/_graphql
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        //rclViewLanding.layoutManager = GridLayoutManager(this, 2) //as RecyclerView.LayoutManager?
        var activity_ = this
        var apolloClient =  ApolloClient.builder().serverUrl("https://stage.ektdevelopers.com/_graphql").build()
        apolloClient.query(
            ProductsQuery.builder()
                .skus(Arrays.asList(
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
                ))
                .build()
        ).enqueue(object: ApolloCall.Callback<ProductsQuery.Data>() {
            override fun onResponse(response: Response<ProductsQuery.Data>) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                //Log.e("Respues Remote: ${response.data()?.viewer()?.products?.size}", response.data().toString())
                productos = response.data()?.viewer()?.products()
                var productos_ = listOf<Producto>()
                productos?.map {
                    if (it != null){
                        productos_.plus(
                            Producto(
                                it.id.toString(),
                                it.name.toString(),
                                it.image.toString(),
                                it.price.toString(),
                                it.lastPrice.toString()
                            )
                        )
                    }
                }
                Log.e("Cantidad pasada: ", productos?.size.toString())
                Thread(kotlinx.coroutines.Runnable {
                    this@MainActivity.runOnUiThread(Runnable {
                        listview.adapter = CustomAdapter(activity_, productos)
                    })
                }).start()
                Thread(kotlinx.coroutines.Runnable {
                    this@MainActivity.runOnUiThread(Runnable {
                        var adapterSlide = PagerAdapter(activity_, productos)
                        slider.adapter = adapterSlide
                    })
                }).start()
                //productos?.forEach { producto -> Log.e("Producto: ${producto.name}", producto.toString()) }

            }
            override fun onFailure(e: ApolloException) {
                //Snackbar.make(, "Error Productos: ${e.message}", Snackbar.LENGTH_LONG).setAction("Error", null).show()
            }

        });




    }

    fun loadList(productos : List<ProductsQuery.Product>?) {
        var productos_ = listOf<Producto>()
        productos?.map {
            if (it != null){
                productos_.plus(
                    Producto(
                        it.id.toString(),
                        it.name.toString(),
                        it.image.toString(),
                        it.price.toString(),
                        it.lastPrice.toString()
                    )
                )
            }
        }
        Log.e("Cantidad pasada: ", productos_.size.toString())
        Thread(kotlinx.coroutines.Runnable { listview.adapter = CustomAdapter(this, productos) })
        /*
        Thread(kotlinx.coroutines.Runnable {
            this@MainActivity.runOnUiThread(Runnable {
                listview.adapter = AdapterProduct(this, productos_)
            })
        }).start()
        */
        //listview.adapter = AdapterProduct(this, productos_)
    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

 */

}
