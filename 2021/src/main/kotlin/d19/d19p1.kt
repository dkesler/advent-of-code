package d19

import utils.*

fun main() {
    val list = readList("/d19/d19p1")
    val scanners = toScannerBeacons(list).toMutableList()
    val normalizedScanners = mutableListOf<List<Point>>(scanners.removeFirst())
    val offsets = mutableListOf<Point>()

    while(scanners.isNotEmpty()) {
        //each iteration, we find one unnormalized scanner and normalize it to the first scanner's
        //coordinate system
        val (idx, normalized, offset) = normalizeOneScanner(scanners, normalizedScanners)
        normalizedScanners.add(normalized)
        offsets.add(offset)
        scanners.removeAt(idx)
    }

    //once all scanners have been normalized to the same coordinate system, we just need to
    //find the count of all unique points
    println(normalizedScanners.flatten().toSet().size)

    //and the largest manhattan distance
    println(offsets.map{o1 -> offsets.map{ Pair(o1, it) }}.flatten()
        .map{ manhattan(it.first, it.second) }.max()!!)


}

fun manhattan(first: Point, second: Point): Int {
    return Math.abs(first.x - second.x) + Math.abs(first.y - second.y) + Math.abs(first.z - second.z)
}

fun normalizeOneScanner(
    unnormalizedScanners: MutableList<List<Point>>,
    normalizedScanners: MutableList<List<Point>>
): Triple<Int, List<Point>, Point> {
    for (normalized in normalizedScanners) {
        for (unnormalized in unnormalizedScanners) {
            //because we don't know the orientation of the unnormalized scanner, we need to try every
            //orientation possible
            for (rotation in rot(unnormalized)) {
                val (overlapFound, offset) = findOverlaps(normalized, rotation)
                if (overlapFound) {
                    return Triple(unnormalizedScanners.indexOf(unnormalized), norm(rotation, offset), offset)
                }
            }
        }
    }
    System.out.println("failed to find overlap")
    return Triple(0, listOf(), Point(0, 0, 0))
}

fun norm(rotation: List<Point>, offset: Point): List<Point> {
    return rotation.map{ Point(it.x + offset.x, it.y + offset.y, it.z + offset.z)}
}

fun findOverlaps(a: List<Point>, b: List<Point>): Pair<Boolean, Point> {
    for (a0 in a) {
        for (b0 in b) {
            //if we assume a0 == b0, how many overlaps can we get?
            //based on that assumption, find the offset that would normalize b _if_ a0==b0
            val xOffset = a0.x - b0.x
            val yOffset = a0.y - b0.y
            val zOffset = a0.z - b0.z
            //then, remap all of b, still with all of our current assumptions
            val bRemapped = b.map{ Point(it.x + xOffset, it.y + yOffset, it.z + zOffset) }
            //if the result of that remapping results in at least 12 overlaps, then our assumption
            //must have been correct, a0 matches b0, and our offset can be applied to b to normalize it
            val overlap = bRemapped.intersect(a)
            if (overlap.size >= 12) {
                return Pair(true, Point(xOffset, yOffset, zOffset))
            }
        }
    }
    return Pair(false, Point(0, 0, 0))
}

fun rot(unnormalized: List<Point>): List<List<Point>> {
    return (0..23).map{r -> unnormalized.map{it.rot(r)}}
}


data class Point(val x: Int, val y: Int, val z: Int) {
    fun rot(rotNum: Int): Point {
        return when(rotNum) {
            0 -> this
            1 -> Point(x, z, -y)
            2 -> Point(x, -y, -z)
            3 -> Point(x, -z, y)
            4 -> Point(-x, y, -z)
            5 -> Point(-x, -z, -y)
            6 -> Point(-x, -y, z)
            7 -> Point(-x, z, y)
            8 -> Point(y, -x, z)
            9 -> Point(y, z, x)
            10 -> Point(y, x, -z)
            11 -> Point(y, -z, -x)
            12 -> Point(-y, x, z)
            13 -> Point(-y, z, -x)
            14 -> Point(-y, -x, -z)
            15 -> Point(-y, -z, x)
            16 -> Point(z, y, -x)
            17 -> Point(z, -x, -y)
            18 -> Point(z, -y, x)
            19 -> Point(z, x, y)
            20 -> Point(-z, y, x)
            21 -> Point(-z, x, -y)
            22 -> Point(-z, -y, -x)
            23 -> Point(-z, -x, y)
            else -> this
        }
    }
}

fun toScannerBeacons(list: List<String>): List<List<Point>> {
    val scanners = mutableListOf<List<Point>>()
    val beacons = mutableListOf<Point>()
    for (line in list.subList(1, list.size)) {
        if (!line.contains("---")) {
            val split = line.split(",")
            beacons.add(Point(split[0].toInt(), split[1].toInt(), split[2].toInt()))
        } else {
            scanners.add(beacons.toList())
            beacons.clear()
        }
    }
    scanners.add(beacons)
    return scanners.toList()

}
