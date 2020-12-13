import d12.toDir
import kotlin.math.absoluteValue

fun rotate2(dir: Char, wX: Long, wY: Long): Pair<Long, Long> {
    if (dir == 'L') {
        return Pair(-wY, wX)
    } else {
        return Pair(wY, -wX)
    }
}


fun main() {
    val content = Class::class.java.getResource("/d12/d12p1").readText()
    val list = content.split("\n").filter{it != ""}.map(::toDir)

    var shipX = 0L
    var shipY = 0L
    var waypointX = 10L
    var waypointY = 1L

    for (dir in list) {
        if (dir.cmd == 'R' || dir.cmd == 'L') {
            val turns = dir.amt / 90
            for (i in 0 until turns) {
                val result =  rotate2(dir.cmd, waypointX, waypointY)
                waypointX = result.first
                waypointY = result.second
            }

        } else {
            if (dir.cmd == 'F') {
                for (i in 0 until dir.amt) {
                    shipX += waypointX
                    shipY += waypointY
                }
            } else if (dir.cmd == 'N') {
                waypointY += dir.amt
            } else if (dir.cmd == 'S') {
                waypointY -= dir.amt
            } else if (dir.cmd == 'E') {
                waypointX += dir.amt
            } else {
                waypointX -= dir.amt
            }
        }
        println(shipX)
        println(shipY)
        println(waypointX)
        println(waypointY)
        println()
    }

    println(shipX)
    println(shipY)
    println(shipX.absoluteValue + shipY.absoluteValue)

}
