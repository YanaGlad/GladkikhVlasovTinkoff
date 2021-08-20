package com.example.gladkikhvlasovtinkoff.extension

fun String.convertToStyled() : String {
    val firstIndex = this.indexOfFirst{ char ->
        char == ','
    }
    val firstComma = if(firstIndex != -1) firstIndex
    else this.length - 1
    var digitInLastGroup = 0
    return buildString {
        for(i in firstComma - 1 downTo 0 ) {
            if (digitInLastGroup < 3) {
                digitInLastGroup++
                append(this@convertToStyled[i])
            }else{
                digitInLastGroup = 1
                append(" ")
                append(this@convertToStyled[i])
            }
        }
    }.reversed() + "," + this.substring(firstComma + 1)
}

fun String.convertFromStyled() : String =
    buildString {
        var containDot = false
        for(char in this@convertFromStyled){
            if(char != ' ') {
                if(char == '.' || char == ',' && !containDot) {
                    containDot = true
                    append(',')
                }
                else {
                    append(char)
                }
            }
        }
    }