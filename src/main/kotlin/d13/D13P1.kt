package d13

import intcode.runProgramFromFile

fun main() {
    val output = runProgramFromFile("src/main/resources/d13/d13p1")
    println(output)
    val blockTiles = output
        .withIndex()
        .filter { it.index % 3 == 2 }
        .filter { it.value == 2L }
        .count()

    println(blockTiles)
}
