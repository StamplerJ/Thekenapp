package com.jann_luellmann.thekenapp.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(dateEditText: EditText?) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val dateEditText: EditText? = dateEditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        dateEditText?.setText(
            String.format(
                Locale.GERMANY,
                "%d.%d.%d",
                day,
                month + 1,
                year
            )
        )
    }

}