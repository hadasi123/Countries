package com.shraifel.countries.app.ui
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shraifel.countries.R
import com.shraifel.countries.app.api.RetrofitClient
import com.shraifel.countries.app.data.Country
import kotlinx.android.synthetic.main.fragment_countries_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesFragment: Fragment(), CountriesListAdapter.OnItemClickListener
{
    private val TAG = "CountriesFragment"
    private var isAscending = true
    private var isOrderedByName = true
    private val countriesRetriever = RetrofitClient()
    private lateinit var countriesAscendingByName: List<Country>
    private lateinit var countriesDescendingByArea: List<Country>
    private lateinit var countriesDescendingByName: List<Country>
    private lateinit var countriesAscendingByArea: List<Country>
    private lateinit var listener: CountriesListAdapter.OnItemClickListener

    private val callback = object : Callback<List<Country>> {

        override fun onFailure(call: Call<List<Country>>?, t: Throwable?) {
            Log.e(TAG, "Problem calling API ${t?.message}")
        }

        override fun onResponse(call: Call<List<Country>>?, response: Response<List<Country>>?) {
            response?.isSuccessful.let {
                val resultList = response?.body()

                if (resultList != null) {
                    countriesAscendingByName = resultList.sortedWith(compareBy { it.name })
                    countriesDescendingByArea = resultList.sortedWith(compareByDescending { it.area })
                    countriesDescendingByName = resultList.sortedWith(compareByDescending { it.name })
                    countriesAscendingByArea = resultList.sortedWith(compareBy { it.area })
                }

                list_of_countries.adapter = countriesAscendingByName?.let { it1 -> CountriesListAdapter(it1, listener) }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_countries_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        listener = this
        list_of_countries.layoutManager = LinearLayoutManager(activity)

        order_by_name.setOnClickListener {
            activity?.resources?.getColor(R.color.black)?.let { it1 -> order_by_name.setTextColor(it1) }
            activity?.resources?.getColor(R.color.grey)?.let { it1 -> order_by_size.setTextColor(it1) }
            isOrderedByName = true
            refreshListByParameters()
        }
        order_by_size.setOnClickListener {
            activity?.resources?.getColor(R.color.grey)?.let { it1 -> order_by_name.setTextColor(it1) }
            activity?.resources?.getColor(R.color.black)?.let { it1 -> order_by_size.setTextColor(it1) }
            isOrderedByName = false
            refreshListByParameters()
        }
        arrow.setOnClickListener{
            onArrowPressed()
            refreshListByParameters()}
    }

    override fun onResume() {
        super.onResume()
        countriesRetriever.getCountries(callback)
    }

    private fun refreshListByParameters()
    {
        if(isOrderedByName) {
            if(isAscending)
                list_of_countries.adapter = countriesAscendingByName?.let { it1 -> CountriesListAdapter(it1, listener) }
            else
                list_of_countries.adapter = countriesDescendingByName?.let { it1 -> CountriesListAdapter(it1, listener) }
        }
        else {
            if(isAscending)
                list_of_countries.adapter =
                    countriesAscendingByArea?.let { it1 -> CountriesListAdapter(it1, listener) }
            else
                list_of_countries.adapter =
                    countriesDescendingByArea?.let { it1 -> CountriesListAdapter(it1, listener) }
        }
    }

    private fun onArrowPressed(){
        isAscending = !isAscending
        if(isAscending)
            arrow.setImageResource(R.mipmap.arrow_up)
        else
            arrow.setImageResource(R.mipmap.arrow_down)
    }

    override fun onItemClicked(country: Country) {
        (activity as MainActivity).onNavigateToBordersInfo(country)
    }

    interface Navigation{
        fun onNavigateToBordersInfo(country: Country)
    }
}