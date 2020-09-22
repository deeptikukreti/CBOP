package com.example.cbopproject.activity

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cbopproject.R
import com.example.cbopproject.`interface`.DateRangeInterface
import com.example.cbopproject.adapter.MonthsAdapter
import com.example.cbopproject.model.MonthBean
import com.example.cbopproject.utils.CalenderUtils
import kotlinx.android.synthetic.main.date_range_layout.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
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
                if (isDateRange) {
                    if (selectedMonthsList.size >= 2) {
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
                    if (selectedMonthsList.size > 1 && isDateRange) {
                        getMinAndMaxDatesFromSelectedDates()
                        setMonthsAdapter(dialog,monthsListRecyclerView,isDateRange,activity)
                    }else{
                        monthsAdapter.notifyDataSetChanged()
                    }


                }

            })
            monthsListRecyclerView?.adapter = monthsAdapter

        }

        var dateList: ArrayList<Date> = ArrayList()
        lateinit var minDate: Date
        lateinit var maxDate: Date
        fun getMinAndMaxDatesFromSelectedDates() {
            dateList.clear()
            var dateFormat = SimpleDateFormat("dd-MM-yyyy")
            for (i in selectedMonthsWithYear.indices) {
                dateList.add(dateFormat.parse(selectedMonthsWithYear[i]))
            }
            maxDate = CalenderUtils.getMaxDate(dateList)

            minDate = CalenderUtils.getMinDate(dateList)
            var dateFormat2 = SimpleDateFormat("MMM-yyyy")
            setDatesBetweenDateRange(dateFormat2.format(minDate), dateFormat2.format(maxDate))
        }

        fun setDatesBetweenDateRange(beignDate: String, endDate: String) {
            val formater: DateFormat = SimpleDateFormat("MMM-yyyy")

            val beginCalendar = Calendar.getInstance()
            val finishCalendar = Calendar.getInstance()

            try {
                beginCalendar.time = formater.parse(beignDate)
                finishCalendar.time = formater.parse(endDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            var loopCount = 0
            while (beginCalendar.before(finishCalendar)) {
                // add one month to date per loop
                loopCount++
                val date: String = formater.format(beginCalendar.time)
                var splitDate = date.split("-")
                beginCalendar.add(Calendar.MONTH, 1)
                if (loopCount > 1) {
                    Log.d("SelectedDateRange", date)
                    Log.d("SelectedDateRange", splitDate.toString())
                    Log.d(
                        "SelectedDateRange",
                        "${SimpleDateFormat("MM").format(beginCalendar.time).toInt()}"
                    )
                    Log.d(
                        "SelectedDateRange",
                        "${SimpleDateFormat("MM").format(beginCalendar.time).toInt() - 2}"
                    )
                    var selectMonthInNumber=0
                    if((SimpleDateFormat("MM").format(beginCalendar.time).toInt() - 2)==-1){
                        selectMonthInNumber=11
                    }else{
                        selectMonthInNumber=(SimpleDateFormat("MM").format(beginCalendar.time).toInt() - 2)
                    }
                    if (!selectedMonthsList.contains(
                            MonthBean(
                                splitDate[0],
                                splitDate[1].toInt(),
                                selectMonthInNumber,
                                true
                            )
                        )
                    ) {
                        selectedMonthsList.add(
                            MonthBean(
                                splitDate[0],
                                splitDate[1].toInt(),
                               selectMonthInNumber,
                                true
                            )
                        )
                        selectedMonthsWithYear.add(
                            "01-${selectMonthInNumber+1}-${splitDate[1].toInt()}"
                        )
                    }
                }
            }

        }

        fun addMonths() {
            monthList.add(
                MonthBean(
                    "Jan",
                    selectedCurrentYear,
                    0,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Feb",
                    selectedCurrentYear,
                    1,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Mar",
                    selectedCurrentYear,
                    2,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Apr",
                    selectedCurrentYear,
                    3,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "May",
                    selectedCurrentYear,
                    4,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Jun",
                    selectedCurrentYear,
                    5,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Jul",
                    selectedCurrentYear,
                    6,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Aug",
                    selectedCurrentYear,
                    7,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Sep",
                    selectedCurrentYear,
                    8,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Oct",
                    selectedCurrentYear,
                    9,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Nov",
                    selectedCurrentYear,
                    10,
                    false
                )
            )
            monthList.add(
                MonthBean(
                    "Dec",
                    selectedCurrentYear,
                    11,
                    false
                )
            )

        }
    }
}