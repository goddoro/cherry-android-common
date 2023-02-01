package doro.android.core.util

import java.text.DecimalFormat
import java.text.NumberFormat

object MoneyUtil {

    fun numberToMoney(number: Int): String{
        val formatter: NumberFormat = DecimalFormat("#,###")
        return formatter.format(number)
    }
}