package d22

import utils.*


fun drop2(bricks: Set<Pt3d>): MutableSet<Pt3d> {
    val newBricks = mutableSetOf<Pt3d>()
    for (brick in bricks) {
        if (brick.z.first > 1) {
            val otherBricks = bricks - brick

            val topZ = otherBricks.maxOfOrNull{ if (isec(it.x, brick.x) && isec(it.y, brick.y) && it.z.last < brick.z.first) it.z.last else 0 } ?: 0
            val zDrop = brick.z.first - topZ - 1
            val newBrick = Pt3d(brick.id, brick.x, brick.y, brick.z.first - zDrop..brick.z.last - zDrop)
            //if (bricks.any { it != brick && it.intersects(newBrick) }) {
                newBricks.add(newBrick)
            //} else {
//                newBricks.add(newBrick)
//            }
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

    var dropped = 0
    for (brick in bricks) {
        val nukedBricks = bricks - brick

        var droppedBricks = nukedBricks.toMutableSet()
        moved = true
        while(moved) {
            moved = false
            val newBricks = drop(droppedBricks)

            if (newBricks != droppedBricks) {
                droppedBricks = newBricks
                moved = true
            }
        }

        droppedBricks.removeAll(nukedBricks)
        dropped += droppedBricks.size

    }

    println(dropped)
    //75878 wrong

}

