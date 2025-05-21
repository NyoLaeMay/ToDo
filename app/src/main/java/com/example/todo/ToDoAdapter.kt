package com.example.todo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(var todoList: List<TodoList>,var context: Context) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    private val db: DatabaseHelper = DatabaseHelper(context)
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
        holder.imgEdit.setOnClickListener{
            val intent = Intent(context, UpdateData::class.java)
            intent.putExtra("id",todo.id)
            context.startActivity(intent)

        }
        holder.imgDelete.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to delete this note?")
            builder.setCancelable(false)//touch outside to cancel / to dismiss dialog
            builder.setPositiveButton("OK"){dialog, which ->
                db.deleteDate(todo.id)
                refreshData(db.getAllData())
                Toast.makeText(context,"Deleted an item", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }
            val dialog : AlertDialog = builder.create()
            dialog.show()
            }

        }
    override fun getItemCount(): Int {
        return todoList.size
    }
    fun refreshData(newNote: List<TodoList>){
        todoList  = newNote
        notifyDataSetChanged()
    }
}