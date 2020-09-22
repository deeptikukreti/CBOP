package com.example.cbopproject.fragment

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cbopproject.R
import com.example.cbopproject.`interface`.DateRangeInterface
import com.example.cbopproject.activity.MainActivity
import com.example.cbopproject.activity.SelectDateRange
import com.example.cbopproject.adapter.DurationAdapter
import com.example.cbopproject.adapter.UptimeAdapter
import com.example.cbopproject.utils.CommonMethod
import kotlinx.android.synthetic.main.fragment_e_o_s.*
import kotlinx.android.synthetic.main.fragment_e_o_s.durationListCard
import kotlinx.android.synthetic.main.fragment_e_o_s.durationListRecyclerView
import kotlinx.android.synthetic.main.fragment_e_o_s.durationTextView
import kotlinx.android.synthetic.main.fragment_uptime.*
import java.text.SimpleDateFormat
import java.util.*


class UptimeFragment : Fragment(), View.OnClickListener, DateRangeInterface {
   private var mainActivity: MainActivity? =null
    private var isDurationListOpen = false
    private var durationList: Array<String> = resources.getStringArray(R.array.duration_list)
    private lateinit var dateRangeInterface: DateRangeInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_uptime, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateRangeInterface = this
        durationTextView.setOnClickListener(this)
        uptimeVehiclesRecyclerView.adapter=UptimeAdapter(mainActivity!!,object : UptimeAdapter.ClickedPositionInterface{
            override fun onPositionClicked(position: Int) {

            }
        })
        setDurationData()
    }

    private fun setDurationData() {
        durationListRecyclerView.adapter = DurationAdapter(
            mainActivity!!,
            durationList,
            object : DurationAdapter.DurationInterface {
                override fun onPositionClicked(position: Int) {
                    when(position){
                        0->{durationTextView.text = durationList[position]}
                        1->{ var lastMonth = Calendar.getInstance().get(Calendar.MONTH)
                            durationTextView.text = durationList[position] + "(${CommonMethod.getMonth(
                                lastMonth
                            )})"}
                        2->{
                            var lastMonth = Calendar.getInstance().get(Calendar.MONTH)
                            var lastToLastMonth = Calendar.getInstance().get(Calendar.MONTH) - 1
                            durationTextView.text =
                                durationList[position] + "(${CommonMethod.getMonth(lastToLastMonth)}-${CommonMethod.getMonth(
                                    lastMonth
                                )})"
                        }
                        3->{ SelectDateRange.openSelectDateRangeDialog(
                            mainActivity!!,
                            dateRangeInterface,
                            false
                        )}
                        4->{
                            SelectDateRange.openSelectDateRangeDialog(
                                mainActivity!!,
                                dateRangeInterface,
                                true
                            )
                        }
                    }
                }

            })
    }
    override fun onClick(v: View?) {
       when(v?.id){
           R.id.durationTextView -> {
               if (isDurationListOpen) {
                   isDurationListOpen = false
                   durationTextView.setCompoundDrawablesWithIntrinsicBounds(
                       0,
                       0,
                       R.drawable.path,
                       0
                   )
                   durationListCard.visibility = View.GONE
               } else {
                   isDurationListOpen = true
                   durationTextView.setCompoundDrawablesWithIntrinsicBounds(
                       0,
                       0,
                       R.drawable.dropdownup,
                       0
                   )
                   durationListCard.visibility = View.VISIBLE
               }
           }
       }

    }
    var dateList: ArrayList<Date> = ArrayList()
    lateinit var minDate: Date
    lateinit var maxDate: Date
    override fun selectedMonths(selectedMonthsWithYear: ArrayList<String>,isDateRange:Boolean) {
        dateList.clear()
        var dateFormat = SimpleDateFormat("dd-MM-yyyy")
        if (isDateRange) {
            for (i in selectedMonthsWithYear.indices) {
                dateList.add(dateFormat.parse(selectedMonthsWithYear[i]))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                maxDate = dateList.stream()
                    .max(Date::compareTo)
                    .get()
                //Log.d("SelectedDateRange","maxDate=${maxDate}")
                Log.d("SelectedDateRange", "maxDate=${CommonMethod.convertDateFormat(maxDate)}")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                minDate = dateList.stream()
                    .min(Date::compareTo)
                    .get()
                Log.d("SelectedDateRange", "minDate=${CommonMethod.convertDateFormat(minDate)}")

                //E MMM dd hh:mm:ss GMT+05:30 yyyy
            }
            Log.d("SelectedDateRange", "gap=${CommonMethod.checkMonthGap(minDate, maxDate)}")
            durationTextView.text =
                "${CommonMethod.convertDateFormat(minDate)} - ${CommonMethod.convertDateFormat(
                    maxDate
                )}(${CommonMethod.checkMonthGap(
                    minDate,
                    maxDate
                ) + 1} months)"
        }else{
            durationTextView.text ="${CommonMethod.convertDateFormat(dateFormat.parse(selectedMonthsWithYear[0]))}"
        }
    }
    override fun onAttach(activity : Activity) {
        super.onAttach(activity)
        if (activity is MainActivity) {
            mainActivity = activity
        }
    }


}