package d18

import utils.*

fun toDir(c: Char): String {
    if (c == '0') return "R"
    if (c == '1') return "D"
    if (c == '2') return "L"
    return "U"
}

data class Box(val rowRange: IntRange, val colRange: IntRange) {

    fun contains(pt: Point): Boolean {
        return pt.row in rowRange && pt.col in colRange
    }

    fun size(): Long {
        return (rowRange.last.toLong() - rowRange.first + 1) * (colRange.last.toLong() - colRange.first + 1)
    }

    fun intersects(other: Box): Boolean {
        return (rowRange.first in other.rowRange || rowRange.last in other.rowRange || other.rowRange.first in rowRange || other.rowRange.last in rowRange) &&
                (colRange.first in other.colRange || colRange.last in other.colRange || other.colRange.first in colRange || other.colRange.last in colRange)
    }
}

fun main() {
    val lines = readList("/d18.txt")
    val digs = lines.map {
        val s = it.split(" ")
        //Pair(s[0], s[1].toInt())
        Pair(toDir(s[2][7]), s[2].substring(2,7).toInt(16))
    }

    var cur = Point(0, 0)
    val boxes = mutableSetOf<Box>()

    for (dig in digs) {
        if (dig.first == "U") {
            val next = Point(cur.row - dig.second, cur.col)
            boxes.add(Box((next.row+1..cur.row), (cur.col..cur.col)))
            cur = next
        }
        if (dig.first == "D") {
            val next = Point(cur.row + dig.second, cur.col)
            boxes.add(Box((cur.row..next.row-1), (cur.col..cur.col)))
            cur = next
        }
        if (dig.first == "L") {
            val next = Point(cur.row, cur.col - dig.second)
            boxes.add(Box((cur.row..cur.row), (next.col+1..cur.col)))
            cur = next

        }
        if (dig.first == "R") {
            val next = Point(cur.row, cur.col + dig.second)
            boxes.add(Box((cur.row..cur.row), (cur.col..next.col-1)))
            cur = next
        }
    }

    val minRow = boxes.minOf{it.rowRange.first}
    val minCol = boxes.minOf{it.colRange.first}
    val maxRow = boxes.maxOf{it.rowRange.last}
    val maxCol = boxes.maxOf{it.colRange.last}

    val boxesNormalized = boxes.map{Box( (it.rowRange.first-minRow..it.rowRange.last-minRow), (it.colRange.first-minCol..it.colRange.last-minCol))}.toMutableList()
    val trench = boxesNormalized.toSet()
    val rows = maxRow - minRow + 1
    val cols = maxCol - minCol + 1

    val maxNormalizedRow = boxesNormalized.maxOf{it.rowRange.last}
    val maxNormalizedCol = boxesNormalized.maxOf{it.colRange.last}


    var row = 0
    var col = 0
    while (row < rows) {
        val boxesInRow = boxesNormalized.filter{ row in it.rowRange }.sortedBy{it.colRange.first}
        if (boxesInRow.map{ it.colRange.last - it.colRange.first + 1 }.sum() == cols) {
            row = boxesInRow.minOf{ it.rowRange.last}
        }

        while (col < cols) {
            val pt = Point(row, col)
            val inBox = boxesNormalized.find{ it.contains(pt)}
            if (inBox != null) {

                col = inBox.colRange.last+1
            } else {
                val boxesInRowToRight = boxesNormalized.filter{ row in it.rowRange && it.colRange.first > col }
                val maxWidth = boxesInRowToRight.minOfOrNull{ it.colRange.first - 1 } ?: maxNormalizedCol
                val colSpan = (pt.col..maxWidth)
                val boxesInColsBelow = boxesNormalized.filter {
                    it.rowRange.first > row &&
                    (it.colRange.first in colSpan || it.colRange.last in colSpan || colSpan.first in it.colRange || colSpan.last in it.colRange)
                }
                val maxHeight = boxesInColsBelow.minOfOrNull{ it.rowRange.first - 1} ?: maxNormalizedRow

                boxesNormalized.add(Box(pt.row..maxHeight, colSpan))
                col = maxWidth+1
            }
        }
        col = 0
        row += 1
    }


    val mergePairs = mutableSetOf<Pair<Box, Box>>()

    for (b1 in boxesNormalized.indices) {
        for (b2 in (b1+1 until boxesNormalized.size)) {
            val box1 = boxesNormalized[b1]
            val box2 = boxesNormalized[b2]
            if (box1 !in trench && box2 !in trench) {
                val expandedBox1 = Box(box1.rowRange.first - 1..box1.rowRange.last + 1, (box1.colRange.first - 1..box1.colRange.last + 1))
                if ( box2.intersects(expandedBox1)) {
                    mergePairs.add(Pair(box1, box2))
                }
            }
        }
    }

    val toKill = mutableSetOf<Box>()
    for (box in boxesNormalized) {
        if (box !in trench) {
            var added = false
            var toMerge = setOf(box)
            do {
                added = false
                val next = mergePairs.filter { it.first in toMerge || it.second in toMerge }.flatMap { listOf(it.first, it.second) }.toSet()
                if (next != toMerge) added = true
                toMerge = next
            } while (added)

            if (toMerge.any { it.rowRange.first == 0 || it.rowRange.last == maxNormalizedRow || it.colRange.first == 0 || it.colRange.last == maxNormalizedCol }) {
                toKill.add(box)
            }
        }
    }

    boxesNormalized.removeIf{ it in toKill}

    println(
            boxesNormalized.sumOf{ it.size() }
    )
}
