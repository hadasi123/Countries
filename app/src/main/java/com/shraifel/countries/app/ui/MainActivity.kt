package com.shraifel.countries.app.ui
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shraifel.countries.R
import com.shraifel.countries.app.data.Country

class MainActivity : AppCompatActivity(), CountriesFragment.Navigation {

    var countriesFragment = CountriesFragment()
    private lateinit var bordersFragment: BordersFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkNetworkFlow()
    }

    private fun checkNetworkFlow()
    {
        if (isNetworkConnected()) {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, countriesFragment).commit()

        } else {
            AlertDialog.Builder(this).setTitle(R.string.no_internet_connection)
                .setMessage(R.string.no_internet_connection_content)
                .setPositiveButton(android.R.string.ok) { _, _ ->checkNetworkFlow() }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun navigateToBordersFragment(border: String?)
    {
        bordersFragment = BordersFragment.newInstance(border)
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, bordersFragment).addToBackStack(null).commit()
    }

    override fun onNavigateToBordersInfo(country: Country) {
        navigateToBordersFragment(country.borders?.joinToString(";"))
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}