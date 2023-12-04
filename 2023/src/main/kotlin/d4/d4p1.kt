package d4

import utils.*

fun main() {
    val lines = readList("/d4.txt")

    val cards = lines.map{ it.split(":")[1] }
            .map {
                val s = it.split(" | ")
                val winning = s[0].split(" ").filter { it != "" }.map { it.toInt() }
                val mine = s[1].split(" ").filter { it != "" }.map { it.toInt() }
                Pair(winning.toSet(), mine.toSet())
            }

    println(cards.map{ it.first.intersect(it.second).size}
            .filter{it != 0}
            .map{Math.pow(2.0, it.toDouble()-1)}
            .sum()
    )
}
