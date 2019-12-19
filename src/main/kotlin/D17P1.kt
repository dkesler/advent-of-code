import intcode.Computer
import intcode.loadProgramFromFile

fun main() {
    val computer = Computer(loadProgramFromFile("src/main/resources/d17p1"))

    val output = computer.runBlockingProgram(listOf()).second.map{ it.toChar() }

    val scaffold = toScaffold(output)

    scaffold.print()

    println(scaffold.intersections().map{ it.first * it.second }.sum())


}

class Scaffold(val tiles: List<List<Char>>) {
    fun print() {
        for (line in tiles) {
            for (tile in line) {
                print(tile)
            }
            println("")
        }
    }

    fun intersections(): List<Pair<Int, Int>> {
        return tiles.mapIndexed{ y, line -> line.mapIndexed{ x, tile -> Pair(x, y) }.filterIndexed{ x, v -> isIntersection(x, y) }}.flatMap{it}
    }

    private fun isIntersection(x: Int, y: Int): Boolean {
        if (y == 0 || y == tiles.size-1) {
            return false
        }

        if (x == 0 || x == tiles[y].size-1) {
            return false
        }

        val b = tiles[y][x] == '#' && tiles[y - 1][x] == '#' && tiles[y + 1][x] == '#' && tiles[y][x - 1] == '#' && tiles[y][x + 1] == '#'
        return b
    }
}

fun toScaffold(chars: List<Char>): Scaffold {
    val width = chars.takeWhile { it != '\n' }.size + 1

    return Scaffold(chars.chunked(width).map{ it.filter{ c -> c != '\n'} })
}
