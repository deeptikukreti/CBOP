package com.example.cbopproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cbopproject.R
import kotlinx.android.synthetic.main.single_dropdown_item_layout.view.*

class DurationAdapter(var context : Context, var durationList:Array<String>, var clickedPosition: DurationInterface) : RecyclerView.Adapter<DurationAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_dropdown_item_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(position).textView.text=durationList[position]
     holder.itemView.setOnClickListener {
         clickedPosition.onPositionClicked(position)
     }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return durationList.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(position: Int):View {
            return itemView
        }
    }
     interface DurationInterface{
         fun onPositionClicked(position:Int)
     }

}