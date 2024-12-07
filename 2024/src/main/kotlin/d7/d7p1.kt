package d7

import utils.*

data class Equation(val value: Long, val operands: List<Long>)

fun main() {
    val lines = readList("/d7.txt")

    val equations = lines.map{ it.split(":")}.map{ Equation(it[0].toLong(), it[1].split(" ").filter{it.isNotBlank()}.map{ it.trim().toLong()})}

    println(equations.filter{ couldBeTrue(it.value, it.operands[0], it.operands.subList(1, it.operands.size)) }.sumOf{ it.value })
}

fun couldBeTrue(value: Long, currentValue: Long, operands: List<Long>): Boolean {
    if (currentValue == value && operands.isEmpty()) return true
    if (operands.isEmpty()) return false
    if (currentValue > value) return false

    val couldSum = couldBeTrue(value, currentValue + operands[0], operands.subList(1, operands.size))
    if (couldSum) return true

    val couldMul = couldBeTrue(value, currentValue * operands[0], operands.subList(1, operands.size))
    if (couldMul) return true

    //comment out for pt 1
    if (couldBeTrue(value, (currentValue.toString()  + operands[0].toString()).toLong(), operands.subList(1, operands.size))) return true

    return false
}
