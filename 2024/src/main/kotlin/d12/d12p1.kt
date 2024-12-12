package d12

import utils.*

fun main() {
    val grid = readCharGrid("/d12.txt")

    val plots = Grids.fullFloodFill(
        grid,
        { from, to -> from.value == to.value }
    )

    println(plots.map{ fencingCost(it)}.sum())


}

fun fencingCost(plot: Set<Point>): Long {
    val area = plot.size

    val perimiter = plot.flatMap{ n2(it)}.filter{ !plot.contains(it) }.count()

    return area.toLong() * perimiter
}

fun n2(point: Point): Set<Point> {
    return setOf(
        Point(point.row, point.col+1),
        Point(point.row, point.col-1),
        Point(point.row+1, point.col),
        Point(point.row-1, point.col)
    )
}