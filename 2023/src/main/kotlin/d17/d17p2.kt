package d17

import utils.*
import java.util.PriorityQueue

fun neigh2(grid: List<List<Long>>, cur: Point, dirs: List<Char>): Set<Point> {
    val ns = mutableListOf<Point>()
    if (dirs.last() == '>') {
        if (dirs.size >= 4) {
            ns.add(Point(cur.row + 1, cur.col))
            ns.add(Point(cur.row - 1, cur.col))
        }
        if (dirs.size < 10) ns.add(Point(cur.row, cur.col+1))
    }else if (dirs.last() == '<') {
        if (dirs.size >= 4) {
            ns.add(Point(cur.row + 1, cur.col))
            ns.add(Point(cur.row - 1, cur.col))
        }
        if (dirs.size < 10) ns.add(Point(cur.row, cur.col-1))
    } else if (dirs.last() == '^') {
        if (dirs.size >= 4) {
            ns.add(Point(cur.row, cur.col - 1))
            ns.add(Point(cur.row, cur.col + 1))
        }
        if (dirs.size < 10) ns.add(Point(cur.row-1, cur.col))
    } else {
        if (dirs.size >= 4) {
            ns.add(Point(cur.row, cur.col - 1))
            ns.add(Point(cur.row, cur.col + 1))
        }
        if (dirs.size < 10) ns.add(Point(cur.row+1, cur.col))
    }
    return ns.filter{ it.row >= 0 && it.row < grid.size && it.col >= 0 && it.col < grid[0].size }.toSet()

}


fun main() {
    val grid = readLongGrid("/d17.txt")
    val target = Point(grid.size - 1, grid[0].size - 1)

    val toVisit = PriorityQueue<Path> { a, b ->
        val hl = a.heatLoss.compareTo(b.heatLoss)
        if (hl != 0) hl
        else
            a.cur.manhattanDistance(target).compareTo(b.cur.manhattanDistance(target))
    }
    toVisit.add(
            Path(Point(0, 0), listOf('V'), 0)
    )
    toVisit.add(
            Path(Point(0, 0), listOf('>'), 0)
    )

    val bestPossible = mutableMapOf<Path, Long>()

    var bestPath: Path? = null
    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.poll()

        val pathKey = Path(visiting.cur, visiting.dirs, 0)
        if (!bestPossible.keys.contains(pathKey) || bestPossible[pathKey]!! > visiting.heatLoss) {
            bestPossible[pathKey] = visiting.heatLoss

            if (visiting.cur == target && visiting.dirs.size >= 4) {
                if (bestPath == null || bestPath.heatLoss > visiting.heatLoss)
                    bestPath = visiting
            } else {
                val neighbors = neigh2(grid, visiting.cur, visiting.dirs)
                for (n in neighbors) {
                    val dir = getDirs(n, visiting)
                    val next = Path(
                            n,
                            dir,
                            visiting.heatLoss + grid[n.row][n.col]
                    )
                    if ((bestPath == null || next.heatLoss < bestPath.heatLoss)) {
                        toVisit.add(next)
                    }
                }
            }
        }
    }

    println(bestPath!!.heatLoss)


}


