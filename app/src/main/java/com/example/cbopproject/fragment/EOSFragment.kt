package com.example.cbopproject.fragment

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cbopproject.R
import com.example.cbopproject.`interface`.DateRangeInterface
import com.example.cbopproject.activity.MainActivity
import com.example.cbopproject.activity.SelectDateRange
import com.example.cbopproject.adapter.DetailViewAdapter
import com.example.cbopproject.adapter.DurationAdapter
import com.example.cbopproject.adapter.ModelsOrVehiclesAdapter
import com.example.cbopproject.adapter.SortByAdapter
import com.example.cbopproject.utils.CalenderUtils
import com.example.cbopproject.utils.CalenderUtils.Companion.checkMonthGap
import com.example.cbopproject.utils.CalenderUtils.Companion.convertDateFormat
import com.example.cbopproject.utils.CalenderUtils.Companion.getMaxDate
import com.example.cbopproject.utils.CalenderUtils.Companion.getMinDate
import com.example.cbopproject.utils.CalenderUtils.Companion.getMonth
import kotlinx.android.synthetic.main.fragment_e_o_s.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EOSFragment : Fragment(), View.OnClickListener, DateRangeInterface {
    private var mainActivity: MainActivity? = null
    private var sortBYList: Array<String> = arrayOf("All Vehicles", "Model", "Individual Vehicle")
    private var durationList: Array<String> =
        arrayOf("All", "Last month", "Last 2 month", "Specific month", "Date Range")
    private var vehicleOrModelList: Array<String> =
        arrayOf("MH 20 GP 2029", "MH 20 GP 2028", "MH 20 GP 2027", "MH 20 GP 2026", "MH 20 GP 2025")
    /**check is sortlist dropdown open or not*/
    private var isFilterByListOpen = false
    /**check is search dropdown open or not*/
    private var isSeachByVin = 0
    /**check model list dropdown open or not*/
    private var isModelListOpen = false
    /**check is duration dropdown open or not*/
    private var isDurationListOpen = false
    private var modelsOrVehiclesAdapter: ModelsOrVehiclesAdapter? = null
    /**date range interface*/
    private lateinit var dateRangeInterface: DateRangeInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_e_o_s, container, false)
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
        detailViewRecyclerView.adapter =
            DetailViewAdapter(
                mainActivity!!,
                false,
                object : DetailViewAdapter.ClickedPositionInterface {
                    override fun onPositionClicked(position: Int) {
                    }
                })

        setSortByListAdapter()
        setModelOrVehiclesList()
        setDurationData()

    }

    /***set list of duration in adapter*/
    private fun setDurationData() {
        durationListRecyclerView.adapter = DurationAdapter(
            mainActivity!!,
            durationList,
            object : DurationAdapter.DurationInterface {
                override fun onPositionClicked(position: Int) {
                    when (position) {
                        0 -> {
                            durationTextView.text = durationList[position]
                        }
                        1 -> {
                            var lastMonth = Calendar.getInstance().get(Calendar.MONTH)
                            durationTextView.text =
                                durationList[position] + "(${getMonth(lastMonth)})"
                        }
                        2 -> {
                            var lastMonth = Calendar.getInstance().get(Calendar.MONTH)
                            var lastToLastMonth = Calendar.getInstance().get(Calendar.MONTH) - 1
                            durationTextView.text =
                                durationList[position] + "(${getMonth(lastToLastMonth)}-${getMonth(
                                    lastMonth
                                )})"
                        }
                        3 -> {
                            SelectDateRange.openSelectDateRangeDialog(
                                mainActivity!!,
                                dateRangeInterface,
                                false
                            )
                        }
                        4 -> {
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

    /***set filter by list adapter*/
    private fun setSortByListAdapter() {
        sortRecyclerView.adapter = SortByAdapter(
            mainActivity!!,
            sortBYList,
            object : SortByAdapter.SearchFilterInterface {
                override fun onPositionClicked(position: Int) {
                    filterByTextView.text = sortBYList[position]

                    if (position != 0) {
                        /**if position is 1 than model list dropdown is visible else search layout is visible */
                        searchOrDropdownLayout.visibility = View.VISIBLE
                        if (position == 1) {
                            isSeachByVin = 0
                            searchByVINEditText.setText("")
                            searchLayout.visibility = View.GONE
                            vehiclesListCard.visibility = View.GONE
                            modelTextView.visibility = View.VISIBLE
                        } else {
                            isSeachByVin = 1
                            modelTextView.setText("")
                            modelTextView.visibility = View.GONE
                            vehiclesListCard.visibility = View.GONE
                            searchLayout.visibility = View.VISIBLE
                        }
                    }
                    /**if position is equals to zero*/
                    else {
                        resetData()
                    }
                }

            })
    }
    /**set data for model List and searchList**/
    private fun setModelOrVehiclesList() {
        modelsOrVehiclesAdapter = ModelsOrVehiclesAdapter(mainActivity!!, vehicleOrModelList,
            object : ModelsOrVehiclesAdapter.SearchFilterInterface {
                override fun onPositionClicked(position: Int) {
                    /**if isSearchByVin == 1 then search is visible
                     * else modelList view is visible*/
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
                /**if there is data in searchByVINEditText then crossLayout is visible*/
                vehiclesListCard.visibility = View.VISIBLE
                if (s != null) {
                    crossLayout.visibility = View.VISIBLE
                }
            }

        })
    }
    /**when click on sort by list then reset data of model List and search list*/
    private fun resetData() {
        isSeachByVin = 0
        isModelListOpen = false
        modelTextView.setText("")
        searchByVINEditText.setText("")
        searchOrDropdownLayout.visibility = View.GONE
        searchLayout.visibility = View.GONE
        modelTextView.visibility = View.GONE
        vehiclesListCard.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.filterByTextView -> {
                //resetData()
                vehiclesListCard.visibility = View.GONE
                /**if isFilterByListOpen=true then set downArrow image
                 * else upArrow image and hide dropdown layout*/
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
                /**if isModelListOpen=true then set downArrow image
                 * else upArrow image and hide dropdown layout*/
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
                /**if isDurationListOpen=true then set downArrow image
                 * else upArrow image and hide dropdown layout*/
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
                /**empty search field and hide searchlist data */
                searchByVINEditText.setText("")
                vehiclesListCard.visibility = View.GONE
                crossLayout.visibility = View.GONE
            }
            R.id.overviewLayout -> {
                /**hide detailview layout show overviewLayout */
                changeOverviewDetailViewBackground(
                    R.color.dark_blue,
                    R.color.light_grey
                )
                detailViewSummaryLayout.visibility = View.GONE
                overviewSummaryLayout.visibility = View.VISIBLE
            }
            R.id.detailViewLayout -> {
                /**hide overviewLayout layout show detailViewLayout */
                changeOverviewDetailViewBackground(
                    R.color.light_grey,
                    R.color.dark_blue
                )
                overviewSummaryLayout.visibility = View.GONE
                detailViewSummaryLayout.visibility = View.VISIBLE
            }
        }
    }
/***change backround of selected layout*/
    private fun changeOverviewDetailViewBackground(overviewColor: Int, detailViewColor: Int) {
        overviewBg.backgroundTintList =
            ContextCompat.getColorStateList(mainActivity!!, overviewColor);
        overViewTextView.setTextColor(
            ContextCompat.getColorStateList(
                mainActivity!!,
                overviewColor
            )
        )
        detailViewBg.backgroundTintList =
            ContextCompat.getColorStateList(mainActivity!!, detailViewColor);
        detailViewTextView.setTextColor(
            ContextCompat.getColorStateList(
                mainActivity!!,
                detailViewColor
            )
        )
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity is MainActivity) {
            mainActivity = activity
        }
    }
/****set data for date range or select specific month from date interface*/
    var dateList: ArrayList<Date> = ArrayList()
    lateinit var minDate: Date
    lateinit var maxDate: Date
    override fun selectedMonths(selectedMonthsWithYear: ArrayList<String>, isDateRange: Boolean) {
        dateList.clear()
        var dateFormat = SimpleDateFormat("dd-MM-yyyy")
        if (isDateRange) {
            for (i in selectedMonthsWithYear.indices) {
                dateList.add(dateFormat.parse(selectedMonthsWithYear[i]))
            }
            maxDate = getMaxDate(dateList)
            Log.d("SelectedDateRange", "maxDate=${convertDateFormat(maxDate)}")

            minDate = getMinDate(dateList)
            Log.d("SelectedDateRange", "minDate=${convertDateFormat(minDate)}")

            Log.d("SelectedDateRange", "gap=${checkMonthGap(minDate, maxDate)}")
            durationTextView.text =
                "${convertDateFormat(minDate)} - ${convertDateFormat(maxDate)}(${checkMonthGap(
                    minDate,
                    maxDate
                ) + 1} months)"
        } else {
            durationTextView.text =
                "${CalenderUtils.convertDateFormat(dateFormat.parse(selectedMonthsWithYear[0]))}"
        }
    }
}