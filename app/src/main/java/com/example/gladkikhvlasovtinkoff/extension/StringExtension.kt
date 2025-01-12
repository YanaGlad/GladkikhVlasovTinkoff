package com.example.gladkikhvlasovtinkoff.extension

import android.content.Context
import com.example.gladkikhvlasovtinkoff.R

private const val DIGITS_BEFORE_COMMA = 12
private const val DIGITS_AFTER_COMMA = 2

fun String.convertToStyled(): String {
    val firstCommaIndex = this.getNumOfDigitsBeforeComma()
    val integerPart = this.getNumBeforeComma(firstCommaIndex)
    return integerPart.addDigitsAfterComma(firstCommaIndex, this)
}

private fun String.addDigitsAfterComma(commaIndex: Int, initialStr: String): String =
    if (commaIndex < initialStr.length) {
        buildString {
            append(this@addDigitsAfterComma)
            append(",")
            append(initialStr.substring(commaIndex + 1))
        }
    } else this

private fun String.getNumBeforeComma(commaIndex: Int): String =
    buildString {
        var digitInLastGroup = 0
        for (i in commaIndex - 1 downTo 0) {
            if (digitInLastGroup < 3) {
                digitInLastGroup++
                append(this@getNumBeforeComma[i])
            } else {
                digitInLastGroup = 1
                append(" ")
                append(this@getNumBeforeComma[i])
            }
        }
    }.reversed()

private fun String.getNumOfDigitsBeforeComma(): Int {
    val first = this.indexOfFirst { char ->
        char == ','
    }
    return if (first != -1) first
    else this.length
}

fun String.convertFromStyled(): String =
    buildString {
        var containDot = false
        var currentBeforeComma = 0
        var currentAfterComma = 0
        for (char in this@convertFromStyled) {
            if (char != ' ') {
                if (char == '.' || char == ',') {
                    if (!containDot) {
                        containDot = true
                        append(',')
                    }
                } else if (!containDot && currentBeforeComma <= DIGITS_BEFORE_COMMA) {
                    if (this.length == 1 && this[0] == '0') {
                        if (char != '0')
                            this[0] = char
                    } else {
                        append(char)
                        currentBeforeComma++
                    }
                } else if (containDot && currentAfterComma < DIGITS_AFTER_COMMA) {
                    append(char)
                    currentAfterComma++
                }
            }
        }
    }

fun String.styleInput() = this.convertFromStyled().convertToStyled()

fun Boolean.getTransactionTypeString(context: Context) =
    if (this) context.getString(R.string.income_text)
    else context.getString(R.string.costs_text)

fun String.convertCurrencyCodeToSymbol(): String =
    when (this) {
        "EUR" -> "€"
        "RUB" -> "₽"
        "USD" -> "$"
        "UAH" -> "₴"
        "GBP" -> "£"
        else -> this
    }

fun String.trimTrailingZeros(): String {
    val res = this.removeZeros()
    for (i in res.length - 1 downTo 0)
        if (res[i] == ',')
            return res.substring(0, i + 2)
    return res
}

fun String.removeZeros(): String {
    val res = this
    for (i in this.length - 1 downTo 0) {
        if (this[i] != '0') {
            return this.substring(0, i + 1)
        } else if (this[i] == ',')
            return this.substring(0, i)
    }
    return res
}
