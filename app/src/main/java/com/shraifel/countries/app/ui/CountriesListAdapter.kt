package com.shraifel.countries.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shraifel.countries.R
import com.shraifel.countries.app.data.Country
import kotlinx.android.synthetic.main.country_item.view.*

class CountriesListAdapter(private val resultList: List<Country>) : RecyclerView.Adapter<CountriesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindRepo(resultList[position])
    }

    override fun getItemCount(): Int = resultList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindRepo(country: Country) {
            with(country) {

                itemView.native_name.text = nativeName
                itemView.english_name.text = name
            }
        }
    }
}