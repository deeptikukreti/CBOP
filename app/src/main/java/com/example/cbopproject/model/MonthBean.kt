package com.example.cbopproject.model

data class MonthBean (
    var monthName:String,
    var year:Int,
    var position:Int,
    var isSelected:Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MonthBean

        if (monthName != other.monthName) return false
        if (year != other.year) return false
        if (position != other.position) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = monthName.hashCode()
        result = 31 * result + year
        result = 31 * result + position
        result = 31 * result + isSelected.hashCode()
        return result
    }
}