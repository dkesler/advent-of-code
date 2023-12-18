package d16

import utils.*

fun main() {
    val grid = readCharGrid("/d16.txt")

    val energized = findEnergy(grid, Beam(0, 0, '>'))


    println(energized.size)



}

fun findEnergy(grid: List<List<Char>>, start: Beam): MutableSet<Point> {
    var beams = mutableSetOf(start)
    val visited = mutableSetOf<Beam>()
    val energized = mutableSetOf<Point>()

    while (beams.isNotEmpty()) {
        val newBeams = mutableSetOf<Beam>()
        for (beam in beams) {
            if (beam !in visited) {
                visited.add(beam)
                energized.add(Point(beam.row, beam.col))

                if (beam.dir == '>') {
                    val cur = grid[beam.row][beam.col]
                    if (cur == '.' || cur == '-') newBeams.add(Beam(beam.row, beam.col + 1, '>'))
                    if (cur == '\\') newBeams.add(Beam(beam.row + 1, beam.col, 'V'))
                    if (cur == '/') newBeams.add(Beam(beam.row - 1, beam.col, '^'))
                    if (cur == '|') {
                        newBeams.add(Beam(beam.row - 1, beam.col, '^'))
                        newBeams.add(Beam(beam.row + 1, beam.col, 'V'))
                    }
                } else if (beam.dir == '<') {
                    val cur = grid[beam.row][beam.col]
                    if (cur == '.' || cur == '-') newBeams.add(Beam(beam.row, beam.col - 1, '<'))
                    if (cur == '\\') newBeams.add(Beam(beam.row - 1, beam.col, '^'))
                    if (cur == '/') newBeams.add(Beam(beam.row + 1, beam.col, 'V'))
                    if (cur == '|') {
                        newBeams.add(Beam(beam.row - 1, beam.col, '^'))
                        newBeams.add(Beam(beam.row + 1, beam.col, 'V'))
                    }
                } else if (beam.dir == '^') {
                    val cur = grid[beam.row][beam.col]
                    if (cur == '.' || cur == '|') newBeams.add(Beam(beam.row - 1, beam.col, '^'))
                    if (cur == '\\') newBeams.add(Beam(beam.row, beam.col - 1, '<'))
                    if (cur == '/') newBeams.add(Beam(beam.row, beam.col + 1, '>'))
                    if (cur == '-') {
                        newBeams.add(Beam(beam.row, beam.col - 1, '<'))
                        newBeams.add(Beam(beam.row, beam.col + 1, '>'))
                    }
                } else if (beam.dir == 'V') {
                    val cur = grid[beam.row][beam.col]
                    if (cur == '.' || cur == '|') newBeams.add(Beam(beam.row + 1, beam.col, 'V'))
                    if (cur == '\\') newBeams.add(Beam(beam.row, beam.col + 1, '>'))
                    if (cur == '/') newBeams.add(Beam(beam.row, beam.col - 1, '<'))
                    if (cur == '-') {
                        newBeams.add(Beam(beam.row, beam.col - 1, '<'))
                        newBeams.add(Beam(beam.row, beam.col + 1, '>'))
                    }
                }
            }
            newBeams.removeIf { it.row < 0 || it.row >= grid.size || it.col < 0 || it.col >= grid[0].size }
            newBeams.removeIf { it in visited }
            beams = newBeams
        }
    }
    return energized
}