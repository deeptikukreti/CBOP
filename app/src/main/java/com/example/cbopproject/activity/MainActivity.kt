package com.example.cbopproject.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cbopproject.R
import com.example.cbopproject.fragment.EOSFragment
import com.example.cbopproject.fragment.UptimeFragment
import com.example.cbopproject.fragment.WorkShopRepairFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewInitialization()
    }

    private fun viewInitialization() {
        eosRadioBtn.isChecked = true
        /**set default fragment*/
        loadFragment(EOSFragment())

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                /**set fragment on click of selected radio button*/
                R.id.eosRadioBtn -> {
                    loadFragment(EOSFragment())
                }
                R.id.workShopRepairRadioBtn -> {
                    loadFragment(WorkShopRepairFragment())
                }
                R.id.upTimeRadioBtn -> {
                    loadFragment(UptimeFragment())
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

    /**method for add or replace a fragment*/
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, "fragmentTag")
            .disallowAddToBackStack()
            .commit()

    }

}