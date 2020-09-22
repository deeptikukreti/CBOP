package com.example.cbopproject.utils

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class CommonMethod {
    companion object{
        fun checkMonthGap(startDate: Date, endDate: Date): Int {
            val startCalendar: Calendar = GregorianCalendar()
            startCalendar.time = startDate
            val endCalendar: Calendar = GregorianCalendar()
            endCalendar.time = endDate
            val diffYear: Int = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)
            return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)
        }
        fun convertDateFormat(date:Date): String {
            var dateFormat = SimpleDateFormat("MMM yyyy")
            return dateFormat.format(date)
        }

        fun getMonth(month: Int): String? {
            return DateFormatSymbols().getMonths().get(month - 1)
        }

    }
}