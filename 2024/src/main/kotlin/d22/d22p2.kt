package d22

import utils.*

fun main() {
    val lines = readLongList("/d22.txt")

    var secretNumbers = mutableListOf(lines)
    for (iter in 1..2000) {
        val newNumbers = secretNumbers[iter-1].map{evolve(it)}
        secretNumbers.add(newNumbers)
    }

    val diffs = mutableListOf<List<Pair<Long, Long>>>()
    for (monkey in secretNumbers[0].indices) {
        val monkeyVals = (0..2000).map{ secretNumbers[it][monkey] }
        diffs.add(genDiffs(monkeyVals))
    }

    val seqMaps = diffs.map{ genSeqMap(it)}

    var mostBananas = 0L
    for (a in -9..9L) {
        for (b in -9..9L) {
            for (c in -9..9L) {
                for (d in -9..9L) {
                    var bananas = 0L
                    for (monkey in seqMaps) {
                        val seq = Seq(a, b, c, d)
                        bananas += monkey.getOrDefault(seq, 0L)
                    }
                    if (bananas > mostBananas) mostBananas = bananas
                }
            }
        }
    }

    println(mostBananas)

}

data class Seq(val a: Long, val b: Long, val c: Long, val d: Long)
fun genSeqMap(diffs: List<Pair<Long, Long>>): Map<Seq, Long> {
    val map = mutableMapOf<Seq, Long>()
    for (idx in 3 until diffs.size) {
        val seq = Seq( diffs[idx].second, diffs[idx-1].second, diffs[idx-2].second, diffs[idx-3].second)
        if (seq !in map.keys) map[seq] = diffs[idx].first
    }
    return map
}

fun genDiffs(nums: List<Long>): List<Pair<Long, Long>> {
    val diffs = mutableListOf<Pair<Long, Long>>()
    for (i in 1..nums.size-1) {
        diffs.add(Pair(nums[i] % 10, nums[i] % 10 - nums[i-1] % 10))
    }
    return diffs
}
