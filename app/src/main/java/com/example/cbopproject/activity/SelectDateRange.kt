package com.example.cbopproject.activity

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import com.example.cbopproject.MonthBean
import com.example.cbopproject.R
import com.example.cbopproject.`interface`.DateRangeInterface
import com.example.cbopproject.adapter.MonthsAdapter
import kotlinx.android.synthetic.main.date_range_layout.*
import java.util.*
import kotlin.collections.ArrayList


class SelectDateRange {
    companion object {
        private var monthList: ArrayList<MonthBean> = ArrayList()
        var selectedMonthsList: ArrayList<MonthBean> = ArrayList()
        var selectedMonthsWithYear: ArrayList<String> = ArrayList()
        private lateinit var monthsAdapter: MonthsAdapter
        var currentYear: Int = 0
        var selectedCurrentYear: Int = 0
        var currentMonth: Int = 0
        fun openSelectDateRangeDialog(
            activity: Activity?,
            dateRangeInterface: DateRangeInterface,
            isDateRange: Boolean
        ) {
            var dialog: Dialog = Dialog(activity!!)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(R.layout.date_range_layout)
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.setCanceledOnTouchOutside(false)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            clearData()
            currentMonth = Calendar.getInstance().get(Calendar.MONTH)
            currentYear = Calendar.getInstance().get(Calendar.YEAR)
            selectedCurrentYear = Calendar.getInstance().get(Calendar.YEAR)
            addMonths()
            dialog.yearTextView.text = selectedCurrentYear.toString()
            dialog.ivNextYear.visibility = View.INVISIBLE
            dialog.selectRangeButton.isEnabled = false
            dialog.selectRangeButton.alpha = 0.5f
            //  var monthLisR.array.months_array
            setMonthsAdapter(dialog, dialog.monthsListRecyclerView, isDateRange, activity!!)
            dialog.closeDialogButton.setOnClickListener {
                clearData()
                dialog.dismiss()
            }
            dialog.ivPreviousYear.setOnClickListener {
                selectedCurrentYear -= 1
                dialog.yearTextView.text = selectedCurrentYear.toString()
                dialog.ivNextYear.visibility = View.VISIBLE
                monthsAdapter.notifyDataSetChanged()
                setMonthsAdapter(dialog, dialog.monthsListRecyclerView, isDateRange, activity!!)
            }
            dialog.ivNextYear.setOnClickListener {
                selectedCurrentYear += 1
                dialog.yearTextView.text = selectedCurrentYear.toString()
                if (selectedCurrentYear == currentYear)
                    dialog.ivNextYear.visibility = View.INVISIBLE
                monthsAdapter.notifyDataSetChanged()
                setMonthsAdapter(dialog, dialog.monthsListRecyclerView, isDateRange, activity!!)
            }
            dialog.selectRangeButton.setOnClickListener {
                if (isDateRange ) {
                    if(selectedMonthsList.size >= 2) {
                        dateRangeInterface.selectedMonths(selectedMonthsWithYear, true)
                        clearData()
                        dialog.dismiss()
                    }
                } else {
                    dateRangeInterface.selectedMonths(selectedMonthsWithYear, false)
                    clearData()
                    dialog.dismiss()
                }

            }
            dialog?.show()
        }

        private fun clearData() {
            monthList.clear()
            selectedMonthsList.clear()
            selectedMonthsWithYear.clear()
        }

        private fun setMonthsAdapter(
            dialog: Dialog,
            monthsListRecyclerView: RecyclerView?,
            isDateRange: Boolean,
            activity: Activity?
        ) {
            monthList.clear()
            addMonths()
            for (i in selectedMonthsList.indices) {
                if (selectedMonthsList[i].year == selectedCurrentYear) {
                    monthList.removeAt(selectedMonthsList[i].position)
                    monthList.add(selectedMonthsList[i].position, selectedMonthsList[i])
                }
            }
            monthsAdapter = MonthsAdapter(activity!!, monthList, isDateRange, object :
                MonthsAdapter.SelectedMonthInterface {
                override fun onPositionClicked(position: Int, isSelected: Boolean) {
                    if (isSelected) {
                        selectedMonthsList.remove(monthList[position])
                        selectedMonthsWithYear.remove("01-${monthList[position].position + 1}-$selectedCurrentYear")
                    } else {
                        selectedMonthsList.add(monthList[position])
                        selectedMonthsWithYear.add("01-${monthList[position].position + 1}-$selectedCurrentYear")
                    }
                    if (selectedMonthsList.size > 0) {
                        dialog.selectRangeButton.isEnabled = true
                        dialog.selectRangeButton.alpha = 1f
                    } else {
                        dialog.selectRangeButton.isEnabled = false
                        dialog.selectRangeButton.alpha = 0.5f
                    }
                    monthsAdapter.notifyDataSetChanged()
                }

            })
            monthsListRecyclerView?.adapter = monthsAdapter

        }

        fun addMonths() {
            monthList.add(MonthBean("Jan", selectedCurrentYear, 0, false))
            monthList.add(MonthBean("Feb", selectedCurrentYear, 1, false))
            monthList.add(MonthBean("Mar", selectedCurrentYear, 2, false))
            monthList.add(MonthBean("Apr", selectedCurrentYear, 3, false))
            monthList.add(MonthBean("May", selectedCurrentYear, 4, false))
            monthList.add(MonthBean("June", selectedCurrentYear, 5, false))
            monthList.add(MonthBean("July", selectedCurrentYear, 6, false))
            monthList.add(MonthBean("Aug", selectedCurrentYear, 7, false))
            monthList.add(MonthBean("Sep", selectedCurrentYear, 8, false))
            monthList.add(MonthBean("Oct", selectedCurrentYear, 9, false))
            monthList.add(MonthBean("Nov", selectedCurrentYear, 10, false))
            monthList.add(MonthBean("Dec", selectedCurrentYear, 11, false))

        }
    }
}