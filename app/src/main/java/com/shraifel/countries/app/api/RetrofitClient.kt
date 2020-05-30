package com.shraifel.countries.app.api
import com.shraifel.countries.app.data.Country
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val service: Endpoints

    companion object {
        private const val BASE_URL = "https://restcountries.eu"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(Endpoints::class.java)
    }

    fun getCountries(callback: Callback<List<Country>>) {
        service.retrieveCountries().enqueue(callback)
    }

    fun getBordersWithCountry(callback: Callback<List<Country>>, codes: String){
        service.retrieveCountriesByCodes(codes).enqueue(callback)
    }
}