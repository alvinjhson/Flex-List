package com.example.flexlist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ToDoListRecyclerAdapter(val context: Context, var lists: List<ToDoList>)  : RecyclerView.Adapter<ToDoListRecyclerAdapter.ViewHolder>(){

        var layoutInflater = LayoutInflater.from(context)


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var itemNameView = itemView.findViewById<TextView>(R.id.itemNameTextView)
        var checkBox = itemView.findViewById<CheckBox>(R.id.itemNameCheckBox)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.removeItemButton)
        var itemPosistion = 0


        init {
            itemView.setOnClickListener {
                val intent = Intent(context,CreateAndEditTaskActivity::class.java)
                intent.putExtra(ITEM_POSISTION_KEY,itemPosistion)
                context.startActivity(intent)
            }
            //deleteButton.setOnClickListener {
                //removeItem(itemPosistion)

            //}



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item,parent,false)
        return ViewHolder(itemView)
    }
    //fun removeItem(position: Int) {
       // DataManager.item.removeAt(position)
       // notifyDataSetChanged()
    //}


    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var itemList = lists[position]
       holder.itemNameView.text = itemList.itemName
       holder.checkBox.isChecked = itemList.checkBox
       holder.itemPosistion = position
    }



}