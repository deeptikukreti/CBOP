package com.example.cbopproject.fragment

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.cbopproject.activity.MainActivity
import com.example.cbopproject.R
import com.example.cbopproject.`interface`.DateRangeInterface
import com.example.cbopproject.activity.SelectDateRange
import com.example.cbopproject.adapter.DetailViewAdapter
import com.example.cbopproject.adapter.DurationAdapter
import com.example.cbopproject.adapter.ModelsOrVehiclesAdapter
import com.example.cbopproject.adapter.SortByAdapter
import com.example.cbopproject.utils.CommonMethod
import kotlinx.android.synthetic.main.fragment_e_o_s.*
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.*
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.crossLayout
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.detailViewBg
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.detailViewLayout
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.detailViewRecyclerView
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.detailViewSummaryLayout
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.detailViewTextView
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.durationListCard
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.durationListRecyclerView
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.durationTextView
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.filterByTextView
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.modelTextView
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.overViewTextView
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.overviewBg
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.overviewLayout
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.overviewSummaryLayout
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.searchByVINEditText
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.searchLayout
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.searchOrDropdownLayout
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.sortByCard
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.sortRecyclerView
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.vehiclesListCard
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragment.vehiclesListRecyclerView
import java.text.SimpleDateFormat
import java.util.*


class WorkShopRepairFragment : Fragment(),View.OnClickListener, DateRangeInterface {
    var mainActivity: MainActivity? =null
    private var sortBYList: Array<String> = resources.getStringArray(R.array.sort_by_array)
    private var durationList: Array<String> = resources.getStringArray(R.array.duration_list)
    private var vehicleOrModelList: Array<String> =resources.getStringArray(R.array.vehicle_or_model_list)
    private var isFilterByListOpen = false
    private var isSeachByVin = 0
    private var isModelListOpen = false
    private var isDurationListOpen = false
    private var modelsOrVehiclesAdapter: ModelsOrVehiclesAdapter? = null
    private lateinit var dateRangeInterface: DateRangeInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_shop_repair_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateRangeInterface = this
        viewInitialization()
    }
    private fun viewInitialization() {
        overviewLayout.setOnClickListener(this)
        detailViewLayout.setOnClickListener(this)
        filterByTextView.setOnClickListener(this)
        modelTextView.setOnClickListener(this)
        durationTextView.setOnClickListener(this)
        crossLayout.setOnClickListener(this)
        filterByTextView.text = "All Vehichles"
        detailViewRecyclerView.adapter=
            DetailViewAdapter(
                mainActivity!!,
                true,
                object :
                    DetailViewAdapter.ClickedPositionInterface {
                    override fun onPositionClicked(position: Int) {

                    }

                })
        setSortByListAdapter()
        setModelOrVehiclesList()
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
    private fun setSortByListAdapter() {
        sortRecyclerView.adapter = SortByAdapter(
            mainActivity!!,
            sortBYList,
            object : SortByAdapter.SearchFilterInterface {
                override fun onPositionClicked(position: Int) {
                    filterByTextView.text = sortBYList[position]
                    if (position != 0) {
                        searchOrDropdownLayout.visibility = View.VISIBLE
                        if (position == 1) {
                            isSeachByVin = 0
                            searchByVINEditText.setText("")
                            searchLayout.visibility = View.GONE
                            modelTextView.visibility = View.VISIBLE
                        } else {
                            isSeachByVin = 1
                            modelTextView.setText("")
                            modelTextView.visibility = View.GONE
                            searchLayout.visibility = View.VISIBLE
                        }
                    } else {
                        resetData()
                    }
                }

            })
    }
    private fun setModelOrVehiclesList() {
        modelsOrVehiclesAdapter = ModelsOrVehiclesAdapter(mainActivity!!, vehicleOrModelList,
            object : ModelsOrVehiclesAdapter.SearchFilterInterface {
                override fun onPositionClicked(position: Int) {
                    if (isSeachByVin == 1) {
                        searchByVINEditText.setText(vehicleOrModelList[position])
                        vehiclesListCard.visibility = View.GONE
                    } else {
                        modelTextView.text = vehicleOrModelList[position]
                    }

                }

            })
        vehiclesListRecyclerView.adapter = modelsOrVehiclesAdapter
        searchByVINEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vehiclesListCard.visibility = View.VISIBLE
                if (s != null) {
                    crossLayout.visibility = View.VISIBLE
                }
            }

        })
    }
    private fun resetData(){
        isSeachByVin = 0
        isModelListOpen=false
        modelTextView.setText("")
        searchByVINEditText.setText("")
        searchOrDropdownLayout.visibility = View.GONE
        searchLayout.visibility = View.GONE
        modelTextView.visibility = View.GONE
        vehiclesListCard.visibility=View.GONE
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.filterByTextView -> {
                resetData()
                vehiclesListCard.visibility=View.GONE
                if (isFilterByListOpen) {
                    isFilterByListOpen = false
                    filterByTextView.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.path,
                        0
                    )
                    sortByCard.visibility = View.GONE
                } else {
                    isFilterByListOpen = true
                    filterByTextView.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.dropdownup,
                        0
                    )
                    sortByCard.visibility = View.VISIBLE
                }
            }
            R.id.modelTextView -> {
                if (isModelListOpen) {
                    isModelListOpen = false
                    modelTextView.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.path,
                        0
                    )
                    vehiclesListCard.visibility = View.GONE
                } else {
                    isModelListOpen = true
                    modelTextView.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.dropdownup,
                        0
                    )
                    vehiclesListCard.visibility = View.VISIBLE
                }
            }
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
            R.id.crossLayout -> {
                searchByVINEditText.setText("")
                vehiclesListCard.visibility = View.GONE
                crossLayout.visibility = View.GONE
            }
            R.id.overviewLayout ->{
                changeOverviewDetailViewBackground(
                    R.color.dark_blue,
                    R.color.light_grey
                )
                detailViewSummaryLayout.visibility=View.GONE
                overviewSummaryLayout.visibility=View.VISIBLE
            }
            R.id.detailViewLayout ->{
                changeOverviewDetailViewBackground(
                    R.color.light_grey,
                    R.color.dark_blue
                )
                overviewSummaryLayout.visibility=View.GONE
                detailViewSummaryLayout.visibility=View.VISIBLE
            }
        }
    }
    private fun changeOverviewDetailViewBackground(overviewColor:Int,detailViewColor:Int){
        overviewBg.backgroundTintList = ContextCompat.getColorStateList(mainActivity!!, overviewColor);
        overViewTextView.setTextColor(ContextCompat.getColorStateList(mainActivity!!, overviewColor))
        detailViewBg.backgroundTintList = ContextCompat.getColorStateList(mainActivity!!, detailViewColor);
        detailViewTextView.setTextColor( ContextCompat.getColorStateList(mainActivity!!, detailViewColor))
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