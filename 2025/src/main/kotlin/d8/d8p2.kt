package d8

import utils.readList
import kotlin.math.sign

fun main() {
    val boxes = readList("/d8.txt").map{
        val s = it.split(",")
        Triple(s[0].toInt(), s[1].toInt(), s[2].toInt())
    }

    val distances = mutableMapOf<Set<Triple<Int, Int, Int>>, Double>()
    for (box1 in boxes) {
        for (box2 in boxes) {
            if (box1 != box2) {
                val distance = Math.sqrt(
                    Math.pow(Math.abs(box1.first.toDouble()- box2.first), 2.0) +
                            Math.pow(Math.abs(box1.second.toDouble()- box2.second), 2.0) +
                            Math.pow(Math.abs(box1.third.toDouble()- box2.third), 2.0)
                )
                distances[setOf(box1, box2)] = distance
            }
        }
    }

    val toConnect = distances.toList().sortedBy { it.second }.map{it.first}

    val groupMemberships = mutableMapOf<Triple<Int, Int, Int>, Set<Triple<Int, Int, Int>>>()

    for (box in boxes) groupMemberships[box] = setOf(box)

    for (connection in toConnect) {
        val allAffected = connection.flatMap{ groupMemberships[it]!! }.toSet()
        if (allAffected.size == boxes.size) {
            println(connection.map{it.first}.fold(1, {a, b -> a*b }))
            break
        }
        for (affected in allAffected) groupMemberships[affected] = allAffected.toSet()
    }

}