package com.jann_luellmann.thekenapp.dialog

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
import com.jann_luellmann.thekenapp.databinding.DialogEntryDateBinding
import com.jann_luellmann.thekenapp.databinding.DialogEntryEdittextBinding
import com.jann_luellmann.thekenapp.databinding.DialogEntryMoneyBinding
import com.jann_luellmann.thekenapp.databinding.DialogFragmentEditEntryBinding
import com.jann_luellmann.thekenapp.util.Prefs
import com.jann_luellmann.thekenapp.util.TextUtil
import java.lang.reflect.Field
import java.util.*

class CreateEntryDialogFragment<T>(
    private val item: T
) : DialogFragment() {

    private var binding: DialogFragmentEditEntryBinding? = null
    private val entries: MutableList<Entry> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        dialog!!.window!!.setLayout((width * 0.8f).toInt(), (height * 0.8f).toInt())
        binding = DialogFragmentEditEntryBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.title.text = context?.getString(R.string.create_title) ?: "Empty Title"
        for (v in generateInputs(item as Any)) binding!!.fieldsHolder.addView(v)
        binding!!.fieldsHolder.invalidate()
        binding!!.deleteButton.visibility = View.GONE
        binding!!.cancelButton.setOnClickListener { b: View? -> dismiss() }
        binding!!.saveButton.setOnClickListener { b: View? -> createEntry() }
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
            val editText = valueView
            editText.setOnClickListener { view: View? ->
                val newFragment = DatePickerFragment(editText)
                newFragment.show(parentFragmentManager, "datePicker")
            }
        }
        entries.add(Entry(field.name, valueView))
    }

    private fun createEntry() {
        val eventId = Prefs.getLong(context, Prefs.CURRENT_EVENT, 1L)
        if (item is Drink) {
            val drink = item as Drink
            for (entry in entries) {
                val value = (entry.value as EditText).text.toString()
                if (value.isEmpty()) {
                    Toast.makeText(context, R.string.error_fill_fields, Toast.LENGTH_LONG).show()
                    return
                }
                when (entry.name) {
                    "name" -> drink.name = TextUtil.FirstLetterUpperCase(value)
                    "price" -> drink.price = value.toLong()
                }
            }
            DrinkViewModel().insert(eventId, drink)
        } else if (item is Customer) {
            val customer = item as Customer
            for (entry in entries) {
                val value = (entry.value as EditText).text.toString()
                if (value.isEmpty()) {
                    Toast.makeText(context, R.string.error_fill_fields, Toast.LENGTH_LONG).show()
                    return
                }
                if ("name" == entry.name) {
                    customer.name = TextUtil.FirstLetterUpperCase(value)
                }
            }
            CustomerViewModel().insert(eventId, customer)
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
                    "date" -> event.date = TextUtil.stringToDate(value)
                }
            }
            EventViewModel().insert(event)
        }
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        if (showsDialog) {
            val metrics = resources.displayMetrics
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            dialog!!.window!!.setLayout((width * 0.6f).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}