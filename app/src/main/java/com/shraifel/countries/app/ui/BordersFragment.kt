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
import kotlinx.android.synthetic.main.fragment_borders.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BordersFragment: Fragment()
{
    private val TAG = "CountriesFragment"
    private val bordersRetriever = RetrofitClient()
    private val callback = object : Callback<List<Country>> {
        override fun onFailure(call: Call<List<Country>>?, t: Throwable?) {
            Log.e(TAG, "Problem calling API ${t?.message}")
        }

        override fun onResponse(call: Call<List<Country>>?, response: Response<List<Country>>?) {
            response?.isSuccessful.let {
                val resultList = response?.body()
                list_of_borders?.adapter = resultList?.let { it1 -> CountriesListAdapter(it1, null) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_borders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_of_borders.layoutManager = LinearLayoutManager(activity)
        arguments?.getString("codes")?.let {
            bordersRetriever.getBordersWithCountry(callback, it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(borders: String?) = BordersFragment().apply {
            arguments = Bundle().apply {
                putString("codes",borders)
            }
        }
    }
}