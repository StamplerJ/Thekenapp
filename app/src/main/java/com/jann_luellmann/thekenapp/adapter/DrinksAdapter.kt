package com.jann_luellmann.thekenapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.data.model.relationship.CustomerEventWithBoughtDrinks
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventAndCustomerWithDrinksViewModel
import com.jann_luellmann.thekenapp.databinding.DialogEntryDrinkBinding

class DrinksAdapter(
    private val context: Context,
    private val items: List<CustomerEventWithBoughtDrinks>
) : RecyclerView.Adapter<DrinksAdapter.DrinkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_entry_drink, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val item = items[position]
        holder.init(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun saveUpdates(eventAndCustomerWithDrinksViewModel: EventAndCustomerWithDrinksViewModel, eventId: Long, customerId: Long) {
        items.forEach {
            eventAndCustomerWithDrinksViewModel.updateAmount(eventId, customerId, it.drink.drinkId, it.amount)
        }
    }

    fun resetAmountToZero(eventAndCustomerWithDrinksViewModel: EventAndCustomerWithDrinksViewModel, eventId: Long, customerId: Long) {
        items.forEach {
            eventAndCustomerWithDrinksViewModel.updateAmount(eventId, customerId, it.drink.drinkId, 0)
        }
    }

    class DrinkViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val binding: DialogEntryDrinkBinding = DialogEntryDrinkBinding.bind(itemView)

        var original: Int = 0
        var added: Int = 0

        fun init(item: CustomerEventWithBoughtDrinks) {
            binding.drinkName.text = item.drink.name
            original = item.amount

            binding.drinkAmount.text = original.toString()
            updateAmountAddedDisplay(added)

            binding.decrease1.setOnClickListener {
                updateAmount(item, -1)
            }
            binding.increase1.setOnClickListener {
                updateAmount(item, 1)
            }
            binding.increase10.setOnClickListener {
                updateAmount(item, 10)
            }
        }

        private fun updateAmount(item: CustomerEventWithBoughtDrinks, amount: Int) {
            if(added + amount <= 0)
                return

            item.amount += amount
            added += amount

            updateAmountAddedDisplay(added)
        }

        private fun updateAmountAddedDisplay(amount: Int) {
            binding.drinkAmountAdded.text = "($amount)"
        }
    }
}