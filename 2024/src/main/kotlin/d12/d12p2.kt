package d12

import utils.Grids
import utils.Point
import utils.readCharGrid

fun main() {
    val grid = readCharGrid("/d12.txt")

    val plots = Grids.fullFloodFill(
        grid,
        { from, to -> from.value == to.value }
    )

    println(plots.map{ fencingCost2(it)}.sum())


}

fun fencingCost2(plot: Set<Point>): Long {
    val area = plot.size

    val lNeighbors = plot.map{ Point(it.row, it.col-1) }.filter{ !plot.contains(it) }.toSet()
    val lMerged = fff(
        lNeighbors,
        { from, to -> lNeighbors.contains(to) && from.col == to.col }
    )

    val rNeighbors = plot.map{ Point(it.row, it.col+1) }.filter{ !plot.contains(it) }.toSet()
    val rMerged = fff(
        rNeighbors,
        { from, to -> rNeighbors.contains(to) && from.col == to.col }
    )

    val uNeighbors = plot.map{ Point(it.row-1, it.col) }.filter{ !plot.contains(it) }.toSet()
    val uMerged = fff(
        uNeighbors,
        { from, to -> uNeighbors.contains(to) && from.row == to.row }
    )

    val dNeighbors = plot.map{ Point(it.row+1, it.col) }.filter{ !plot.contains(it) }.toSet()
    val dMerged = fff(
        dNeighbors,
        { from, to -> dNeighbors.contains(to) && from.row == to.row }
    )


    return area.toLong() * (lMerged.size + rMerged.size + uMerged.size + dMerged.size)
}

fun ff(start: Point, floodTo: (Point, Point) -> Boolean ): Set<Point> {
    val toVisit = mutableSetOf(start)
    val visited = mutableSetOf<Point>()
    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        visited.add(visiting)
        toVisit.remove(visiting)

        toVisit.addAll(
            n2(visiting)
                .filter { it !in visited }
                .filter { floodTo(
                    Point(visiting.row, visiting.col),
                    Point(it.row, it.col)
                )}
        )
    }
    return visited
}

fun fff(grid: Set<Point>, floodTo: (Point, Point) -> Boolean, fillFrom: (Point) -> Boolean = {_->true}): Set<Set<Point>> {
    val fills = mutableSetOf<Set<Point>>()
    val filled = mutableSetOf<Point>()

    for (point in grid) {
        if (point !in filled && fillFrom(Point(point.row, point.col))) {
            val fill = ff(point, floodTo)
            fills.add(fill)
            filled.addAll(fill)
        }
    }
    return fills
}