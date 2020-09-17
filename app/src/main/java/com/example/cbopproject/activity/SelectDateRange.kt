package com.example.cbopproject.activity

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import com.example.cbopproject.R
import com.example.cbopproject.`interface`.DateRangeInterface
import com.example.cbopproject.adapter.MonthsAdapter
import kotlinx.android.synthetic.main.date_range_layout.*

class SelectDateRange{
  companion object{
      fun openSelectDateRangeDialog(activity: Activity?,
          dateRangeInterface: DateRangeInterface
      ){
          var dialog:Dialog= Dialog(activity!!)
          dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
          dialog?.setContentView(R.layout.date_range_layout)
//          dialog?.window?.setLayout(
//              ViewGroup.LayoutParams.MATCH_PARENT,
//              ViewGroup.LayoutParams.MATCH_PARENT)
          dialog.window!!.setGravity(Gravity.CENTER)
          dialog.setCanceledOnTouchOutside(false)
          dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
          dialog.closeDialogButton.setOnClickListener {
              dialog.dismiss()
          }
          dialog.monthsListRecyclerView.adapter=MonthsAdapter(activity!!,object :
              MonthsAdapter.SelectedMonthInterface{
              override fun onPositionClicked(position: Int) {

              }

          })
          dialog?.show()
      }
  }
}