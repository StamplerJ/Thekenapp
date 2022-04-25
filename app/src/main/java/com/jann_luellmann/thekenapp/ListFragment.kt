package com.jann_luellmann.thekenapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.evrencoskun.tableview.TableView
import com.jann_luellmann.thekenapp.data.db.Database
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.relationship.CustomerEventWithBoughtDrinks
import com.jann_luellmann.thekenapp.data.repository.EventAndCustomerWithDrinksRepository
import com.jann_luellmann.thekenapp.data.repository.EventWithDrinksAndCustomersRepository
import com.jann_luellmann.thekenapp.data.view_model.ViewModelFactory
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventAndCustomerWithDrinksViewModel
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel
import com.jann_luellmann.thekenapp.databinding.FragmentListBinding
import com.jann_luellmann.thekenapp.dialog.AddDrinkDialogFragment
import com.jann_luellmann.thekenapp.util.Prefs
import com.jann_luellmann.thekenapp.view.*
import java.util.*

class ListFragment : Fragment(), EventChangedListener, OnCustomerClickedListener {

    private lateinit var binding: FragmentListBinding

    private var adapter: ListTableViewAdapter = ListTableViewAdapter()

    private val eventWithDrinksAndCustomersViewModel: EventWithDrinksAndCustomersViewModel by viewModels {
        ViewModelFactory(
            EventWithDrinksAndCustomersRepository(
                Database.getInstance().eventWithDrinksAndCustomersDAO()
            )
        )
    }

    private val eventAndCustomerWithDrinksViewModel: EventAndCustomerWithDrinksViewModel by viewModels {
        ViewModelFactory(
            EventAndCustomerWithDrinksRepository(
                Database.getInstance().eventAndCustomerWithDrinksDAO()
            )
        )
    }

    private val rowHeaderList: MutableList<RowHeader?> = mutableListOf()
    private val columnHeaderList: MutableList<ColumnHeader?> = mutableListOf()
    private val cellList: MutableList<List<Cell?>> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)

        binding.tableView.isIgnoreSelectionColors = true
        binding.tableView.setHasFixedWidth(false)
        adapter.tableView = binding.tableView

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tableView.setAdapter(adapter)
        adapter.setAllItems(columnHeaderList, rowHeaderList, cellList)

        val eventId: Long = Prefs.getCurrentEvent(view.context)
        onEventUpdated(eventId)

        binding.tableView.tableViewListener = ListTableViewClickListener()
    }

    override fun onEventUpdated(eventId: Long) {
        eventWithDrinksAndCustomersViewModel.findById(eventId)
            .observe(viewLifecycleOwner) { event ->
                if (event == null) {
                    binding.noEventsMessage.visibility = View.VISIBLE
                    return@observe
                }

                binding.noEventsMessage.visibility = View.GONE

                columnHeaderList.clear()
                event.drinks.sortedBy { it.name }.forEach {
                    columnHeaderList.add(ColumnHeader(it.toString()))
                }
                columnHeaderList.add(ColumnHeader(getString(R.string.total)))
            }

        eventAndCustomerWithDrinksViewModel.findAllByEvent(eventId).observe(viewLifecycleOwner) {
            rowHeaderList.clear()
            cellList.clear()
            setData(it)
            adapter.setAllItems(columnHeaderList, rowHeaderList, cellList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setData(event: List<CustomerEventWithBoughtDrinks>) {
        var total = 0f
        event
            .sortedBy { it.customer.name }
            .groupBy { it.customer }
            .forEach { (customer, entry) ->
                rowHeaderList.add(RowHeaderCustomer(customer, this))
                var sum = 0f
                val row: MutableList<Cell> = mutableListOf()

                entry.sortedBy { it.drink.name }.forEachIndexed { index, it ->
                    sum += it.amount * it.drink.price
                    row.add(
                        Cell(
                            if (columnHeaderList[index]?.data.toString() == it.drink.toString())
                                it.amount
                            else
                                "WRONG"
                        )
                    )
                }
                total += sum
                row.add(Cell(String.format(Locale.GERMAN, "%.2f€", sum / 100f)))
                cellList.add(row)
            }

        if (event.isNotEmpty()) {
            rowHeaderList.add(RowHeader(getString(R.string.total)))

            // Add last row with total sum of all customers
            cellList.add(mutableListOf<Cell>().apply {
                repeat(columnHeaderList.size - 1) { add(Cell("")) }
                add(Cell(String.format(Locale.GERMAN, "%.2f€", total / 100f)))
            })
        }
    }

    override fun onCustomerClicked(customer: Customer) {
        context?.let {
            val eventId = Prefs.getCurrentEvent(it)
            val dialog = AddDrinkDialogFragment(
                eventAndCustomerWithDrinksViewModel,
                eventId,
                customer.customerId
            )
            dialog.show(parentFragmentManager, "AddDrink")
        }
    }
}