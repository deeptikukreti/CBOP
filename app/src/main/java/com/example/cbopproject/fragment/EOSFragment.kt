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
import com.example.cbopproject.adapter.SearchByVinAdapter
import kotlinx.android.synthetic.main.fragment_e_o_s.*

class EOSFragment : Fragment(), View.OnClickListener {
    private var mainActivity: MainActivity? = null
    private var sortBYList: Array<String> = arrayOf("All Vehicles", "Model", "Individual Vehicle")
    private var searchByModelList: Array<String> =
        arrayOf("MH 20 GP 2029", "MH 20 GP 2028", "MH 20 GP 2027", "MH 20 GP 2026", "MH 20 GP 2025")
    private var isFilterBy = 0
    private var isSeachByVin = 0
    private var searchByVinAdapter: SearchByVinAdapter? = null
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
        sortRecyclerView.adapter =
            FilterOrSearchAdapter(
                mainActivity!!,
                sortBYList,
                object :
                    FilterOrSearchAdapter.SearchFilterInterface {
                    override fun onPositionClicked(position: Int) {
                        filterByTextView.text = sortBYList[position]
                        if (position != 0) {
                            searchLayout.visibility = View.VISIBLE
                        } else {
                            searchLayout.visibility = View.GONE
                        }
                    }

                })
        searchByVinAdapter = SearchByVinAdapter(
            mainActivity!!,
            searchByModelList,
            object : SearchByVinAdapter.SearchFilterInterface {
                override fun onPositionClicked(position: Int) {
                    SearchByVINEditText.setText(searchByModelList[position])
                    isSeachByVin = 0
                    searchByVinCard.visibility = View.GONE
                }

            })
        searchByVinRecyclerView.adapter=searchByVinAdapter
        SearchByVINEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchByVinCard.visibility = View.VISIBLE
                if (s != null) {
                    crossLayout.visibility = View.VISIBLE
                }
            }

        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.filterByTextView -> {
                if (isFilterBy == 1) {
                    isFilterBy = 0
                    filterByTextView.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.path,
                        0
                    )
                    sortByCard.visibility = View.GONE
                } else {
                    isFilterBy = 1
                    filterByTextView.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.dropdownup,
                        0
                    )
                    sortByCard.visibility = View.VISIBLE
                }
            }
            R.id.crossLayout -> {
                SearchByVINEditText.setText("")
                searchByVinCard.visibility = View.GONE
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