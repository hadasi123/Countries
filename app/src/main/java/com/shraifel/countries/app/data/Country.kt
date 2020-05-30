package com.shraifel.countries.app.data

data class Country(
        val name: String,
        val nativeName: String,
        val area: Double,
        val borders: List<String>?){

}
