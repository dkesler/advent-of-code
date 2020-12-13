package d12

import kotlin.math.absoluteValue

data class Dir(val cmd: Char, val amt: Long)

val rMap = mapOf(
    Pair('E', 'S'),
    Pair('S', 'W'),
    Pair('W', 'N'),
    Pair('N', 'E')
)

val lMap =  mapOf(
    Pair('E', 'N'),
    Pair('S', 'E'),
    Pair('W', 'S'),
    Pair('N', 'W')
)
fun rotate(orientation: Char, dir: Char): Char {
    if (dir == 'R') {
        return rMap.getValue(orientation)
    } else {
        return lMap.getValue(orientation)
    }
}

fun toDir(str: String): Dir {
    return Dir(str[0], str.substring(1).toLong())
}

fun main() {
    val content = Class::class.java.getResource("/d12/d12p1").readText()
    val list = content.split("\n").filter{it != ""}.map(::toDir)

    var x = 0L
    var y = 0L
    var orientation = 'E'
    for (dir in list) {
        if (dir.cmd == 'R' || dir.cmd == 'L') {
            val turns = dir.amt / 90
            for (i in 0 until turns) {
                orientation = rotate(orientation, dir.cmd)
            }

        } else {
            val effectiveDir = if (dir.cmd == 'F') orientation else dir.cmd
            if (effectiveDir == 'N') {
                y += dir.amt
            } else if (effectiveDir == 'S') {
                y -= dir.amt
            } else if (effectiveDir == 'E') {
                x += dir.amt
            } else {
                x -= dir.amt
            }
        }
    }

    println(x)
    println(y)
    println(x.absoluteValue + y.absoluteValue)

}
