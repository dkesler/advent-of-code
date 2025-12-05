package d3

import utils.readList
import utils.readLongGrid

fun main() {
    val grid = readLongGrid("/d3.txt")

    var sum = 0L;
    for (bank in grid) {
        sum += maxBank(bank)
    }

    println(sum)


}

fun maxBank(bank: List<Long>): Long {
    var max = 0L
    for (i in bank.indices)
        for (j in i+1 until bank.size) {
            val cur = bank[i] * 10L + bank[j]
            if (cur > max) max = cur
        }

    return max
}
