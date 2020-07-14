package com.example.projectbase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.projectbase.App.Companion.context
import com.example.projectbase.adapters.DealsAdapter
import com.example.projectbase.models.Deal
import com.example.projectbase.models.Deals
import com.example.projectbase.models.NetworkConnection
import com.example.projectbase.models.TypeNetwork
import com.example.projectbase.viewmodels.DealsViewModel
import com.example.projectbase.viewmodels.ViewModelFactory
import com.example.projectbase.views.GameActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), DealsAdapter.onDealsListener {

    lateinit var dealAdapter: DealsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var dealsViewModel: DealsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).getAppComponent().inject(this)
        dealsViewModel =
            ViewModelProvider(this, viewModelFactory).get(DealsViewModel::class.java)

       //dealsViewModel.connection()
        dealsViewModel.connectivity.observe(this,
        Observer { connection -> StateConnection(connection) })


        dealsViewModel.responseLiveDataDeals.observe(this, Observer { deals ->
            setDeals(deals)
        })

        dealsViewModel.getDeals()
        dealsViewModel.responseLiveDataGetDeals.observe(this,
            Observer { deals -> showDeals(deals) })
    }
    fun openClick (view: View){

            context?.showToast("button")
            val intent = Intent(this,GameActivity::class.java)
            startActivity(intent)


    }

    private fun setDeals(deals: List<Deals>) {
        if (!deals.isNullOrEmpty()) {
            dealsViewModel.addDeals(deals)
        }
    }

    private fun showDeals(deals: List<Deals>) {
        if (deals.isNullOrEmpty()) {
            dealsViewModel.loadDeals()
        }else {
            val deal : ArrayList<Deal> = ArrayList()
            deals.forEach {
                deal.add(Deal(it.title,it.thumb))
            }
            dealAdapter = DealsAdapter(deal,this,this)
            recycler_deal.adapter = dealAdapter
        }

    }

    fun Context.showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

    }

    override fun gameClickListener(name: String, view: View) {
       view.snackbar(name,Color.WHITE,Color.BLACK)

    }


    fun StateConnection(connection: NetworkConnection){
       val view: View = findViewById(android.R.id.content)
        when(connection.Type){
            TypeNetwork.WIFI -> { view.snackbar(TypeNetwork.WIFI.toString(),Color.GREEN,Color.WHITE)}
            TypeNetwork.DATA -> { view.snackbar(TypeNetwork.DATA.toString(),Color.GREEN,Color.WHITE)}
            TypeNetwork.NON -> { view.snackbar(TypeNetwork.NON.toString(),Color.RED,Color.WHITE)}
            else -> null
        }
        if(!connection.state && connection.Type == TypeNetwork.WIFI){
            view.snackbar(TypeNetwork.NON.toString() + "WIFI",Color.RED,Color.WHITE)
        }

    }


    fun View.snackbar(message: String, color: Int, textColor: Int) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(color)
            .setTextColor(textColor)
            .show()


    }

}
