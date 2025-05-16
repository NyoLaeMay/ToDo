package com.example.todo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo.databinding.ActivityAddToDoBinding
import com.example.todo.databinding.ActivityMainBinding

class AddToDo : AppCompatActivity() {
    lateinit var binding: ActivityAddToDoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}