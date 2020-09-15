package com.example.cbopproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var headerList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewInitialization()
    }

    private fun viewInitialization() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, EOSFragment(), "fragmentTag")
            .disallowAddToBackStack()
            .commit()

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