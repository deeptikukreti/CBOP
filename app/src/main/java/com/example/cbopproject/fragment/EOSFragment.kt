package com.example.cbopproject.fragment

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.cbopproject.activity.MainActivity
import com.example.cbopproject.R
import com.example.cbopproject.adapter.FilterOrSearchAdapter
import com.example.cbopproject.adapter.DetailViewAdapter
import com.example.cbopproject.adapter.DurationAdapter
import com.example.cbopproject.adapter.ModelsOrVehiclesAdapter
import kotlinx.android.synthetic.main.fragment_e_o_s.*

class EOSFragment : Fragment(), View.OnClickListener {
    private var mainActivity: MainActivity? = null
    private var sortBYList: Array<String> = arrayOf("All Vehicles", "Model", "Individual Vehicle")
    private var durationList: Array<String> = arrayOf("All", "Last month", "Last 2 month","Specific month","Date Range")
    private var vehicleOrModelList: Array<String> =
        arrayOf("MH 20 GP 2029", "MH 20 GP 2028", "MH 20 GP 2027", "MH 20 GP 2026", "MH 20 GP 2025")
    private var isFilterByListOpen = false
    private var isSeachByVin = 0
    private var isModelListOpen = false
    private var isDurationListOpen = false
    private var modelsOrVehiclesAdapter: ModelsOrVehiclesAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_e_o_s, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun setDurationData() {
        durationListRecyclerView.adapter=DurationAdapter(mainActivity!!,durationList,object :DurationAdapter.DurationInterface{
            override fun onPositionClicked(position: Int) {
                durationTextView.text=durationList[position]
            }

        })
    }

    private fun setSortByListAdapter() {
        sortRecyclerView.adapter = FilterOrSearchAdapter(
            mainActivity!!,
            sortBYList,
            object : FilterOrSearchAdapter.SearchFilterInterface {
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
        when (v?.id) {
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
            R.id.overviewLayout -> {
                changeOverviewDetailViewBackground(
                    R.color.dark_blue,
                    R.color.light_grey
                )
                detailViewSummaryLayout.visibility = View.GONE
                overviewSummaryLayout.visibility = View.VISIBLE
            }
            R.id.detailViewLayout -> {
                changeOverviewDetailViewBackground(
                    R.color.light_grey,
                    R.color.dark_blue
                )
                overviewSummaryLayout.visibility = View.GONE
                detailViewSummaryLayout.visibility = View.VISIBLE
            }
        }
    }

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
}