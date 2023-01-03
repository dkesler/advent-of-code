package d15

import utils.readList
import kotlin.math.abs

data class Sensor(val sx: Int, val sy: Int, val bx: Int, val by: Int)

fun main() {
    val lines = readList("/d15.txt")
    val sensors = lines.map {
        val split = it.split(" ")
        val sx = split[2].split("=", ",")[1].toInt()
        val sy = split[3].split("=", ":")[1].toInt()
        val bx = split[8].split("=", ",")[1].toInt()
        val by = split[9].split("=", ",")[1].toInt()
        Sensor(sx, sy, bx, by)
    }

    var noBeaconCount = 0
    for (i in (-10000000..10000000)) {
        if (sensors.any {
            val bToS = abs(it.sx - it.bx) + abs(it.sy - it.by)
                    val ptToS = abs(i - it.sx) + abs(2000000 - it.sy)
                    val isBeacon = it.bx == i && it.by == 2000000
                    !isBeacon && ptToS <= bToS
        }) {
            noBeaconCount += 1
        }

    }

    println(noBeaconCount)
}