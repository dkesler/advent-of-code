package d21

import utils.*
val buttonToPosNum = mapOf(
    Pair('7', Point(0, 0)),
    Pair('8', Point(0, 1)),
    Pair('9', Point(0, 2)),
    Pair('4', Point(1, 0)),
    Pair('5', Point(1, 1)),
    Pair('6', Point(1, 2)),
    Pair('1', Point(2, 0)),
    Pair('2', Point(2, 1)),
    Pair('3', Point(2, 2)),
    Pair('0', Point(3, 1)),
    Pair('A', Point(3, 2))
)
val posToButtonNum = buttonToPosNum.keys.map{Pair(buttonToPosNum[it]!!, it) }.toMap()

val buttonToPosDir = mapOf(
    Pair('^', Point(0, 1)),
    Pair('A', Point(0, 2)),
    Pair('<', Point(1, 0)),
    Pair('>', Point(1, 2)),
    Pair('v', Point(1, 1)),
)
val posToButtonDir = buttonToPosDir.keys.map{Pair(buttonToPosDir[it]!!, it) }.toMap()


fun findPatterns(cur: Char, tar: Char, buttonToPos: Map<Char, Point>, posToButton: Map<Point, Char>): Set<String> {
    if (cur == tar) return setOf("A")
    val curPos = buttonToPos[cur]!!
    val tarPos = buttonToPos[tar]!!
    val patterns = mutableSetOf<String>()
    if (curPos.col > tarPos.col && Point(curPos.row, curPos.col-1) in posToButton.keys) {
        val next = findPatterns(posToButton[Point(curPos.row, curPos.col-1)]!!, tar, buttonToPos, posToButton)
        if (next.isNotEmpty()) patterns.addAll( next.map{ "<" + it }) else patterns.add("<")
    }

    if (curPos.col < tarPos.col && Point(curPos.row, curPos.col+1) in posToButton.keys) {
        val next = findPatterns(posToButton[Point(curPos.row, curPos.col+1)]!!, tar, buttonToPos, posToButton)
        if (next.isNotEmpty()) patterns.addAll( next.map{ ">" + it }) else patterns.add(">")
    }

    if (curPos.row < tarPos.row && Point(curPos.row+1, curPos.col) in posToButton.keys) {
        val next = findPatterns(posToButton[Point(curPos.row+1, curPos.col)]!!, tar, buttonToPos, posToButton)
        if (next.isNotEmpty()) patterns.addAll( next.map{ "v" + it }) else patterns.add("v")
    }

    if (curPos.row > tarPos.row && Point(curPos.row-1, curPos.col) in posToButton.keys) {
        val next = findPatterns(posToButton[Point(curPos.row-1, curPos.col)]!!, tar, buttonToPos, posToButton)
        if (next.isNotEmpty()) patterns.addAll( next.map{ "^" + it }) else patterns.add("^")
    }

    return patterns
}


fun main() {
    val lines = readList("/d21.txt")

    val dirButtons = setOf('>', '<', 'v', '^', 'A')
    val shortestDirMap1 = mutableMapOf<Pair<Char, Char>, String>()
    for (cur in dirButtons) {
        for (tar in dirButtons) {
            val numPatterns = findPatterns(cur, tar, buttonToPosDir, posToButtonDir)
            shortestDirMap1[Pair(cur, tar)] = numPatterns.sortedWith{ a, b -> a.length.compareTo(b.length)}.first()
        }
    }

    val shortestDirMap2 = mutableMapOf<Pair<Char, Char>, String>()
    for (cur in dirButtons) {
        for (tar in dirButtons) {
            val numPatterns = findPatterns(cur, tar, buttonToPosDir, posToButtonDir)
            val shortest = numPatterns.map{ findShortest(it, shortestDirMap1) }.sortedWith{ a, b -> a.length.compareTo(b.length)}.first()
            shortestDirMap2[Pair(cur, tar)] = shortest
        }
    }


    val buttons = setOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A')
    val shortestNumMap = mutableMapOf<Pair<Char, Char>, String>()
    for (cur in buttons) {
        for (tar in buttons) {
            val numPatterns = findPatterns(cur, tar, buttonToPosNum, posToButtonNum)
            val shortest = numPatterns.map{ findShortest(it, shortestDirMap2) }.sortedWith{ a, b -> a.length.compareTo(b.length)}.first()
            shortestNumMap[Pair(cur, tar)] = shortest

        }
    }

    val shortestPatternMap = mutableMapOf<String, String>()
    for (pattern in lines) {
        val shortest = findShortest(pattern, shortestNumMap)
        shortestPatternMap[pattern] = shortest
    }

    println(
    shortestPatternMap.keys.map {
        shortestPatternMap[it]!!.length * it.substring(0, 3).toLong()
    }.sum()
    )




}

fun findShortest(pattern: String, shortestMap: Map<Pair<Char, Char>, String>): String {
    var newPattern = mutableListOf<Char>()
    for (idx in pattern.indices) {
        val cur = if (idx == 0) 'A' else pattern[idx-1]
        val tar = pattern[idx]
        newPattern.addAll(shortestMap[Pair(cur, tar)]!!.toList())
    }
    return String(newPattern.toCharArray())
}
