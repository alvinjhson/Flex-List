package com.example.flexlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

const val ITEM_POSISTION_KEY = "ITEM_POSISTION"

const val POSISTION_NOT_SET = -1
class CreateAndEditTaskActivity : AppCompatActivity() {

    lateinit var nameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_and_edit_task)
        nameEditText = findViewById(R.id.editTextText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val itemPosistion = intent.getIntExtra(ITEM_POSISTION_KEY, POSISTION_NOT_SET)


        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val check = false
            val item = ToDoList(name,check)
            DataManager.item.add(item)
            finish()
        }

    }
}
