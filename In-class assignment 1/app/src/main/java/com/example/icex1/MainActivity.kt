package com.example.icex1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var listViewResults: ListView
    private lateinit var textViewMessage: TextView

    private val results = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        editTextNumber = findViewById(R.id.editTextNumber)
        radioGroup = findViewById(R.id.radioGroup)
        listViewResults = findViewById(R.id.listViewResults)
        textViewMessage = findViewById(R.id.textViewSelected)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, results)
        listViewResults.adapter = adapter

        radioGroup.setOnCheckedChangeListener { _, _ -> populateList() }
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    private fun populateList() {
        val input = editTextNumber.text.toString()

        // Clear previous results
        results.clear()

        // Check if the input is valid
        if (input.isEmpty() || !input.isDigitsOnly()) {
            textViewMessage.text = "Invalid input. Please enter a valid number."
            adapter.notifyDataSetChanged()
            return
        }

        val n = input.toInt()
        if (n <= 0) {
            textViewMessage.text = "Please enter a number greater than 0."
            adapter.notifyDataSetChanged()
            return
        }

        // Get selected RadioButton
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId

        when (selectedRadioButtonId) {
            R.id.radioEven -> {
                // Populate with even numbers from 0 to n
                for (i in 0..n step 2) {
                    results.add(i.toString())
                }
            }
            R.id.radioOdd -> {
                // Populate with odd numbers from 1 to n
                for (i in 1..n step 2) {
                    results.add(i.toString())
                }
            }
            R.id.radioSquare -> {
                // Populate with square numbers from 1 to n
                var i = 1
                while (i * i <= n) {
                    results.add((i * i).toString())
                    i++
                }
            }
            else -> {
                textViewMessage.text = "Please select an option."
            }
        }

        // Update the ListView with the results
        adapter.notifyDataSetChanged()

        // Update the TextView with the selected number or message
        if (results.isNotEmpty()) {
            textViewMessage.text = "Populated numbers up to $n."
        }
    }
}