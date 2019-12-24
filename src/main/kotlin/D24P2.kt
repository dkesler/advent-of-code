import java.io.File
import java.lang.RuntimeException

fun main() {
    var cells = File("src/main/resources/d24p1").readLines()
        .mapIndexed{ y, line -> line.split("").filter{it.isNotEmpty()}.mapIndexed{ x, c -> toBugCell2(x, y, c) } }


    val level0 = BugGrid(cells, 0)

    var bugGrids = mutableMapOf(
        Pair(0, level0),
        Pair(1, BugGrid(emptyCells(), 1)),
        Pair(-1, BugGrid(emptyCells(), -1))
    )

    bugGrids.getValue(-1).print(bugGrids)

    for (minute in 1..200) {
        val newGrids = bugGrids.mapValues { it.value.tick(bugGrids) }
        bugGrids = newGrids.toMutableMap()
        val lowestLevel = bugGrids.keys.min()!!
        if (bugGrids.getValue(lowestLevel).infested()) {
            bugGrids.put(lowestLevel - 1, BugGrid(emptyCells(), lowestLevel-1))
        }

        val highestLevel = bugGrids.keys.max()!!
        if (bugGrids.getValue(highestLevel).infested()) {
            bugGrids.put(highestLevel + 1, BugGrid(emptyCells(), highestLevel+1))
        }
    }

    bugGrids.getValue(bugGrids.keys.min()!!).print(bugGrids)

    println(bugGrids.map { it.value.totalInfested() }.sum())
}

fun toBugCell2(x: Int, y: Int, c: String): BugCell2 {
    if (c == "#") {
        return BugCell2(x, y,true)
    } else {
        return BugCell2(x, y,false)
    }
}

private fun emptyCells(): List<List<BugCell2>> {
    return (0..4).map{ y -> (0..4).map { x -> BugCell2(x, y, false) } }
}


data class BugGrid(val cells: List<List<BugCell2>>, val level: Int) {
    fun tick(bugGrids: Map<Int, BugGrid>): BugGrid {
        val newCells = cells.map{ it.map{ it.tick(this, bugGrids) } }

        return BugGrid(
            newCells,
            level
        )
    }

    fun inner(bugGrids: Map<Int, BugGrid>): BugGrid? {
        return bugGrids.get(level-1)
    }

    fun outer(bugGrids: Map<Int, BugGrid>): BugGrid? {
        return bugGrids.get(level+1)
    }

    fun infestedNorth(): Int {
        return cells[0].map{ if(it.infested) 1 else 0 }.sum()
    }

    fun infestedSouth(): Int {
        return cells[4].map{ if(it.infested) 1 else 0 }.sum()
    }

    fun infestedEast(): Int {
        return (0..4).map{ if (cells[it][4].infested) 1 else 0}.sum()
    }

    fun infestedWest(): Int {
        return (0..4).map{ if (cells[it][0].infested) 1 else 0}.sum()
    }

    fun infested(): Boolean {
        return cells.flatten().any { it.infested }
    }

    fun totalInfested(): Int {
        return cells.flatten().map{ if (it.infested) 1 else 0 }.sum()
    }

    fun print(bugGrids: Map<Int, BugGrid>) {
        println("Level: $level")
        cells.forEach { it.forEach { print(it.toString()) }; println("") }
        println("")

        outer(bugGrids)?.print(bugGrids)
    }
}

data class BugCell2(val x: Int, val y: Int, val infested: Boolean) {
    override fun toString(): String {
        if (x == 2 && y == 2)
            return "?"
        if (infested)
            return "#"
        return "."
    }

    fun tick(myGrid: BugGrid, bugGrids: Map<Int, BugGrid>): BugCell2 {
        if (x == 2 && y == 2) {
            return this
        }

        var adjacentBugs = 0
        adjacentBugs += bugCountAt(x-1, y, myGrid, bugGrids)
        adjacentBugs += bugCountAt(x+1, y, myGrid, bugGrids)
        adjacentBugs += bugCountAt(x, y-1, myGrid, bugGrids)
        adjacentBugs += bugCountAt(x, y+1, myGrid, bugGrids)

        if (infested) {
            if (adjacentBugs == 1) {
                return BugCell2(x, y, true)
            } else {
                return BugCell2(x, y, false)
            }
        } else {
            if (adjacentBugs == 1 || adjacentBugs == 2) {
                return BugCell2(x, y, true)
            } else {
                return BugCell2(x, y, false)
            }
        }
    }

    fun bugCountAt(targetX: Int, targetY: Int, myGrid: BugGrid, bugGrids: Map<Int, BugGrid>): Int {
        if (targetX == 2 && targetY == 2) {
            return getInfestedInner(myGrid, bugGrids)
        }

        if (targetX == -1 || targetX == 5 || targetY == -1 || targetY == 5) {
            var adjInfested = false
            if (targetX == -1 || targetX == 5) {
                adjInfested = adjInfested || myGrid.outer(bugGrids)?.cells?.get(2)?.get(normalizeToOuter(targetX))?.infested ?: false
            }
            if (targetY == -1 || targetY == 5) {
                adjInfested = adjInfested || myGrid.outer(bugGrids)?.cells?.get(normalizeToOuter(targetY))?.get(2)?.infested ?: false
            }

            if (adjInfested) {
                return 1
            } else {
                return 0
            }
        }

        if (myGrid.cells[targetY][targetX].infested) {
            return 1
        } else {
            return 0
        }
    }

    private fun getInfestedInner(myGrid: BugGrid, bugGrids: Map<Int, BugGrid>): Int {
        if (x == 2 && y == 1) {
            return myGrid.inner(bugGrids)?.infestedNorth() ?: 0
        }

        if (x == 2 && y == 3) {
            return myGrid.inner(bugGrids)?.infestedSouth() ?: 0
        }

        if (x == 1 && y == 2) {
            return myGrid.inner(bugGrids)?.infestedWest() ?: 0
        }

        if (x == 3 && y == 2) {
            return myGrid.inner(bugGrids)?.infestedEast() ?: 0
        }

        throw RuntimeException("Tried to get infested inner with cell at ($x, $y)")
    }

    private fun normalizeToOuter(i: Int): Int {
        if (i < 0) {
            return i + 2
        }

        if (i == 5) {
            return i - 2
        }

        return i
    }
}

