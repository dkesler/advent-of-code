package d15

import utils.*

fun hash(str: String): Int {
    return str.fold(0) { acc, c ->
        ((acc + c.toInt()) * 17) % 256
    }
}

fun main() {
    val lines = readList("/d15.txt")
    val commands = lines[0].split(",")

    val boxes = mutableMapOf<Int, MutableList<Pair<String, Int>>>()

    commands.map { cmd ->
        val clean = cmd.filter{it != '\n' }

        if (clean.contains('=')) {
            val split = clean.split("=")
            val label = split[0]
            val hash = hash(label)
            val focalLength = split[1].toInt()
            if (hash !in boxes.keys) boxes[hash] = mutableListOf()
            if (boxes[hash]!!.any{ it.first == label }) {
                boxes[hash]!!.set( boxes[hash]!!.indexOfFirst{it.first == label}, Pair(label, focalLength))
            } else {
                boxes[hash]!!.add(Pair(label, focalLength))
            }
        } else {
            val label = clean.split("-")[0]
            val hash = hash(label)
            if (hash !in boxes.keys) boxes[hash] = mutableListOf()
            boxes[hash]!!.removeIf{ it.first == label }
        }
    }

    var totalFocalPower = 0
    for (box in boxes.keys) {
        val boxVal = box+1
        for (lens in boxes[box]!!.indices) {
            totalFocalPower += boxVal * (lens+1) * boxes[box]!![lens].second
        }
    }

    println(totalFocalPower)
}