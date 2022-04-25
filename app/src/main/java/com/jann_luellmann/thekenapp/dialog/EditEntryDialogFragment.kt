package com.jann_luellmann.thekenapp.dialog

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.adapter.Entry
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.Drink
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.util.Editable
import com.jann_luellmann.thekenapp.data.view_model.CustomerViewModel
import com.jann_luellmann.thekenapp.data.view_model.DrinkViewModel
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithCustomersViewModel
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksViewModel
import com.jann_luellmann.thekenapp.databinding.DialogEntryDateBinding
import com.jann_luellmann.thekenapp.databinding.DialogEntryEdittextBinding
import com.jann_luellmann.thekenapp.databinding.DialogEntryMoneyBinding
import com.jann_luellmann.thekenapp.databinding.DialogFragmentEditEntryBinding
import com.jann_luellmann.thekenapp.util.Prefs
import com.jann_luellmann.thekenapp.util.TextUtil
import com.jann_luellmann.thekenapp.util.Util.getStatusBarHeight
import com.jann_luellmann.thekenapp.util.Util.isTablet
import java.lang.reflect.Field
import java.util.*

class EditEntryDialogFragment(
    private var item: Any
) : DialogFragment() {

    private lateinit var binding: DialogFragmentEditEntryBinding
    private val entries: MutableList<Entry> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentEditEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = context?.getString(R.string.edit_title)

        for (v in generateInputs(item))
            binding.fieldsHolder.addView(v)

        binding.fieldsHolder.invalidate()
        binding.deleteButton.setOnClickListener { showConfirmDialog() }
        binding.cancelButton.setOnClickListener { dismiss() }
        binding.saveButton.setOnClickListener { updateEntry() }
    }

    private fun generateInputs(item: Any): List<View> {
        val fields = item.javaClass.declaredFields
        val views: MutableList<View> = ArrayList()
        for (field in fields) {
            if (field.isAnnotationPresent(Editable::class.java)) {
                when (field.type) {
                    String::class.java -> {
                        val binding = DialogEntryEdittextBinding.inflate(
                            layoutInflater
                        )
                        attach(binding.textView, binding.editText, field)
                        views.add(binding.root)
                    }
                    Long::class.javaPrimitiveType -> {
                        val binding = DialogEntryMoneyBinding.inflate(
                            layoutInflater
                        )
                        attach(binding.textView, binding.editText, field)
                        views.add(binding.root)
                    }
                    Date::class.java -> {
                        val binding = DialogEntryDateBinding.inflate(
                            layoutInflater
                        )
                        attach(binding.textView, binding.editText, field)
                        views.add(binding.root)
                    }
                }
            }
        }
        return views
    }

    private fun attach(textView: TextView, valueView: View, field: Field) {
        textView.text = getString(field.getAnnotation(Editable::class.java).stringId)
        try {
            if (valueView is EditText) {
                field.isAccessible = true
                var text: String? = field[item]?.toString()

                // Handle Date EditText
                if (field.type == Date::class.java) {
                    text = TextUtil.dateToString(field[item] as Date)
                    valueView.setOnClickListener {
                        val datePickerFragment = DatePickerFragment(valueView)
                        datePickerFragment.show(parentFragmentManager, "datePicker")
                    }
                }
                valueView.setText(text)
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        entries.add(Entry(field.name, valueView))
    }

    private fun updateEntry() {
        if (item is Drink) {
            val drink = item as Drink
            for (entry in entries) {
                val value = (entry.value as EditText).text.toString()
                if (value.isEmpty()) {
                    Toast.makeText(context, R.string.error_fill_fields, Toast.LENGTH_LONG).show()
                    return
                }
                when (entry.name) {
                    "name" -> {
                        val name = (entry.value).text.toString()
                        drink.name = TextUtil.FirstLetterUpperCase(name)
                    }
                    "price" -> {
                        val price = (entry.value as EditText).text.toString().toLong()
                        drink.price = price
                    }
                }
            }
            DrinkViewModel().update(drink)
        } else if (item is Customer) {
            val customer: Customer = item as Customer
            for (entry in entries) {
                val value = (entry.value as EditText).text.toString()
                if (value.isEmpty()) {
                    Toast.makeText(context, R.string.error_fill_fields, Toast.LENGTH_LONG).show()
                    return
                }
                if ("name" == entry.name) {
                    val name = (entry.value as EditText).text.toString()
                    customer.name = TextUtil.FirstLetterUpperCase(name)
                }
            }
            CustomerViewModel().update(customer)
        } else if (item is Event) {
            val event = item as Event
            for (entry in entries) {
                val value = (entry.value as EditText).text.toString()
                if (value.isEmpty()) {
                    Toast.makeText(context, R.string.error_fill_fields, Toast.LENGTH_LONG).show()
                    return
                }
                when (entry.name) {
                    "name" -> event.name = TextUtil.FirstLetterUpperCase(value)
                    "date" -> {
                        val date = TextUtil.stringToDate(value)
                        event.date = date
                    }
                }
            }
            EventViewModel().update(event)
        }
        dismiss()
    }

    private fun deleteEntry() {
        context?.let {
            val eventId = Prefs.getCurrentEvent(it)
            when (item) {
                is Drink -> {
                    EventWithDrinksViewModel().delete(eventId, item as Drink)
                }
                is Customer -> {
                    EventWithCustomersViewModel().delete(eventId, item as Customer)
                }
                is Event -> {
                    EventViewModel().delete(item as Event)
                }
            }
        }
    }

    private fun showConfirmDialog() {
        AlertDialog.Builder(context).apply {
            setTitle(R.string.confirm_title)
            setMessage(R.string.confirm_message)
            setPositiveButton(R.string.confirm_yes) { _, _ ->
                deleteEntry()
                dismiss()
            }
            setNegativeButton(R.string.confirm_no) { _, _ -> dismiss() }
        }.show()
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            if (showsDialog) {
                if(!it.isTablet()) {
                    val metrics = resources.displayMetrics
                    val width = metrics.widthPixels
                    val height = metrics.heightPixels - it.getStatusBarHeight()

                    dialog?.window?.setLayout(width, height)
                }
            }
        }
    }
}