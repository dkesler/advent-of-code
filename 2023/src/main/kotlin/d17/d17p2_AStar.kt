package d17

import utils.*
import java.util.PriorityQueue




fun main() {
    val grid = readLongGrid("/d17.txt")
    val target = Point(grid.size - 1, grid[0].size - 1)

    data class Node(val cur: Point, val dirs: List<Char>)
    fun getDirs2(n: Point, visiting: Node): List<Char> {
        return if (n.row > visiting.cur.row) {
            if (visiting.dirs.last() == 'V') visiting.dirs + 'V' else listOf('V')
        } else if (n.row < visiting.cur.row) {
            if (visiting.dirs.last() == '^') visiting.dirs + '^' else listOf('^')
        }else if (n.col > visiting.cur.col) {
            if (visiting.dirs.last() == '>') visiting.dirs + '>' else listOf('>')
        } else {
            if (visiting.dirs.last() == '<') visiting.dirs + '<' else listOf('<')
        }
    }


    val search = Grids.aStar<Node>(
            setOf(Node(Point(0, 0), listOf('V')), Node(Point(0, 0), listOf('>'))),
            { node: Node -> node.cur == target && node.dirs.size >= 4},
            { node: Node ->
                val neighbors = neigh2(grid, node.cur, node.dirs)
                neighbors.map { n ->
                    val dirs = getDirs2(n, node)
                    Node(n, dirs)
                }.toSet()
            },
            { _: Node, next: Node -> grid[next.cur.row][next.cur.col] },
            { node: Node -> node.cur.manhattanDistance(target).toLong() }
    )

    println(search.cost)




}


