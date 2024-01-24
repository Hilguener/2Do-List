package com.hilguener.a2do.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.hilguener.a2do.R
import com.hilguener.a2do.databinding.ActivityNewTaskBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewTaskActivity : AppCompatActivity() {

    private lateinit var editTextEndDate: EditText
    private val calendarEnd: Calendar = Calendar.getInstance()
    private lateinit var binding: ActivityNewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextEndDate = binding.editTextEndDate
        editTextEndDate.inputType = InputType.TYPE_NULL
        editTextEndDate.isFocusable = false  // Impede que o EditText seja editável


        setDatePicker()

        val categories = arrayOf("Trabalho", "Profissional", "Estudos", "Lazer")
        val adapter = ArrayAdapter(this, R.layout.list_item, categories)
        (binding.textFieldCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setDatePicker() {
        val currentDate = Calendar.getInstance()

        editTextEndDate.setOnClickListener {
            showDatePickerDialog(currentDate)
        }
    }

    private fun showDatePickerDialog(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                if (calendarEnd.before(calendar)) {
                    calendarEnd.timeInMillis = calendar.timeInMillis
                }
                updateEditText(editTextEndDate, calendarEnd)
            },
            year,
            month,
            day
        ).apply {
            datePicker.minDate = System.currentTimeMillis()
            show()
        }
    }

    private fun updateEditText(editText: EditText, calendar: Calendar) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        editText.setText(dateFormat.format(calendar.time))
    }
}


