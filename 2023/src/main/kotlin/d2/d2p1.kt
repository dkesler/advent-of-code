package d2

import utils.*

fun main() {
    val lines = readList("/d2.txt")

    val games = lines.map {
        val split = it.split(":")[1].split(";")
        split.map { reveal ->
            val rs = reveal.split(", ")
            rs.map { color ->
                val cs = color.split(" ").filter{it != ""}
                Pair(cs[1], cs[0].toInt())
            }.toMap()
        }
    }

    val c = games.mapIndexed{ ix, game ->
     if (game.all { reveal ->
         reveal.getOrDefault("green", 0) <= 13 &&
                 reveal.getOrDefault("blue", 0) <= 14 &&
                 reveal.getOrDefault("red", 0) <= 12
     })
         ix+1
     else
         0
    }.sum()

    println(c)
}