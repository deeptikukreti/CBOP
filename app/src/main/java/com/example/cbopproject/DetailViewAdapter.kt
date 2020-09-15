package com.example.cbopproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_header_item_layout.view.*

class DetailViewAdapter(var context : Context, var clickedPosition: ClickedPositionInterface) : RecyclerView.Adapter<DetailViewAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_detail_view_item_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return 2
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(position: Int):View {
            return itemView
        }
    }
     interface ClickedPositionInterface{
         fun onPositionClicked(position:Int)
     }

}