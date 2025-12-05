package d5

import utils.readBlocks
import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = readBlocks("/d5.txt")

    var ranges = lines[0].map{
        val split = it.split("-")
        Pair(split[0].toLong(), split[1].toLong() )
    }

    var unioned = true
    while(unioned) {
        unioned = false
        val newRanges = mutableSetOf<Pair<Long, Long>>()

        for (rangeIdx in ranges.indices) {
            for (rangeIdx2 in rangeIdx+1 until ranges.size) {
                val range1 = ranges[rangeIdx]
                val range2 = ranges[rangeIdx2]
                if (range1.first in (range2.first..range2.second) || range1.second in (range2.first..range2.second) || range2.first in (range1.first..range1.second) || range2.second in (range1.first..range1.second)) {
                    val unionedRange = Pair( min(range1.first, range2.first), max(range1.second, range2.second))
                    newRanges.addAll(ranges.filter{ it != range1 && it != range2} )
                    newRanges.add(unionedRange)
                    unioned = true
                    break
                }
            }
            if (unioned) break
        }
        if (unioned)
        ranges = newRanges.toList()
    }


    println(
        ranges.map {
            it.second - it.first + 1L
        }.sum()
    )


}
