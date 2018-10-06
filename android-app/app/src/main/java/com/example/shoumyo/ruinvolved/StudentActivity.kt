package com.example.shoumyo.ruinvolved

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_student.*
import com.example.shoumyo.ruinvolved.ui.MultiSelectionSpinner



class StudentActivity : AppCompatActivity(), MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    override fun selectedIndices(indices: MutableList<Int>?) {
    }

    override fun selectedStrings(strings: MutableList<String>?) {
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
//                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
//                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
//                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val array = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
        categories.setItems(array)
        categories.setSelection(intArrayOf(2, 6))
        categories.setListener(this)
    }
}
