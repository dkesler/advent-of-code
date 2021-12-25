package d25

import utils.*

fun main() {
    val originalGrid = readCharGrid("/d25/d25p1")
    var g = originalGrid.mapIndexed{ rowIdx, row ->
        row.mapIndexed { colIdx, c ->
            Pair(Pair(rowIdx, colIdx), c)
        }
    }.flatten().filter{it.second != '.'}.toMap()


    var newG = tick(g, originalGrid.size, originalGrid[0].size)
    var ticks = 1
    while(g != newG) {
        g = newG
        newG = tick(newG,  originalGrid.size, originalGrid[0].size)

        ticks++
    }

    println(ticks)
}


fun tick(g: Map<Pair<Int, Int>, Char>, rows: Int, cols: Int): Map<Pair<Int, Int>, Char> {
    val gAfterEastmove = g.filter{it.value == 'v'}.toMutableMap()
    val east = g.filter{it.value == '>'}
    for (e in east) {
        val newCol = (e.key.second + 1) % cols
        if (!g.containsKey(Pair(e.key.first, newCol))) {
            gAfterEastmove.put(Pair(e.key.first, newCol), '>')
        } else {
            gAfterEastmove.put(e.key, '>')
        }
    }

    val gAfterSouthmove = gAfterEastmove.filter{it.value == '>'}.toMutableMap()
    val south = gAfterEastmove.filter{it.value == 'v'}
    for (e in south) {
        val newRow = (e.key.first + 1) % rows
        if (!gAfterEastmove.containsKey(Pair(newRow, e.key.second))) {
            gAfterSouthmove.put(Pair(newRow, e.key.second), 'v')
        } else {
            gAfterSouthmove.put(e.key, 'v')
        }
    }

    return gAfterSouthmove
}
