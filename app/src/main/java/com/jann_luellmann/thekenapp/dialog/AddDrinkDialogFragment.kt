package com.jann_luellmann.thekenapp.dialog

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.adapter.DrinksAdapter
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventAndCustomerWithDrinksViewModel
import com.jann_luellmann.thekenapp.databinding.DialogFragmentAddDrinkBinding
import com.jann_luellmann.thekenapp.util.Util.getStatusBarHeight
import com.jann_luellmann.thekenapp.util.Util.isTablet

class AddDrinkDialogFragment(
    private val eventAndCustomerWithDrinksViewModel: EventAndCustomerWithDrinksViewModel,
    private val eventId: Long,
    private val customerId: Long
) : DialogFragment(R.layout.dialog_fragment_add_drink) {

    private lateinit var binding: DialogFragmentAddDrinkBinding

    private var adapter: DrinksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentAddDrinkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createEntries()

        binding.paidButton.setOnClickListener {
            pay()
        }
        binding.saveButton.setOnClickListener {
            save()
            dismiss()
        }
        binding.cancelButton.setOnClickListener { dismiss() }
    }

    private fun pay() {
        AlertDialog.Builder(context).apply {
            setTitle(R.string.confirm_pay_title)
            setMessage(R.string.confirm_pay_message)
            setPositiveButton(R.string.confirm_yes) { _, _ ->
                adapter?.resetAmountToZero(eventAndCustomerWithDrinksViewModel, eventId, customerId)
                dismiss()
            }
            setNegativeButton(R.string.confirm_no) { _, _ -> dismiss() }
        }.show()
    }

    private fun createEntries() {
        eventAndCustomerWithDrinksViewModel.findAllByEventAndCustomer(eventId, customerId)
            .observe(viewLifecycleOwner) {
                binding.title.text = it[0].customer.name
                adapter = DrinksAdapter(requireContext(), it)
                binding.drinksRecyclerView.adapter = adapter
            }
    }

    private fun save() {
        adapter?.saveUpdates(eventAndCustomerWithDrinksViewModel, eventId, customerId)
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