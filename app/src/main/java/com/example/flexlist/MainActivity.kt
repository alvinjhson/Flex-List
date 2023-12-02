package com.example.flexlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    var toDoLists = mutableListOf<ToDoList>(
        ToDoList("Brush Teath",false),
        ToDoList("well",false),
        ToDoList("hello",false),

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView = findViewById<RecyclerView>(R.id.listRecycle)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ToDoListRecyclerAdapter(this,toDoLists)
        recyclerView.adapter = adapter
    }
}