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

    val s = games.map { game ->
        val max = mutableMapOf<String, Int>()
        max["green"]=0
        max["red"]=0
        max["blue"]=0

        for (reveal in game) {
            if (max["green"]!! < reveal["green"] ?: 0) max["green"] = reveal["green"]!!
            if (max["red"]!! < reveal["red"] ?: 0) max["red"] = reveal["red"]!!
            if (max["blue"]!! < reveal["blue"] ?: 0) max["blue"] = reveal["blue"]!!
        }
        max["green"]!! * max["blue"]!! * max["red"]!!
    }.sum()

    println(s)
}