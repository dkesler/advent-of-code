package d21

import utils.*

val numMemoTable = mutableMapOf<Pair<Char, Char>, Long>()
val dirMemoTable = mutableMapOf<Triple<Int, Char, Char>, Long>()

fun shortestFromNum(cur: Char, tar: Char): Long {
    val memoKey = Pair(cur, tar)
    if (memoKey in numMemoTable) return numMemoTable[memoKey]!!

    val patterns = findPatterns(cur, tar, buttonToPosNum, posToButtonNum)

    val shortest = patterns.map { p ->
        var len = 0L
        for (idx in p.indices) {
            val curD = if (idx == 0) 'A' else p[idx-1]
            val tarD = p[idx]
            len += shortestFromDir(curD, tarD, 24)
        }
        len
    }.sorted().first()

    numMemoTable[memoKey] = shortest
    return shortest
}

fun shortestFromDir(cur: Char, tar: Char, level: Int): Long  {
    val memoKey = Triple(level, cur, tar)
    if (memoKey in dirMemoTable) return dirMemoTable[memoKey]!!

    val patterns = findPatterns(cur, tar, buttonToPosDir, posToButtonDir)

    val shortest = patterns.map { p ->
        if (level == 0) {
            p.length.toLong()
        } else {
            var len = 0L
            for (idx in p.indices) {
                val curD = if (idx == 0) 'A' else p[idx - 1]
                val tarD = p[idx]
                len += shortestFromDir(curD, tarD, level - 1)
            }
            len
        }
    }.sorted().first()

    dirMemoTable[memoKey] = shortest
    return shortest
}

fun main() {
    val lines = readList("/d21.txt")

    var complexity = 0L
    for (pattern in lines) {
        var len = 0L
        for (idx in pattern.indices) {
            val cur = if (idx == 0) 'A' else pattern[idx - 1]
            val tar = pattern[idx]
            len += shortestFromNum(cur, tar)
        }
        complexity += len * pattern.substring(0, 3).toLong()
    }

    println(
        complexity
    )

}
