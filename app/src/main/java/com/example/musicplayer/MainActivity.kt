package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var searchIcon:ImageView
    lateinit var searchEditText: EditText
    lateinit var searchResultRecyclerView:RecyclerView
    lateinit var searchResultAdapter:SearchResultRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchResultRecyclerView=binding.searchResultRecyclerView

        val retrofitBuilder =Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)


        searchIcon=binding.searchIcon
        searchEditText=binding.searchEditText

        searchIcon.setOnClickListener {
            val retrofitData=retrofitBuilder.getData(binding.searchEditText.text.toString())
            retrofitData.enqueue(object : Callback<MusicSearch?>{
                override fun onResponse(
                    call: Call<MusicSearch?>,
                    response: Response<MusicSearch?>
                ) {
                    if(response.isSuccessful)
                    {
                        val searchResponse=response.body();
                        val musicSearchList=searchResponse?.data!!
                        println(musicSearchList)

                        searchResultAdapter=
                            SearchResultRecyclerAdapter(this@MainActivity,musicSearchList)
                        searchResultRecyclerView.adapter=searchResultAdapter
                        searchResultRecyclerView.layoutManager=LinearLayoutManager(this@MainActivity)

                    }
                    else
                    {
                        Toast.makeText(this@MainActivity, "some problem", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MusicSearch?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "some error ", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}