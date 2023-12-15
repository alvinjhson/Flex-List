package com.example.flexlist

class ToDoList() {
    var itemName: String = ""
    var time: String = ""
    var checkBox : Boolean = false
    var id : String = ""
    var userId: String = ""

    constructor(itemName: String, time: String, checkBox : Boolean, id : String,userId : String) : this() {
        this.itemName = itemName
        this.time = time
        this.checkBox = checkBox
        this.id = id
        this.userId = userId
    }
}
