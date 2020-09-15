package com.example.cbopproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cbopproject.R
import kotlinx.android.synthetic.main.single_detail_view_item_layout.view.*

class DetailViewAdapter(var context : Context, var isRepairWorkshop:Boolean,var clickedPosition: ClickedPositionInterface) : RecyclerView.Adapter<DetailViewAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_detail_view_item_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /**isRepairWorkshop variable check if we come from
         *  WorkShopRepairFragment  only then the view will visible*/
        if (isRepairWorkshop){
            holder.bindItem(position).serviceHistoryTextView.visibility=View.VISIBLE
        }else{
            holder.bindItem(position).serviceHistoryTextView.visibility=View.GONE
        }

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return 5
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