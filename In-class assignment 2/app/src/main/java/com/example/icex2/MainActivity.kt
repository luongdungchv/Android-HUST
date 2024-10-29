package com.example.icex2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Create and populate the map directly
        val studentList: Map<String, String> = mapOf(
            "100001" to "John Doe",
            "100002" to "Jane Smith",
            "100003" to "Michael Johnson",
            "100004" to "Emily Davis",
            "100005" to "Daniel Brown",
            "100006" to "Sophia Wilson",
            "100007" to "William Moore",
            "100008" to "Olivia Taylor",
            "100009" to "James Anderson",
            "100010" to "Isabella Thomas",
            "100011" to "Liam Martinez",
            "100012" to "Emma White",
            "100013" to "Lucas Harris",
            "100014" to "Ava Clark",
            "100015" to "Mason Lewis",
            "100016" to "Mia Walker",
            "100017" to "Ethan Hall",
            "100018" to "Amelia Young",
            "100019" to "Alexander King",
            "100020" to "Charlotte Wright",
            "100021" to "Benjamin Scott",
            "100022" to "Abigail Green",
            "100023" to "Henry Adams",
            "100024" to "Chloe Baker",
            "100025" to "Samuel Gonzalez",
            "100026" to "Evelyn Perez",
            "100027" to "Jack Mitchell",
            "100028" to "Avery Carter",
            "100029" to "Sebastian Roberts",
            "100030" to "Ella Phillips",
            "100031" to "Jackson Turner",
            "100032" to "Grace Parker",
            "100033" to "Matthew Collins",
            "100034" to "Scarlett Edwards",
            "100035" to "David Stewart",
            "100036" to "Zoe Foster",
            "100037" to "Logan Price",
            "100038" to "Victoria Howard",
            "100039" to "Ryan Cox",
            "100040" to "Natalie Hughes",
            "100041" to "Joshua Ramirez",
            "100042" to "Sophie Cooper",
            "100043" to "Gabriel Torres",
            "100044" to "Hannah Richardson",
            "100045" to "Dylan Murphy",
            "100046" to "Lily Howard",
            "100047" to "Nathan Diaz",
            "100048" to "Layla Bell",
            "100049" to "Isaac Campbell",
            "100050" to "Aubrey Baker",
            "100051" to "Caleb Stewart",
            "100052" to "Penelope Powell",
            "100053" to "Levi Bryant",
            "100054" to "Hazel Sanders",
            "100055" to "Elijah Bennett",
            "100056" to "Stella Coleman",
            "100057" to "Owen Perry",
            "100058" to "Camila Simmons",
            "100059" to "Isaiah Ross",
            "100060" to "Samantha Morris"
        )
        val listView = findViewById<ListView>(R.id.listViewItems)
        val editText = findViewById<EditText>(R.id.editTextInput);

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList<String>())
        listView.adapter = adapter

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchAndUpdateListView(s.toString(), studentList, adapter)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        searchAndUpdateListView(editText.text.toString(), studentList, adapter)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

    }

    private fun searchAndUpdateListView(inputText: String, studentList: Map<String, String>, adapter: ArrayAdapter<String>) {
        val filteredList = if (inputText.length >= 2) {
            studentList.filter {
                it.key.contains(inputText, ignoreCase = true) || it.value.contains(inputText, ignoreCase = true)
            }
        } else {
            studentList
        }

        val displayList = filteredList.map { "${it.value} ${it.key}" }

        adapter.clear()
        adapter.addAll(displayList)
        adapter.notifyDataSetChanged()
    }
}