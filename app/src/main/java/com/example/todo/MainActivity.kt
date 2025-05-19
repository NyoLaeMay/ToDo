package com.example.todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db: DatabaseHelper
    lateinit var adapter: ToDoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        db = DatabaseHelper(this)
        binding.addTodo.setOnClickListener{
            startActivity(Intent(this,AddToDo::class.java))
        }

        var layoutManager = LinearLayoutManager(this)
        binding.rvTodo.layoutManager = layoutManager
        adapter = ToDoAdapter(db.getAllData(),this)
        binding.rvTodo.adapter = adapter
        setContentView(binding.root)
    }
    override fun onResume(){
        super.onResume()
        adapter.refreshData(db.getAllData())
    }
}