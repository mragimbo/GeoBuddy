package com.k8596.ownproject

import android.content.Intent
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class LocationListAdapter (var LocationList: MutableList<LocationListItem>) : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListAdapter.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)




    }

    override fun getItemCount(): Int = LocationList.size

    override fun onBindViewHolder(holder: LocationListAdapter.ViewHolder, position: Int) {
        val item: LocationListItem = LocationList[position]
        holder.nameTextView.text = item.name
        holder.latTextViiew.text = item.lat.toString()
        holder.lngTextViiew.text = item.lng.toString()

    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
        val latTextViiew: TextView = view.latTextViiew
        val lngTextViiew: TextView = view.lngTextViiew



        init {
            itemView.setOnClickListener {
                Toast.makeText(
                    view.context,
                    "opening" + " '" + nameTextView.text + "' " + "on Maps..",
                    Toast.LENGTH_LONG
                ).show()
                val data = latTextViiew.text.toString()+ "," + lngTextViiew.text.toString() + "," + nameTextView.text
                val intent = Intent(view.context, MapsActivity::class.java)
                intent.putExtra("latlng", data)
                view.context.startActivity(intent)



            }

        }
    }

    fun update(newList: MutableList<LocationListItem>) {
        LocationList = newList
        notifyDataSetChanged()
    }


}
