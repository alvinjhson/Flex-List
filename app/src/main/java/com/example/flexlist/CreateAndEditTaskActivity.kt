package com.example.flexlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.text.FieldPosition

const val ITEM_POSISTION_KEY = "ITEM_POSISTION"

const val POSISTION_NOT_SET = -1
class CreateAndEditTaskActivity : AppCompatActivity() {

    lateinit var db : FirebaseFirestore



    lateinit var nameEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_and_edit_task)


        db = Firebase.firestore
        val delete = findViewById<ImageButton>(R.id.removeItemButton)
        nameEditText = findViewById(R.id.editTextText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val itemPosistion = intent.getIntExtra(ITEM_POSISTION_KEY, POSISTION_NOT_SET)
        val setTime = findViewById<Button>(R.id.setTimeButton)




        if (itemPosistion != POSISTION_NOT_SET) {
            displayItem(itemPosistion)
            saveButton.setOnClickListener {
                editItem(itemPosistion)
            }
            setTime.setOnClickListener {
                val intent = Intent(this,TimeActivity::class.java)
                startActivity(intent)
            }
            delete.setOnClickListener {
                removeItem(itemPosistion)


            }
        }else {
            saveButton.setOnClickListener {

                addItem()
            }
        }

    }
    fun editItem(position: Int) {
        DataManager.item[position].itemName = nameEditText.text.toString()
        val id = DataManager.item[position].id
        db.collection("items").document(id).set(DataManager.item[position])

        finish()
    }

    fun displayItem(position : Int) {
        val item = DataManager.item[position]
        nameEditText.setText(item.itemName)
    }

    fun addItem() {
        val name = nameEditText.text.toString()
        val check = false
        val item = ToDoList(name, "", check, "")
        db.collection("items").add(item).addOnSuccessListener { document ->
            val id = document.id
            item.id = id
            db.collection("items").document(id).set(item)
            DataManager.item.add(item)
            finish()

        }

    }
    fun removeItem(position: Int) {
        val itemId = DataManager.item[position].id
        removeItemFromFirestore(itemId)
        DataManager.item.removeAt(position)
        finish()
    }
    fun removeItemFromFirestore(itemId: String) {
        db.collection("items").document(itemId).delete()
    }


}
