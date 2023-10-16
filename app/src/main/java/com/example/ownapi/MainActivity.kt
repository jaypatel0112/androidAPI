package com.example.ownapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var pokemonImageURL = ""
    private var pokemonName = ""
    private var weight = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.pokeButton)
        val imageView = findViewById<ImageView>(R.id.image)
        val textView = findViewById<TextView>(R.id.textViewPokemonName)
        getNextImage(button, imageView, textView)

    }
    private fun pokemonURL(){
        val client = AsyncHttpClient()
        var choice = Random.nextInt(20)
        val url = "https://pokeapi.co/api/v2/pokemon/$choice"
        client[url, object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                try {
                    pokemonImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                    Log.d("Pokemon Image URL", pokemonImageURL)
                    weight = json.jsonObject.getInt("weight")
                    Log.d("Pokemon Weight", weight.toString())
                    // Extracting the Pokemon name
                    pokemonName = json.jsonObject.getJSONObject("species").getString("name")
                    Log.d("Pokemon Name", pokemonName)
                } catch (e: JSONException) {
                    Log.e("Pokemon URL", "Error parsing JSON", e)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                Log.d("Pokemon's Error", errorResponse)
            }
        }]
    }
    private fun getNextImage(button: Button, imageView: ImageView, textView: TextView){
        button.setOnClickListener {
            pokemonURL()
            Log.d("Image URL", pokemonImageURL)
            Glide.with(this)
                .load(pokemonImageURL)
                .fitCenter()
                .into(imageView)

            textView.text = "Pokemon Name: $pokemonName and Weight: $weight"
        }
    }
}
