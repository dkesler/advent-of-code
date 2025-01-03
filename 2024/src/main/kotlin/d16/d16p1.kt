package d16

import utils.*

fun getNext(cur: Pair<Point, String>): Pair<Point, String> {
    return if (cur.second == "E") {
        Pair(Point(cur.first.row, cur.first.col+1), "E")
    } else if (cur.second == "N") {
        Pair(Point(cur.first.row -1, cur.first.col), "N")
    } else if (cur.second == "S") {
        Pair(Point(cur.first.row + 1, cur.first.col), "S")
    } else {
        Pair(Point(cur.first.row, cur.first.col-1), "W")
    }
}

fun getRotations(cur: Pair<Point, String>): Set<Pair<Point, String>> {
    return if (cur.second == "E") {
        setOf( Pair(cur.first, "N"), Pair(cur.first, "S"))
    } else if (cur.second == "W") {
        setOf( Pair(cur.first, "N"), Pair(cur.first, "S"))
    } else if (cur.second == "N") {
        setOf( Pair(cur.first, "E"), Pair(cur.first, "W"))
    } else {
        setOf( Pair(cur.first, "E"), Pair(cur.first, "W"))
    }
}

fun main() {
    val grid = readCharGrid("/d16.txt")

    val start = Grids.pointValues(grid).filter{ it.value == 'S' }.first().toPoint()
    val end  = Grids.pointValues(grid).filter{ it.value == 'E' }.first().toPoint()


    val path = Grids.aStar(
        setOf(Pair(start, "E")),
        { it == Pair(end, "E")},
        { cur: Pair<Point, String> ->
            val next = getNext(cur)
            val rotated = getRotations(cur)
            if (grid[next.first.row][next.first.col] != '#')
                rotated + next
            else
                rotated
        },
        {cur: Pair<Point, String>, next: Pair<Point, String> -> if (cur.second == next.second) 1 else 1000 },
        { 0 }
    )

    println(path.cost)


}
