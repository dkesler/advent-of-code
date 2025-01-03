package d18

import d12.n2
import utils.*

fun main() {
    val lines = readList("/d18.txt").map{ it.split(",").map{it.toInt()}}

    val corrupted = mutableSetOf<Point>()

    for (i in 0..1023) {
        corrupted.add(Point(lines[i][1], lines[i][0]))
    }

    val path = Grids.aStar(
        setOf(Point(0, 0)),
        {it == Point(70, 70) },
        { n2(it).filter{ it !in corrupted }.filter{it.row >= 0 && it.row <= 70 && it.col >= 0 && it.col <= 70}.toSet() },
        { cur, next -> 1 },
        { 0 }
    )

    println(path.cost)


}
