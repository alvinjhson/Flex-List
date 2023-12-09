package com.example.flexlist

object DataManager {
    val item = mutableListOf<ToDoList>()


    init {
        createMockData()
    }





    fun createMockData() {
        item.add(ToDoList("Brush","",false))



    }
}