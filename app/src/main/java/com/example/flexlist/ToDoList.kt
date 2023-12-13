package com.example.flexlist

class ToDoList() {
    var itemName: String = ""
    var time: String = ""
    var checkBox : Boolean = false
    var id : String = ""

    constructor(itemName: String, time: String, checkBox : Boolean, id : String) : this() {
        this.itemName = itemName
        this.time = time
        this.checkBox = checkBox
        this.id = id
    }
}
