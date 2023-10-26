package com.example.ownapi

import Adapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val data = createDataList()
        val adapter = Adapter(data)
        recyclerView.adapter = adapter
    }

    private fun createDataList(): List<ItemsViewModel> {
        val dataList = mutableListOf<ItemsViewModel>()

        for (i in 1..20) {
            fetchDataForPokemon(i, dataList)
        }

        return dataList
    }

    private fun fetchDataForPokemon(pokemonId: Int, dataList: MutableList<ItemsViewModel>) {
        val client = AsyncHttpClient()
        val url = "https://pokeapi.co/api/v2/pokemon/$pokemonId"

        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                try {
                    val imageUrl = json.jsonObject.getJSONObject("sprites").getString("front_default")
                    val name = json.jsonObject.getJSONObject("species").getString("name")
                    val weight = json.jsonObject.getInt("weight")
                    val item = ItemsViewModel(imageUrl, name, weight)
                    dataList.add(item)

                    // When all data is fetched, update the RecyclerView
                    if (dataList.size == 20) {
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                        val adapter = Adapter(dataList)
                        recyclerView.adapter = adapter
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                t?.printStackTrace()
            }
        }]
    }
}
