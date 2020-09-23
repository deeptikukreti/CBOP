package com.example.cbopproject.utils

import android.os.Build
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class CalenderUtils {
    companion object {
        /***check difference between two months*/
        fun checkMonthGap(startDate: Date, endDate: Date): Int {
            val startCalendar: Calendar = GregorianCalendar()
            startCalendar.time = startDate
            val endCalendar: Calendar = GregorianCalendar()
            endCalendar.time = endDate
            val diffYear: Int = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)
            return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)
        }
          /***conver date from one format to another*/
        fun convertDateFormat(date: Date): String {
            var dateFormat = SimpleDateFormat("MMM yyyy")
            return dateFormat.format(date)
        }

        fun getMonth(month: Int): String? {
            return DateFormatSymbols().getMonths().get(month - 1)
        }

        fun getMaxDate(dateList: ArrayList<Date>): Date {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dateList.stream()
                    .max(Date::compareTo)
                    .get()
            } else {
                TODO("VERSION.SDK_INT < N")
            }
        }

        fun getMinDate(dateList: ArrayList<Date>): Date {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dateList.stream()
                    .min(Date::compareTo)
                    .get()
            } else {
                TODO("VERSION.SDK_INT < N")
            }
        }

    }
}