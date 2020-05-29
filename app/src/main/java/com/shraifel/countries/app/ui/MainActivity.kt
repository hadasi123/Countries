package com.shraifel.countries.app.ui
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shraifel.countries.R
import com.shraifel.countries.app.api.RetrofitClient
import com.shraifel.countries.app.data.Country
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val countriesRetriever = RetrofitClient()
    private val callback = object : Callback<List<Country>> {
        override fun onFailure(call: Call<List<Country>>?, t: Throwable?) {
            Log.e("MainActivity", "Problem calling API ${t?.message}")
        }

        override fun onResponse(call: Call<List<Country>>?, response: Response<List<Country>>?) {
            response?.isSuccessful.let {
                val resultList = response?.body()
                list_of_countries.adapter = resultList?.let { it1 -> CountriesListAdapter(it1) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list_of_countries.layoutManager = LinearLayoutManager(this)

        if (isNetworkConnected()) {
            //countriesRetriever.getCountries(callback)
            countriesRetriever.getBordersWithCountry(callback)
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }
}