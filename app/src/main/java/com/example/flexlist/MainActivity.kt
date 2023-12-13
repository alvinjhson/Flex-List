package com.example.flexlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var db : FirebaseFirestore
    lateinit var adapter: ToDoListRecyclerAdapter

    val dropDownList= arrayOf("Daily List","Shopping List")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listSpinner = findViewById<Spinner>(R.id.listSpinner)
        db = Firebase.firestore
        loadItems()

        val arrayAdapterDIf = ArrayAdapter<String>(this,R.layout.dropdown,dropDownList)
        listSpinner.adapter = arrayAdapterDIf
        listSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext,"Selected List is " + dropDownList[position],Toast.LENGTH_SHORT).show()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



        val taskAddButton = findViewById<FloatingActionButton>(R.id.addTaskFloatingButton)

         recyclerView = findViewById<RecyclerView>(R.id.listRecycle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ToDoListRecyclerAdapter(this,DataManager.item)




        taskAddButton.setOnClickListener {
            val intent = Intent(this,CreateAndEditTaskActivity::class.java)
            startActivity(intent)

        }

    }
    fun loadItems() {
        db.collection("items").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val item = document.toObject(ToDoList::class.java)
                DataManager.item.add(item)
            }
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }




    override fun onResume() {
        super.onResume()

        recyclerView.adapter?.notifyDataSetChanged()

    }

}