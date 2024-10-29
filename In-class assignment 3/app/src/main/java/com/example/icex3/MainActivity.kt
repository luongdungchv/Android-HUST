package com.example.icex3

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var dateOfBirthTextView: TextView
    private var isCalendarVisible = false

    private lateinit var provinceSpinner: Spinner
    private lateinit var districtSpinner: Spinner
    private lateinit var wardSpinner: Spinner

    private lateinit var addressHelper: AddressHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendar_view)
        dateOfBirthTextView = findViewById(R.id.date_of_birth_textview)
        val showCalendarButton: Button = findViewById(R.id.show_calendar_button)
        val submitButton: Button = findViewById(R.id.submit_button)

        showCalendarButton.setOnClickListener {
            toggleCalendarVisibility()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"  // Month is 0-based
            dateOfBirthTextView.text = "Date of Birth: $selectedDate" // Update the TextView
            calendarView.visibility = View.GONE  // Hide calendar after selection
            isCalendarVisible = false  // Reset visibility state
        }
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        provinceSpinner = findViewById(R.id.province_spinner)
        districtSpinner = findViewById(R.id.district_spinner)
        wardSpinner = findViewById(R.id.ward_spinner)

        addressHelper = AddressHelper(resources)

        val provinces = addressHelper.getProvinces()
        val provinceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceSpinner.adapter = provinceAdapter

        provinceSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedProvince = provinces[position]
                updateDistricts(selectedProvince)
                wardSpinner.isEnabled = false // Disable ward spinner until district is selected
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })

        districtSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedDistrict = districtSpinner.selectedItem.toString()
                updateWards(provinceSpinner.selectedItem.toString(), selectedDistrict)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })

    }
    private fun toggleCalendarVisibility() {
        if (isCalendarVisible) {
            calendarView.visibility = View.GONE
        } else {
            calendarView.visibility = View.VISIBLE
        }
        isCalendarVisible = !isCalendarVisible
    }

    private fun updateDistricts(selectedProvince: String) {
        val districts = addressHelper.getDistricts(selectedProvince)
        val districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        districtSpinner.adapter = districtAdapter
        districtSpinner.isEnabled = districts.isNotEmpty()  // Enable district spinner if districts are available
        districtSpinner.setSelection(0)  // Reset selection to the first item
        wardSpinner.isEnabled = false  // Disable ward spinner until district is selected
    }

    private fun updateWards(selectedProvince: String, selectedDistrict: String) {
        val wards = addressHelper.getWards(selectedProvince, selectedDistrict)
        val wardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wards)
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wardSpinner.adapter = wardAdapter
        wardSpinner.isEnabled = wards.isNotEmpty()  // Enable ward spinner if wards are available
        wardSpinner.setSelection(0)  // Reset selection to the first item
    }
}