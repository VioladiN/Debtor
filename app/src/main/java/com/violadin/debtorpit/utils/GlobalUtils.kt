package com.violadin.debtorpit.utils

import kotlin.math.abs

fun pluralTextFrom(count: Int, form2Id: Int, form5Id: Int): Int {
    val n = abs(count) % 100
    val n1 = n % 10
    if (n in 11..19) return form5Id
    if (n1 in 1..4) return form2Id
    return form5Id
}