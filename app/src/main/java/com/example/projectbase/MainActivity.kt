package com.example.projectbase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.projectbase.App.Companion.context
import com.example.projectbase.adapters.DealsAdapter
import com.example.projectbase.models.Deal
import com.example.projectbase.models.Deals
import com.example.projectbase.viewmodels.DealsViewModel
import com.example.projectbase.viewmodels.ViewModelFactory
import com.example.projectbase.views.GameActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

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


        dealsViewModel.stateConexionInternet()
        dealsViewModel.conexion.observe(this,
        Observer { conexion -> context?.showToast("Internet $conexion") })


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
            var deal : ArrayList<Deal> = ArrayList()
            deals.forEach {
                deal.add(Deal(it.title,it.thumb))
            }
            dealAdapter = DealsAdapter(deal,this)
            recycler_deal.adapter = dealAdapter
        }

    }

    fun Context.showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

    }


}
