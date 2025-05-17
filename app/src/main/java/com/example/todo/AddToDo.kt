package com.example.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.CalendarContract.Calendars
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo.databinding.ActivityAddToDoBinding
import com.example.todo.databinding.ActivityMainBinding
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Locale

class AddToDo : AppCompatActivity() {
    lateinit var binding: ActivityAddToDoBinding
    private var calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToDoBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener{
            onBackPressed()
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
            val timePickerDialog = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY,hour)
                calendar.set(Calendar.MINUTE,minute)

                binding.txtTime.text = SimpleDateFormat("HH:mm").format(calendar.time)
            }
            TimePickerDialog(this, timePickerDialog, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show()
        }

        binding.fbSave.setOnClickListener {
            var title = binding.etTitle.text.toString()
            var description = binding.etDes.text.toString()
            var date = binding.txtDate.text.toString()
            var time = binding.txtTime.text.toString()
            Log.d("Data","$title - $description - $date - $time")
        }

        setContentView(binding.root)
    }
}