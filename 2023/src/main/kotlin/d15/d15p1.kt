package d15

import utils.*

fun main() {
    val lines = readList("/d15.txt")
    val commands = lines[0].split(",")
    val sum = commands.map { cmd ->
        val clean = cmd.filter{it != '\n' }
        clean.fold(0) { acc, c ->
            ((acc + c.toInt()) * 17) % 256
        }
    }.sum()

    println(sum)

}