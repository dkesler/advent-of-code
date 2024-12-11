package d11

import utils.*

val memoTable = mutableMapOf<Pair<Long, Long>, Long>()

fun stoneCount(stoneVal: Long, blinks: Long): Long {
    if (blinks == 0L) return 1

    if (memoTable.containsKey(Pair(stoneVal, blinks))) {
        return memoTable[Pair(stoneVal, blinks)]!!
    }

    val count = if (stoneVal == 0L) {
        stoneCount(1, blinks-1)
    } else if (stoneVal.toString().length % 2 == 0) {
        val stoneString = stoneVal.toString()
        val l = stoneString.substring(0, stoneString.length/2)
        val r = stoneString.substring(stoneString.length/2, stoneString.length)
        stoneCount(l.toLong(), blinks-1) + stoneCount(r.toLong(), blinks-1)
    } else stoneCount(stoneVal * 2024, blinks-1)

    memoTable[Pair(stoneVal, blinks)] = count
    return count
}

fun main() {
    var stones = readList("/d11.txt")[0].split(" ").filter{ it.isNotBlank()}.map{ it.toLong() }

    println(stones.map{ stoneCount(it, 75) }.sum())
}