package com.jann_luellmann.thekenapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.data.util.Editable
import java.math.BigDecimal

@Entity
class Drink(
    @PrimaryKey(autoGenerate = true)
    var drinkId: Long = 0,

    @ColumnInfo(name = "dname")
    @field:Editable(stringId = R.string.identifier)
    var name: String = "",

    @ColumnInfo(name = "price")
    @field:Editable(stringId = R.string.price)
    var price: Long = 0
) {
    override fun toString(): String {
        return name + ": " + priceAsText()
    }

    private fun priceAsText(): String {
        return BigDecimal(price).movePointLeft(2).toString() + " €"
    }
}