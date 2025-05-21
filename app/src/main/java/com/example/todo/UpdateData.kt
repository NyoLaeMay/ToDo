package com.example.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo.databinding.ActivityUpdateDataBinding
import java.text.SimpleDateFormat
import java.util.Locale

class UpdateData : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateDataBinding
    private val db: DatabaseHelper = DatabaseHelper(this)
    private var calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)

        var id = intent.getIntExtra("id",-1)
        var todoData = db.getNoteByID(id)
        binding.etTitle.setText(todoData.title)
        binding.etDes.setText(todoData.description)
        binding.txtDate.text = todoData.date
        binding.txtTime.text = todoData.time

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }
        binding.btnUpdate.setOnClickListener{
            var title = binding.etTitle.text.toString()
            var description = binding.etDes.text.toString()
            var date = binding.txtDate.text.toString()
            var time = binding.txtTime.text.toString()

            var todo = TodoList(id,title,description,date,time)
            db.updateData(todo)
            finish()
            Toast.makeText(this,"Data Updated", Toast.LENGTH_SHORT).show()
        }

        binding.imgDate.setOnClickListener{
            //create DatePickerDialog
            val datePickerDialog = DatePickerDialog(this,{DatePicker,year:Int,monthOfYear:Int,dayOfMonth:Int ->
                //create new calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year,monthOfYear,dayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
                val formatDate = dateFormat.format(selectedDate.time)

                binding.txtDate.text = formatDate.toString()
            },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        binding.imgTime.setOnClickListener{
            //create TimePickerDialog
            val timePickerDialog = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY,hour)
                calendar.set(Calendar.MINUTE,minute)

                binding.txtTime.text = SimpleDateFormat("HH:mm").format(calendar.time)
            }
            TimePickerDialog(this, timePickerDialog, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show()
        }

        setContentView(binding.root)
    }
}