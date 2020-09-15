package com.example.cbopproject

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_work_shop_repair_fragmnet.*


class WorkShopRepairFragmnet : Fragment(),View.OnClickListener  {
    var mainActivity: MainActivity? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_shop_repair_fragmnet, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewInitialization()
    }

    private fun viewInitialization() {
        overviewLayout.setOnClickListener(this)
        detailViewLayout.setOnClickListener(this)
        detailViewRecyclerView.adapter=DetailViewAdapter(mainActivity!!,true,object :
            DetailViewAdapter.ClickedPositionInterface{
            override fun onPositionClicked(position: Int) {

            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.overviewLayout->{
                changeOverviewDetailViewBackground(R.color.dark_blue,R.color.light_grey)
                detailViewSummaryLayout.visibility=View.GONE
                overviewSummaryLayout.visibility=View.VISIBLE
            }
            R.id.detailViewLayout->{
                changeOverviewDetailViewBackground(R.color.light_grey,R.color.dark_blue)
                overviewSummaryLayout.visibility=View.GONE
                detailViewSummaryLayout.visibility=View.VISIBLE
            }
        }
    }
    private fun changeOverviewDetailViewBackground(overviewColor:Int,detailViewColor:Int){
        overviewBg.backgroundTintList = ContextCompat.getColorStateList(mainActivity!!, overviewColor);
        overViewTextView.setTextColor( overviewColor)
        detailViewBg.backgroundTintList = ContextCompat.getColorStateList(mainActivity!!, detailViewColor);
        detailViewTextView.setTextColor( detailViewColor)
    }

    override fun onAttach(activity : Activity) {
        super.onAttach(activity)
        if (activity is MainActivity) {
            mainActivity = activity
        }
    }
}