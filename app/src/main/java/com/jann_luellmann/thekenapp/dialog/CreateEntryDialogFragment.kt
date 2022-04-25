package com.jann_luellmann.thekenapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.jann_luellmann.thekenapp.MainActivity
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.adapter.Entry
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.Drink
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.util.Editable
import com.jann_luellmann.thekenapp.data.view_model.CustomerViewModel
import com.jann_luellmann.thekenapp.data.view_model.DrinkViewModel
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel
import com.jann_luellmann.thekenapp.databinding.DialogEntryDateBinding
import com.jann_luellmann.thekenapp.databinding.DialogEntryEdittextBinding
import com.jann_luellmann.thekenapp.databinding.DialogEntryMoneyBinding
import com.jann_luellmann.thekenapp.databinding.DialogFragmentEditEntryBinding
import com.jann_luellmann.thekenapp.util.Prefs
import com.jann_luellmann.thekenapp.util.TextUtil
import com.jann_luellmann.thekenapp.util.Util.getStatusBarHeight
import com.jann_luellmann.thekenapp.util.Util.isTablet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Field
import java.util.*

class CreateEntryDialogFragment<T>(
    private val item: T
) : DialogFragment() {

    private lateinit var binding: DialogFragmentEditEntryBinding
    private val entries: MutableList<Entry> = ArrayList()
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
        binding.title.text = context?.getString(R.string.create_title) ?: "Empty Title"
        for (v in generateInputs(item as Any)) binding.fieldsHolder.addView(v)
        binding.fieldsHolder.invalidate()
        binding.deleteButton.visibility = View.GONE
        binding.cancelButton.setOnClickListener { b: View? -> dismiss() }
        binding.saveButton.setOnClickListener { b: View? -> createEntry() }
    }

    private fun generateInputs(item: Any): List<View> {
        val fields = item.javaClass.declaredFields
        val views: MutableList<View> = ArrayList()
        for (field in fields) {
            if (field.isAnnotationPresent(Editable::class.java)) {
                if (field.type == String::class.java) {
                    val binding = DialogEntryEdittextBinding.inflate(
                        layoutInflater
                    )
                    attach(binding.textView, binding.editText, field)
                    views.add(binding.root)
                } else if (field.type == Long::class.javaPrimitiveType) {
                    val binding = DialogEntryMoneyBinding.inflate(
                        layoutInflater
                    )
                    attach(binding.textView, binding.editText, field)
                    views.add(binding.root)
                } else if (field.type == Date::class.java) {
                    val binding = DialogEntryDateBinding.inflate(
                        layoutInflater
                    )
                    attach(binding.textView, binding.editText, field)
                    views.add(binding.root)
                }
            }
        }
        return views
    }

    private fun attach(textView: TextView, valueView: View, field: Field) {
        textView.text = getString(field.getAnnotation(Editable::class.java).stringId)

        // Handle Date EditText
        if (field.type == Date::class.java && valueView is EditText) {
            valueView.setOnClickListener {
                val newFragment = DatePickerFragment(valueView)
                newFragment.show(parentFragmentManager, "datePicker")
            }
        }
        entries.add(Entry(field.name, valueView))
    }

    private fun createEntry() {
        context?.let {
            val eventId = Prefs.getCurrentEvent(it)
            when (item) {
                is Drink -> {
                    val drink = item as Drink
                    for (entry in entries) {
                        val value = (entry.value as EditText).text.toString()
                        if (value.isEmpty()) {
                            Toast.makeText(it, R.string.error_fill_fields, Toast.LENGTH_LONG).show()
                            return
                        }
                        when (entry.name) {
                            "name" -> drink.name = TextUtil.FirstLetterUpperCase(value)
                            "price" -> drink.price = value.toLong()
                        }
                    }
                    DrinkViewModel().insert(eventId, drink)
                }
                is Customer -> {
                    val customer = item as Customer
                    for (entry in entries) {
                        val value = (entry.value as EditText).text.toString()
                        if (value.isEmpty()) {
                            Toast.makeText(it, R.string.error_fill_fields, Toast.LENGTH_LONG).show()
                            return
                        }
                        if ("name" == entry.name) {
                            customer.name = TextUtil.FirstLetterUpperCase(value)
                        }
                    }
                    CustomerViewModel().insert(eventId, customer)
                }
                is Event -> {
                    val event = item as Event
                    for (entry in entries) {
                        val value = (entry.value as EditText).text.toString()
                        if (value.isEmpty()) {
                            Toast.makeText(it, R.string.error_fill_fields, Toast.LENGTH_LONG).show()
                            return
                        }
                        when (entry.name) {
                            "name" -> event.name = TextUtil.FirstLetterUpperCase(value)
                            "date" -> event.date = TextUtil.stringToDate(value)
                        }
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        val newEventId = EventViewModel().insert(event)
                        Prefs.putCurrentEvent(it, newEventId)

                        // Update current event in MainActivity
                        if (it is MainActivity) {
                            it.updateCurrentEvent(newEventId)
                        }
                    }
                }
                else -> {}
            }
        }
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            if (showsDialog) {
                if (!it.isTablet()) {
                    val metrics = resources.displayMetrics
                    val width = metrics.widthPixels
                    val height = metrics.heightPixels - it.getStatusBarHeight()

                    dialog?.window?.setLayout(width, height)
                }
            }
        }
    }
}