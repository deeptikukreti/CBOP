package com.example.cbopproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cbopproject.R
//var monthList:Array<String>,
class MonthsAdapter(var context : Context, var selectedMonth: SelectedMonthInterface) : RecyclerView.Adapter<MonthsAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_month_item_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return 12
    }

    //the class is holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(position: Int):View {
            return itemView
        }
    }
     interface SelectedMonthInterface{
         fun onPositionClicked(position:Int)
     }

}