package com.example.projectbase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.projectbase.R
import com.example.projectbase.models.Deal
import com.example.projectbase.models.Deals
import kotlinx.android.synthetic.main.item_deals.view.*

class DealsAdapter(var mDeals: List<Deal>, var context: Context,  var listener: DealsAdapter.onDealsListener) :
    RecyclerView.Adapter<DealsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.image_text
        var image: ImageView = view.image_game
        var cardView : CardView = view.CardView_game

        fun bind(deal:Deal){
            name.text = deal.name
            image.loadUrl(deal.image)
        }

        private fun ImageView.loadUrl(url: String) {
            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
            Glide.with(context)
                .load(url)
                .apply(
                    RequestOptions().transforms(
                        RoundedCorners(35)
                    )
                )
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .into(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.item_deals,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mDeals.count()
    }


    interface onDealsListener{
        fun gameClickListener(nombre : String, view: View)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = mDeals[position]
        holder.bind(item)
        holder.cardView.setOnClickListener {
            listener.gameClickListener(item.name, it)
        }
    }

}