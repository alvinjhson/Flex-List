package com.example.flexlist

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var db : FirebaseFirestore
    lateinit var auth : FirebaseAuth
    lateinit var adapter: ToDoListRecyclerAdapter

    val dropDownList= arrayOf("Daily List","Shopping List")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listSpinner = findViewById<Spinner>(R.id.listSpinner)
        db = Firebase.firestore
        auth = Firebase.auth
        val settingButton = findViewById<ImageButton>(R.id.settingsImageButton)


        loadItems()

        settingButton.setOnClickListener {
            val intent = Intent(this,UserSettings::class.java)
            startActivity(intent)

        }

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
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            signInAnonymously()
        } else {
            updateUI(currentUser)
        }
    }
    fun signInAnonymously() {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(
                baseContext,
                "Välkommen tillbaka, anonyma användare!",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }




    fun loadItems() {
        val user = auth.currentUser
        if (user == null) {
            return
        }
        db.collection("users").document(user.uid).collection("items").get().addOnSuccessListener { documents ->
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