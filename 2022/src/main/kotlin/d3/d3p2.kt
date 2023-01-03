package d3

import utils.*

fun findCommon(bags: List<List<Char>>): Char {
    return bags.map{it.toSet()}.reduce{ x, y -> x.intersect(y) }.first()
}

fun main() {
    val lines = readCharGrid("/d3.txt")

    val sum = lines.chunked(3)
            .map { findCommon(it) }
            .sumOf {
                if (it.isUpperCase())
                    it.toInt() - 'A'.toInt() + 27
                else
                    it.toInt() - 'a'.toInt() + 1
            }

    println(sum)



}