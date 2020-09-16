package com.example.cbopproject.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cbopproject.R
import com.example.cbopproject.activity.MainActivity
import com.example.cbopproject.adapter.DurationAdapter
import com.example.cbopproject.adapter.UptimeAdapter
import kotlinx.android.synthetic.main.fragment_e_o_s.*
import kotlinx.android.synthetic.main.fragment_e_o_s.durationListCard
import kotlinx.android.synthetic.main.fragment_e_o_s.durationListRecyclerView
import kotlinx.android.synthetic.main.fragment_e_o_s.durationTextView
import kotlinx.android.synthetic.main.fragment_uptime.*


class UptimeFragment : Fragment(), View.OnClickListener {
   private var mainActivity: MainActivity? =null
    private var isDurationListOpen = false
    private var durationList: Array<String> = arrayOf("All", "Last month", "Last 2 month","Specific month","Date Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_uptime, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        durationTextView.setOnClickListener(this)
        uptimeVehiclesRecyclerView.adapter=UptimeAdapter(mainActivity!!,object : UptimeAdapter.ClickedPositionInterface{
            override fun onPositionClicked(position: Int) {

            }
        })
        setDurationData()
    }

    private fun setDurationData() {
        durationListRecyclerView.adapter=
            DurationAdapter(mainActivity!!,durationList,object : DurationAdapter.DurationInterface{
            override fun onPositionClicked(position: Int) {
                durationTextView.text=durationList[position]
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
    override fun onAttach(activity : Activity) {
        super.onAttach(activity)
        if (activity is MainActivity) {
            mainActivity = activity
        }
    }


}