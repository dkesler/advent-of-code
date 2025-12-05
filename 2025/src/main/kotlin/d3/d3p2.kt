package d3

import utils.readList
import utils.readLongGrid
import kotlin.math.max

fun main() {
    val grid = readLongGrid("/d3.txt")

    var sum = 0L
    for (bank in grid) {
        sum += maxBank2(bank, 12)
    }

    println(sum)
}

val memoTable = HashMap<Pair<List<Long>, Int>, Long>()

fun maxBank2(bank: List<Long>, batteries: Int): Long {
    if (batteries == 0 || bank.isEmpty()) return 0

    val memoKey = Pair(bank, batteries)
    if (memoKey in memoTable.keys) return memoTable[memoKey]!!

    val turnOnTail = bank.last() + 10 * maxBank2(bank.subList(0, bank.size-1), batteries-1)
    val dontTurnOnTail = maxBank2(bank.subList(0, bank.size-1), batteries)
    val thisMax = max(turnOnTail, dontTurnOnTail)

    memoTable[memoKey] = thisMax

    return  thisMax
}
