package d2

import utils.readList

fun main() {
    val lines = readList("/d2.txt")
    val ranges = lines[0].split(",").map{  range -> range.split("-").map{ it.toLong() } }.map{ Pair(it[0], it[1]) }

    var invalidSum = 0L
    for (range in ranges) {
        for (i in range.first..range.second) {

            for (len in 1..i.toString().length/2) {
                val subStr = i.toString().substring(0,len)
                val times = i.toString().length / len
                var str = ""
                for (j in 1..times) str += subStr

                if (str == i.toString()) {
                    invalidSum += i
                    break
                }

            }
        }
    }

    println(invalidSum)

}