package d15

import utils.readList
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.math.abs

fun pointsAtRadius(x: Int, y: Int, rad: Int): Stream<Pair<Int, Int>> {
    return (0..rad).toList().stream().flatMap{ offset ->
        val points = mutableSetOf<Pair<Int, Int>>()
        points.add(Pair(x + offset, y + (rad - offset)))
        points.add(Pair(x + offset, y - (rad - offset)))
        points.add(Pair(x - offset, y + (rad - offset)))
        points.add(Pair(x - offset, y - (rad - offset)))
        points.stream()
    }
}

fun main() {
    val start = System.currentTimeMillis()
    val lines = readList("/d15.txt")
    val sensors = lines.map {
        val split = it.split(" ")
        val sx = split[2].split("=", ",")[1].toInt()
        val sy = split[3].split("=", ":")[1].toInt()
        val bx = split[8].split("=", ",")[1].toInt()
        val by = split[9].split("=", ",")[1].toInt()
        Sensor(sx, sy, bx, by)
    }

    val candidatePoints = sensors.parallelStream().flatMap {
        val bToS = abs(it.sx - it.bx) + abs(it.sy - it.by)
        pointsAtRadius(it.sx, it.sy, bToS + 1)
    }

    val pointsDefined = System.currentTimeMillis()
    println("Points defined: ${pointsDefined - start}")
    candidatePoints.filter { it.first in (0..4000000) && it.second in (0..4000000) }
            .filter { point ->
                sensors.none {
                    val bToS = abs(it.sx - it.bx) + abs(it.sy - it.by)
                    val ptToS = abs(point.first - it.sx) + abs(point.second - it.sy)
                    val isBeacon = it.bx == point.first && it.by == point.second
                    !isBeacon && ptToS <= bToS

                }
            }.forEach { point ->
                println(point.first * 4000000L + point.second)
            }

    println("Solution: ${System.currentTimeMillis() - pointsDefined}")
    println("Total time: ${System.currentTimeMillis() - start}")
}