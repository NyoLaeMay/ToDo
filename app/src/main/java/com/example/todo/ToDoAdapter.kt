package com.example.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(var todoList: List<TodoList>,context: Context) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    class ToDoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.cardTitle)
        var date = itemView.findViewById<TextView>(R.id.cardDate)
        var imgEdit = itemView.findViewById<ImageView>(R.id.imgEdit)
        var imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.ToDoViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.todo_card,parent,false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoViewHolder, position: Int) {
        var todo = todoList[position]
        holder.title.text = todo.title
        holder.date.text = todo.date
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
    fun refreshData(newNote: List<TodoList>){
        todoList  = newNote
        notifyDataSetChanged()
    }
}