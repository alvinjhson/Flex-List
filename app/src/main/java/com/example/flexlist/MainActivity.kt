package com.example.flexlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val taskAddButton = findViewById<FloatingActionButton>(R.id.addTaskFloatingButton)

         recyclerView = findViewById<RecyclerView>(R.id.listRecycle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ToDoListRecyclerAdapter(this,DataManager.item)



        taskAddButton.setOnClickListener {
            val intent = Intent(this,CreateAndEditTaskActivity::class.java)
            startActivity(intent)

        }

    }




    override fun onResume() {
        super.onResume()

        recyclerView.adapter?.notifyDataSetChanged()

    }

}