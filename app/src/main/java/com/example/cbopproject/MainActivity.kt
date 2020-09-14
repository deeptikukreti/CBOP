package com.example.cbopproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var headerList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewInitialization()
    }

    private fun viewInitialization() {
        addHeaderData()
        rvHeader.adapter=HeaderAdapter(this,headerList,object :HeaderAdapter.ClickedPositionInterface{
            override fun onPositionClicked(position: Int) {
                when(position){
                    0->{
                        supportFragmentManager
                            .beginTransaction()
                            .add(R.id.container, EOSFragment(), "fragmentTag")
                            .disallowAddToBackStack()
                            .commit()
                    }
                }
            }

        })
    }

    private fun addHeaderData() {
        headerList.add("EOS")
        headerList.add("Workshop Repair")
        headerList.add("Uptime")
        headerList.add("Action Planning")
        headerList.add("Commercial")
        headerList.add("Training & Productivity")
        headerList.add("Engineering")
    }
}