package com.example.flexlist

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import java.time.LocalTime

class TimeActivity : AppCompatActivity() {

    lateinit var timePicker: TimePicker
    lateinit var timeTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)

        var timeTextView =  findViewById<TextView>(R.id.timeTextView)

        timeTextView.setOnClickListener {
            // Skapa en ny TimePickerDialog
            val timePickerDialog = TimePickerDialog(this,
                // Sätt upp en lyssnare för när användaren väljer en tid
                { _, hourOfDay, minute ->
                    // Formatera den valda tiden till en sträng
                    val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                    // Uppdatera texten i timeTextView med den formaterade tiden
                    timeTextView.text = formattedTime
                },
                // Sätt den initiala tiden till nu
                LocalTime.now().hour,
                LocalTime.now().minute,
                // Använd 24-timmarsformat
                true
            )
            // Visa dialogen
            timePickerDialog.show()
        }






    }







}