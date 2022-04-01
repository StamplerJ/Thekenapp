package com.jann_luellmann.thekenapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.evrencoskun.tableview.TableView
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel
import com.jann_luellmann.thekenapp.util.Prefs
import com.jann_luellmann.thekenapp.view.*
import java.util.*

class ListFragment : Fragment(), EventChangedListener {
    private lateinit var tableView: TableView
    private var adapter: ListTableViewAdapter = ListTableViewAdapter()
    private var eventWithDrinksAndCustomersViewModel: EventWithDrinksAndCustomersViewModel? = null
    private val rowHeaderList: MutableList<RowHeader?> = mutableListOf()
    private val columnHeaderList: MutableList<ColumnHeader?> = mutableListOf()
    private val cellList: MutableList<List<Cell?>> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)
        tableView = view.findViewById(R.id.tableView)
        tableView.isIgnoreSelectionColors = true
        tableView.setHasFixedWidth(false)
        adapter.tableView = tableView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tableView.setAdapter(adapter)
        adapter.setAllItems(columnHeaderList, rowHeaderList, cellList)
        val eventId: Long = Prefs.getLong(context, Prefs.CURRENT_EVENT, 1L)
        eventWithDrinksAndCustomersViewModel =
            ViewModelProvider(this).get(EventWithDrinksAndCustomersViewModel::class.java)
        onEventUpdated(eventId)
    }

    override fun onEventUpdated(eventId: Long) {
        if (eventWithDrinksAndCustomersViewModel == null) {
            eventWithDrinksAndCustomersViewModel = if (isAdded) {
                ViewModelProvider(this).get(EventWithDrinksAndCustomersViewModel::class.java)
            } else {
                return
            }
        }

        eventWithDrinksAndCustomersViewModel?.findById(eventId)?.observe(
            viewLifecycleOwner
        ) { event: EventWithDrinksAndCustomers? ->
            columnHeaderList.clear()
            rowHeaderList.clear()
            cellList.clear()
            setData(event)
            adapter.setAllItems(columnHeaderList, rowHeaderList, cellList)
            adapter.notifyDataSetChanged()
            tableView.tableViewListener = ListTableViewClickListener(requireContext(), event)
        }
    }

    private fun setData(event: EventWithDrinksAndCustomers?) {
        if (event == null) return
        for (drink in event.drinks) {
            columnHeaderList.add(ColumnHeader(drink.toString()))
        }
        columnHeaderList.add(ColumnHeader(getString(R.string.total)))
        var total = 0f
        event.customerWithBoughts.sort()
        for (customer in event.customerWithBoughts!!) {
            rowHeaderList.add(RowHeader(customer.customer.toString()))
            var sum = 0f
            val row: MutableList<Cell> = ArrayList()
            for (drink in event.drinks) {
                var isBoughtPresent = false
                for (bought in customer.getBoughtsByEvent(event.event?.eventId ?: -1)) {
                    if (bought.drinkId == drink.drinkId) {
                        sum += bought.amount * drink.price
                        row.add(Cell(bought.amount))
                        isBoughtPresent = true
                        break
                    }
                }
                if (!isBoughtPresent) {
                    row.add(Cell(0))
                }
            }
            total += sum
            row.add(Cell(String.format(Locale.GERMAN, "%.2f€", sum / 100f)))
            cellList.add(row)
        }
        rowHeaderList.add(RowHeader(getString(R.string.total)))

        // Add total sum of all customers
        val lastRow: MutableList<Cell> = ArrayList()
        for (i in 0 until event.drinks.size) {
            lastRow.add(Cell(""))
        }
        lastRow.add(Cell(String.format(Locale.GERMAN, "%.2f€", total / 100f)))
        cellList.add(lastRow)
    }
}