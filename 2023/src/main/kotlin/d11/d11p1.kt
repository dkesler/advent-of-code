package d11

import utils.*

fun main() {
    val grid = readCharGrid("/d11.txt")
    val rowsWithoutGalaxies = grid.indices.filter{ grid[it].none{it == '#'}}
    val colsWithoutGalaxies = grid[0].indices.filter{ grid.map{g -> g[it]}.none{it == '#'}}

    val galaxies = grid.indices.flatMap{ row ->
        grid[row].indices.map{ Pair(row, it)}
    }.filter{grid[it.first][it.second] == '#'}

    var dist = 0L

    for (g1 in galaxies.indices) {
        for (g2 in (g1 + 1 until galaxies.size)) {
            val rowRange = if (galaxies[g1].first < galaxies[g2].first) (galaxies[g1].first..galaxies[g2].first) else (galaxies[g2].first..galaxies[g1].first)
            val colRange = if (galaxies[g1].second < galaxies[g2].second) (galaxies[g1].second..galaxies[g2].second) else (galaxies[g2].second..galaxies[g1].second)

            //Change coefficient to 1 for pt1
            val rowDist = rowRange.toList().size-1L + 999999L * rowsWithoutGalaxies.filter{it in rowRange}.size
            val colDist = colRange.toList().size-1L + 999999L * colsWithoutGalaxies.filter{it in colRange}.size
            dist += rowDist + colDist
        }
    }

    println(dist)

}
