package d3

import utils.*

fun main() {
    val g = readLongGrid("/d3/d3p1")

    val oxyMatch = findMatch(g, 0, false)
    val co2Match = findMatch(g, 0, true)

    println(oxyMatch * co2Match)

}

fun findMatch(g: List<List<Long>>, matchIdx: Int, invert: Boolean): Long {
    val mostCommonBit = mostCommonBit(g, matchIdx)
    val matchBit = if (invert) Math.abs(mostCommonBit-1) else mostCommonBit

    val newG = g.filter{it.get(matchIdx) == matchBit}

    if (newG.size == 1) {
        return toLong(newG[0])
    } else {
        return findMatch(newG, matchIdx+1, invert)
    }
}

fun mostCommonBit(g: List<List<Long>>, matchIdx: Int): Long {
    var ones = 0;
    for (line in g) {
        if (line[matchIdx] == 1L) ones++
    }

    if (ones*2 >= g.size) {
        return 1
    } else {
        return 0
    }
}

fun toLong(bits: List<Long>): Long {
    var total = 0L
    for (i in 0..bits.size-1) {
        val exp = bits.size - 1 - i
        val amt = bits[i].shl(exp)
        total += amt
    }
    return total
}



