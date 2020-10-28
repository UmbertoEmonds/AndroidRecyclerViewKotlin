package com.umbertoemonds.myapplication.activity

import android.app.ActivityOptions
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umbertoemonds.myapplication.R
import com.umbertoemonds.myapplication.Utils.AlertUtils
import com.umbertoemonds.myapplication.adapter.GareAdapter
import com.umbertoemonds.myapplication.api.Api
import com.umbertoemonds.myapplication.api.BASE_URL
import com.umbertoemonds.myapplication.databinding.ActivityMainBinding
import com.umbertoemonds.myapplication.model.Gare
import com.umbertoemonds.myapplication.model.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

const val TAG = "MyActivity.TAG" // top level property

class MainActivity : AppCompatActivity(), GareAdapter.OnItemClickListener{

    private lateinit var binding: ActivityMainBinding

    private val gares:MutableList<Gare> = mutableListOf()
    private val garesSearched:MutableList<Gare> = mutableListOf()

    private val adapter = GareAdapter(garesSearched, this)
    private lateinit var mGaresRV:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mGaresRV = binding.recyclerView

        mGaresRV.adapter = adapter;
        mGaresRV.layoutManager = LinearLayoutManager(this)
        mGaresRV.setHasFixedSize(true)

        // lancement de la requete
        binding.progressBar.visibility = View.VISIBLE
        buildRequest(BASE_URL).enqueue(object : Callback<ResponseAPI> {

            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {

                if(response.code() == 200){

                    val responseBody = response.body()

                    binding.progressBar.visibility = View.INVISIBLE

                    responseBody?.let {
                        for (record in responseBody.records){
                            gares.add(record.fields)
                            garesSearched.add(record.fields)
                        }
                        adapter.notifyDataSetChanged()
                    }

                } else AlertUtils.showErrorMessage(this@MainActivity, getString(R.string.error_message))

            }
            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                AlertUtils.showErrorMessage(this@MainActivity, getString(R.string.error_message))
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.menu_search)

        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(!newText.isNullOrEmpty()){
                        garesSearched.clear()
                        val search = newText.toLowerCase(Locale.ROOT)
                        gares.forEach{
                            if (it.gare.toLowerCase(Locale.ROOT).contains(search)){
                                garesSearched.add(it)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        garesSearched.clear()
                        garesSearched.addAll(gares)
                        adapter.notifyDataSetChanged()
                    }

                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, GareActivity::class.java).apply {
            putExtra("gare", garesSearched[position])
        }
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    fun buildRequest(url:String): Call<ResponseAPI> {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(Api::class.java)
        return service.getResponse()
    }

}