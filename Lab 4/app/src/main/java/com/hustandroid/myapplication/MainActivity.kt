package com.hustandroid.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private final lateinit var fromCurrencySpinner: Spinner;
    private final lateinit var toCurrencySpinner: Spinner;
    private final lateinit var amountInput: EditText;
    private final lateinit var resultTextView: TextView;

    private val exchangeRates: Map<String, Map<String, Double>> = mapOf(
        "VND" to mapOf("USD" to 0.000041, "EUR" to 0.000038, "CNY" to 0.00029, "JPY" to 0.0061, "VND" to 1.0),
        "USD" to mapOf("VND" to 24300.0, "EUR" to 0.927, "CNY" to 7.25, "JPY" to 146.65, "USD" to 1.0),
        "EUR" to mapOf("VND" to 26250.0, "USD" to 1.078, "CNY" to 7.82, "JPY" to 158.12, "EUR" to 1.0),
        "CNY" to mapOf("VND" to 3350.0, "USD" to 0.138, "EUR" to 0.128, "JPY" to 20.22, "CNY" to 1.0),
        "JPY" to mapOf("VND" to 167.0, "USD" to 0.0068, "EUR" to 0.0063, "CNY" to 0.049, "JPY" to 1.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        fromCurrencySpinner = findViewById<Spinner>(R.id.fromCurrencySpinner);
        toCurrencySpinner = findViewById<Spinner>(R.id.toCurrencySpinner);
        amountInput = findViewById<EditText>(R.id.amountInput)
        resultTextView = findViewById<TextView>(R.id.resultTextView)

        val currencies = resources.getStringArray(R.array.currencies_array)

        val fromAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromCurrencySpinner.adapter = fromAdapter
        fromCurrencySpinner.setSelection(currencies.indexOf("VND"))

        val toAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toCurrencySpinner.adapter = toAdapter
        toCurrencySpinner.setSelection(currencies.indexOf("USD"))

        fromCurrencySpinner.onItemSelectedListener = currencyChangeListener
        toCurrencySpinner.onItemSelectedListener = currencyChangeListener

        amountInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                performConversion()
            }
        })

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
    private val currencyChangeListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            Log.e("MainActivity","asdfas");
            performConversion()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Do nothing
        }
    }
    private fun performConversion() {
        // Get the input amoun

        val amountString = amountInput.text.toString()
        if (amountString.isEmpty()) {
            resultTextView.text = ""
            return
        }

        val amount = amountString.toDoubleOrNull() ?: return

        // Get the selected currencies
        val fromCurrency = fromCurrencySpinner.selectedItem.toString()
        val toCurrency = toCurrencySpinner.selectedItem.toString()

        // Get the exchange rate between the selected currencies
        val conversionRate = exchangeRates[fromCurrency]?.get(toCurrency) ?: 1.0

        // Perform the conversion
        val convertedAmount = amount * conversionRate

        // Display the result
        resultTextView.text = "%.2f".format(convertedAmount)
    }
}