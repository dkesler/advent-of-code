package d1

import utils.*

fun main() {
    val blocks = readLongBlocks("/d1.txt")

    println(blocks.maxOf{ it.sum() })
}

