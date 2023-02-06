package com.example.text

import java.text.SimpleDateFormat
import java.util.*

fun main(args: Array<String>) {
    val sevenDaysCalendar = listOf<Calendar>(
        Calendar.getInstance(),
        Calendar.getInstance().apply { add(Calendar.DATE,1) },
        Calendar.getInstance().apply { add(Calendar.DATE,2) },
        Calendar.getInstance().apply { add(Calendar.DATE,3) },
        Calendar.getInstance().apply { add(Calendar.DATE,4) },
        Calendar.getInstance().apply { add(Calendar.DATE,5) },
        Calendar.getInstance().apply { add(Calendar.DATE,6) },
    )
val calendar1 = sevenDaysCalendar[6]
    val simpleFormatter = SimpleDateFormat("MM月dd日",Locale.CHINA)
    println(
        simpleFormatter.format(calendar1.time)
    )
}