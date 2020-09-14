package com.example.cbopproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_header_item_layout.view.*

class HeaderAdapter(var context : Context,var headerList: ArrayList<String>,var clickedPosition: ClickedPositionInterface) : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    var clickPosition=0
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_header_item_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /***on click on item assign clicked position to clickPostion variable*/
        holder.bindItem(position).headerText.text=headerList.get(position)
        holder.itemView.setOnClickListener {
            clickPosition=position
            notifyDataSetChanged()
        }
        if(position==0){
            holder.bindItem(position).divider.visibility=View.GONE
        }else{
            holder.bindItem(position).divider.visibility=View.VISIBLE
        }

        /***if clicked position match with current item postion
         *  then the item background should be blue else white in color */
        if(clickPosition==position){
            holder.bindItem(position).headerBackground.setBackgroundColor(ContextCompat.getColor(context,R.color.dark_blue))
            holder.bindItem(position).headerText.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
            clickedPosition.onPositionClicked(position)
        }else{
            holder.bindItem(position).headerBackground.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary))
            holder.bindItem(position).headerText.setTextColor(ContextCompat.getColor(context,R.color.light_grey))
        }



    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return headerList.size
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