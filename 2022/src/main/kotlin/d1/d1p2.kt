package d1

import utils.*

fun main() {
    val blocks = readLongBlocks("/d1.txt")

    println(blocks.map{it.sum()}.sortedDescending().take(3).sum())
}

