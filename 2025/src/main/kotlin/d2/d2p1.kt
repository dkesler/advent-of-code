package d2

import utils.readList

fun main() {
    val lines = readList("/d2.txt")
    val ranges = lines[0].split(",").map{  range -> range.split("-").map{ it.toLong() } }.map{ Pair(it[0], it[1]) }

    var invalidSum = 0L
    for (range in ranges) {
        for (i in range.first..range.second) {
            if (i.toString().length % 2 == 0) {
                val rangeStr = i.toString()
                val first = rangeStr.substring(0, rangeStr.length/2)
                val second = rangeStr.substring(rangeStr.length/2)
                if (first == second) invalidSum += i
            }
        }
    }

    println(invalidSum)

}