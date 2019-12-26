package d24

import java.io.File
import java.math.BigInteger

fun main() {
    var cells = File("src/main/resources/d24/d24p1").readLines()
        .mapIndexed{ y, line -> line.split("").filter{it.isNotEmpty()}.mapIndexed{ x, c -> toBugCell(x, y, c) } }

    val previousLayouts = mutableSetOf<List<List<BugCell>>>()

    while(!previousLayouts.contains(cells)) {
        previousLayouts.add(cells)
        val newCells = cells.map{ it.map{ it.tick(cells) } }
        cells = newCells
    }

    println(cells)

    val biodiversity = cells.flatten().mapIndexed{ i, cell -> if (cell.infested) BigInteger.valueOf(2L).pow(i).toInt() else 0  }
        .sum()

    println(biodiversity)
}

fun toBugCell(x: Int, y: Int, cell: String):BugCell {
    if (cell == "#") {
        return BugCell(x, y,true)
    } else {
        return BugCell(x, y,false)
    }
}

data class BugCell(val x: Int, val y: Int, val infested: Boolean) {
    override fun toString(): String {
        if (infested) return "#" else return "."
    }

    fun tick(cells: List<List<BugCell>>): BugCell {
        var adjacentBugs = 0
        if (x > 0 && cells[y][x-1].infested) {
            adjacentBugs++
        }

        if (x+1 < cells[y].size && cells[y][x+1].infested) {
            adjacentBugs++
        }

        if (y > 0 && cells[y-1][x].infested) {
            adjacentBugs++
        }

        if (y+1 < cells.size && cells[y+1][x].infested) {
            adjacentBugs++
        }

        if (infested) {
            if (adjacentBugs == 1) {
                return BugCell(x, y, true)
            } else {
                return BugCell(x, y, false)
            }
        } else {
            if (adjacentBugs == 1 || adjacentBugs == 2) {
                return BugCell(x, y, true)
            } else {
                return BugCell(x, y, false)
            }
        }
    }


}
