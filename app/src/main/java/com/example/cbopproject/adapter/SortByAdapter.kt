package com.example.cbopproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cbopproject.R
import kotlinx.android.synthetic.main.single_dropdown_item_layout.view.*

class SortByAdapter(var context : Context, var searchList:Array<String>, var clickedPosition: SearchFilterInterface) : RecyclerView.Adapter<SortByAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_dropdown_item_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(position).textView.text=searchList[position]
     holder.itemView.setOnClickListener {
         clickedPosition.onPositionClicked(position)
     }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return searchList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(position: Int):View {
            return itemView
        }
    }
     interface SearchFilterInterface{
         fun onPositionClicked(position:Int)
     }

}