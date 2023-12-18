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

    fun canExpandLeft(boxes: MutableSet<Box>): Boolean {
        if (colRange.first-1 < 0) return false
        val newBox = Box(rowRange, (colRange.first-1..colRange.last))
        return boxes.none{ newBox.intersects(it) }
    }

    fun canExpandRight(boxes: MutableSet<Box>, maxCol: Int): Boolean {
        if (colRange.last+1 > maxCol) return false
        val newBox = Box(rowRange, (colRange.first..colRange.last+1))
        return boxes.none{ newBox.intersects(it) }
    }

    fun canExpandUp(boxes: MutableSet<Box>): Boolean {
        if (rowRange.first-1 < 0) return false
        val newBox = Box( (rowRange.first-1..rowRange.last), colRange)
        return boxes.none{ newBox.intersects(it) }
    }

    fun canExpandDown(boxes: MutableSet<Box>, maxRow: Int): Boolean {
        if (rowRange.last+1 > maxRow) return false
        val newBox = Box( (rowRange.first..rowRange.last+1), colRange)
        return boxes.none{ newBox.intersects(it) }
    }
}

fun main() {
    val lines = readList("/d18test.txt")
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

    val boxesNormalized = boxes.map{Box( (it.rowRange.first-minRow..it.rowRange.last-minRow), (it.colRange.first-minCol..it.colRange.last-minCol))}.toMutableSet()
    val trench = boxesNormalized.toSet()
    val rows = maxRow - minRow + 1
    val cols = maxCol - minCol + 1

    val maxNormalizedRow = boxesNormalized.maxOf{it.rowRange.last}
    val maxNormalizedCol = boxesNormalized.maxOf{it.colRange.last}


    var row = 0
    var col = 0
    while (row < rows) {
        while (col < cols) {
            val pt = Point(row, col)
            val inBox = boxesNormalized.find{ it.contains(pt)}
            if (inBox != null) {
                col = inBox.colRange.last+1
            } else {
                var box = Box( (pt.row..pt.row), (pt.col..pt.col))
                while(box.canExpandRight(boxesNormalized, maxNormalizedCol)) box = Box(box.rowRange, (box.colRange.first..box.colRange.last+1))
                while(box.canExpandDown(boxesNormalized, maxNormalizedRow)) box = Box( (box.rowRange.first..box.rowRange.last+1), box.colRange)
                boxesNormalized.add(box)
                col = box.colRange.last + 1
            }
        }
        col = 0
        row += 1
    }

    boxesNormalized.removeIf{ it !in trench &&
            (it.rowRange.first == 0 || it.rowRange.last == maxNormalizedRow || it.colRange.first == 0 || it.colRange.last == maxNormalizedCol)
    }

/*    for (row in (0..maxNormalizedRow)) {
        for (col in 0..maxNormalizedCol) {
            val pt = Point(row, col)
            if (trench.any{ it.contains(pt)}) print('$')
            else if ( boxesNormalized.any{it.contains(pt)}) print('#')
            else print('.')
        }
        println()
    }*/

    println(
            boxesNormalized.sumOf{ it.size() }
    )
}
