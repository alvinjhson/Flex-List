package com.example.flexlist

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.text.FieldPosition
import java.time.LocalTime

const val ITEM_POSISTION_KEY = "ITEM_POSISTION"

const val POSISTION_NOT_SET = -1
class CreateAndEditTaskActivity : AppCompatActivity() {

    lateinit var db : FirebaseFirestore

    lateinit var nameEditText: EditText
    lateinit var timeImageView : ImageView
    lateinit var timeTextView1 : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_and_edit_task)


        db = Firebase.firestore
        val delete = findViewById<ImageButton>(R.id.removeItemButton)
        nameEditText = findViewById(R.id.editTextText)
        timeImageView = findViewById(R.id.timeImageView)
        timeTextView1 = findViewById(R.id.timeTextView1)


        val saveButton = findViewById<Button>(R.id.saveButton)
        val backButton = findViewById<ImageButton>(R.id.backImageButton)
        val itemPosistion = intent.getIntExtra(ITEM_POSISTION_KEY, POSISTION_NOT_SET)

        if (itemPosistion != POSISTION_NOT_SET) {
            displayItem(itemPosistion)
            saveButton.setOnClickListener {
                editItem(itemPosistion)

            }
            backButton.setOnClickListener {
                editItem(itemPosistion)

            }
            timeImageView.setOnClickListener {
                changeTime(itemPosistion)
            }
            delete.setOnClickListener {
                removeItem(itemPosistion)


            }
        }else {
            timeImageView.setOnClickListener {
                val timePickerDialog = TimePickerDialog(this,
                    { _, hourOfDay, minute ->
                        val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                        timeTextView1.text = formattedTime
                    },
                    LocalTime.now().hour,
                    LocalTime.now().minute,
                    true
                )
                timePickerDialog.show()
            }
            saveButton.setOnClickListener {

                addItem()
            }
            backButton.setOnClickListener {
               finish()

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
        timeTextView1.setText(item.time)
    }

    fun addItem() {
        val name = nameEditText.text.toString()
        val check = false
        val time = timeTextView1.text.toString()
        val item = ToDoList(name, time, check, "")
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
    fun changeTime(position: Int) {
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                    timeTextView1.text = formattedTime
                    DataManager.item[position].time = timeTextView1.text.toString()
                    val id = DataManager.item[position].id
                    db.collection("items").document(id).set(DataManager.item[position])
                },
                LocalTime.now().hour,
                LocalTime.now().minute,
                true
            )
            timePickerDialog.show()
    }

}
