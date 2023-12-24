package d22

import utils.*

fun isec(a: IntRange, b: IntRange): Boolean {
    return a.first in b || a.last in b || b.first in a || b.last in a
}
data class Pt3d(val id: String, val x: IntRange, val y: IntRange, val z: IntRange) {
    fun intersects(other: Pt3d): Boolean  {
        return isec(x, other.x) && isec(y, other.y) && isec(z, other.z)
    }
}
fun toRange(l: Int, r: Int): IntRange {
    return if (l <= r) l..r else r..l
}

fun drop(bricks: Set<Pt3d>): MutableSet<Pt3d> {
    val newBricks = mutableSetOf<Pt3d>()
    for (brick in bricks) {
        if (brick.z.first > 1) {
            val newBrick = Pt3d(brick.id, brick.x, brick.y, brick.z.first - 1..brick.z.last - 1)
            if (bricks.any { it != brick && it.intersects(newBrick) }) {
                newBricks.add(brick)
            } else {
                newBricks.add(newBrick)
            }
        } else {
            newBricks.add(brick)
        }
    }
    return newBricks
}

fun main() {
    val lines = readList("/d22.txt")
    var bricks = lines.map {
        val s = it.split("~")
        val l = s[0].split(",")
        val r = s[1].split(",")
        Pt3d(it, toRange(l[0].toInt(), r[0].toInt()), toRange(l[1].toInt(), r[1].toInt()), toRange(l[2].toInt(), r[2].toInt()))
    }.toSet()


    var moved = true
    while(moved) {
        moved = false
        val newBricks = drop(bricks)

        if (newBricks != bricks) {
            bricks = newBricks
            moved = true
        }
    }

    val toNuke = mutableSetOf<Pt3d>()
    for (brick in bricks) {
        val nukedBricks = bricks - brick
        val droppedBricks = drop(nukedBricks)
        if (droppedBricks == nukedBricks) {
            toNuke.add(brick)
        }
    }

    println(toNuke.size)

}

