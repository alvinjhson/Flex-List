package com.example.flexlist

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class ToDoListRecyclerAdapter(val context: Context, var lists: List<ToDoList>)  : RecyclerView.Adapter<ToDoListRecyclerAdapter.ViewHolder>(){

        var layoutInflater = LayoutInflater.from(context)
        lateinit var db : FirebaseFirestore


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var itemNameView = itemView.findViewById<TextView>(R.id.itemNameTextView)
        var checkBox = itemView.findViewById<CheckBox>(R.id.itemCheckBox)
        var itemPosistion = 0
        var timeTextView = itemView.findViewById<TextView>(R.id.timeTextView2)




        init {
            itemView.setOnClickListener {
                val intent = Intent(context,CreateAndEditTaskActivity::class.java)
                intent.putExtra(ITEM_POSISTION_KEY,itemPosistion)
                context.startActivity(intent)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var itemList = lists[position]
       holder.itemNameView.text = itemList.itemName
       //holder.checkBox.isChecked = itemList.checkBox
       holder.itemPosistion = position
       holder.timeTextView.text = itemList.time

        holder.checkBox.setOnCheckedChangeListener(null)

        // Sätt checkboxens tillstånd
        holder.checkBox.isChecked = itemList.checkBox

        // Sätt en ny lyssnare
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            // Uppdatera din data när checkboxen ändras
            itemList.checkBox = isChecked
            updateDatabase(itemList)
            notifyItemChanged(position)
        }

        if (itemList.checkBox){
            holder.itemNameView.paintFlags = holder.itemNameView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.itemNameView.paintFlags = holder.itemNameView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
    fun updateDatabase(itemList: ToDoList) {
        db = Firebase.firestore
        // Uppdatera databasen med det nya värdet för checkboxen
        val id = itemList.id
        db.collection("items").document(id).set(itemList)
    }


}

