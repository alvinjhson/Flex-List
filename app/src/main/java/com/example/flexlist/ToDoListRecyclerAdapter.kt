package com.example.flexlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoListRecyclerAdapter(val context: Context, val lists: List<ToDoList>)  : RecyclerView.Adapter<ToDoListRecyclerAdapter.ViewHolder>(){

        var layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var itemNameView = itemView.findViewById<TextView>(R.id.itemNameTextView)
        var checkBox = itemView.findViewById<CheckBox>(R.id.itemNameCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val itemList = lists[position]
       holder.itemNameView.text = itemList.itemName
       holder.checkBox.isChecked = itemList.checkBox
    }


}