package com.shraifel.countries.app.api
import com.shraifel.countries.app.data.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.QueryName

interface Endpoints
{
    @GET("/rest/v2/all?fields=name;nativeName;area;borders")
    fun retrieveCountries(): Call<List<Country>>

    @GET("/rest/v2/alpha?fields=name;nativeName;area;borders")
    fun retrieveCountriesByCodes(@Query("codes") codes: String): Call<List<Country>>
}
