package d22

import d19.Point
import utils.*

data class InitCommand(val on: Boolean, val x: IntRange, val y: IntRange, val z: IntRange)
fun main() {
    val lines = readList("/d22/d22p1test").map{it.split(" ")}
        .map{ Pair(it[0], it[1].split(","))}
        .map{
            InitCommand(it.first == "on", toIntRange(it.second[0]), toIntRange(it.second[1]), toIntRange(it.second[2]))
        }
    println(lines)

    val cube = mutableSetOf<Point>()

    for (line in lines) {
        for (x in -50..50) {
            for (y in -50..50) {
                for (z in -50..50) {
                    if (x in line.x && y in line.y && z in line.z) {
                        if (line.on) {
                            cube.add(Point(x, y, z))
                        } else {
                            cube.remove(Point(x, y, z))
                        }
                    }

                }
            }
        }
    }
    println(cube.size)

}

fun toIntRange(s: String): IntRange {
    val split = s.substring(2).split("..")
    return IntRange(split[0].toInt(), split[1].toInt())
}
