package com.example.cbopproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cbopproject.MonthBean
import com.example.cbopproject.R
import com.example.cbopproject.activity.SelectDateRange
import com.example.cbopproject.utils.CommonMethod
import kotlinx.android.synthetic.main.single_month_item_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//var monthList:Array<String>,
class MonthsAdapter(
    var context: Context,
    var monthsList: ArrayList<MonthBean>,
    var selectedMonth: SelectedMonthInterface
) : RecyclerView.Adapter<MonthsAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_month_item_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        monthsList[position].year = SelectDateRange.selectedCurrentYear
        holder.bindItem(position).monthNameTextView.text = monthsList[position].monthName
        if (SelectDateRange.selectedCurrentYear == SelectDateRange.currentYear) {
            if (position > SelectDateRange.currentMonth) {
                holder.bindItem(position).isEnabled = false
                holder.bindItem(position).alpha = 0.5f
            } else {
                holder.bindItem(position).isEnabled = true
                holder.bindItem(position).alpha = 1f
            }
        }

        if (monthsList[position].isSelected) {
            holder.bindItem(position).monthLayout.setBackgroundResource(R.drawable.selected_month_bg)
            holder.bindItem(position).monthNameTextView.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimary
                )
            )
        } else {
            holder.bindItem(position).monthLayout.setBackgroundResource(R.drawable.unselected_month_bg)
            holder.bindItem(position).monthNameTextView.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.light_grey
                )
            )
        }
        holder.itemView.setOnClickListener {

            if (!monthsList[position].isSelected && SelectDateRange.selectedMonthsList.size < 6) {
                if (SelectDateRange.selectedMonthsList.size > 0) {

                    if (isValidateSelectedMonthIsUptoSixMonths("1-${monthsList[position].position + 1}-${monthsList[position].year}")) {
                        monthsList[position].isSelected = true
                        selectedMonth.onPositionClicked(position, false)
                    } else {
                        Toast.makeText(context, "You can select upto 6 months", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    monthsList[position].isSelected = true
                    selectedMonth.onPositionClicked(position, false)
                }
            } else {
                monthsList[position].isSelected = false
                selectedMonth.onPositionClicked(position, true)
            }
            notifyDataSetChanged()
        }


    }

    fun isValidateSelectedMonthIsUptoSixMonths(endDate: String): Boolean {
        var isvalidate = true
        var dateFormat = SimpleDateFormat("dd-MM-yyyy")
        for (i in SelectDateRange.selectedMonthsWithYear.indices) {
            var monthGap = CommonMethod.checkMonthGap(
                dateFormat.parse(SelectDateRange.selectedMonthsWithYear[i]),
                dateFormat.parse(endDate)
            )
            if (monthGap > 5 || monthGap < -5) {
                isvalidate = false
                break
            }
        }

        return isvalidate
    }



    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return monthsList.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(position: Int): View {
            return itemView
        }
    }

    interface SelectedMonthInterface {
        fun onPositionClicked(position: Int, isSelected: Boolean)
    }

}