package d16

import utils.*

data class Beam(val row: Int, val col: Int, val dir: Char)
fun main() {
    val grid = readCharGrid("/d16.txt")

    val left = grid.indices.map{ Beam(it, 0, '>')}
    val right = grid.indices.map{ Beam(it, grid[0].size-1, '<')}
    val top = grid[0].indices.map{ Beam(0, it, 'V')}
    val bot = grid[0].indices.map{ Beam(grid.size-1, it, '^')}

    println(
    (left + right + top + bot).map{ findEnergy(grid, it) }.map{it.size}.max()
    )

}
