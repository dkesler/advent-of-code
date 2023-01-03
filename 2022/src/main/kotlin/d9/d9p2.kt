package d9

import utils.*
import kotlin.math.abs

fun main() {
    val lines = readList("/d9.txt")
    val tailVisited = mutableSetOf(Pair(0, 0))
    val rope = mutableListOf(
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0)
    )

    for (line in lines) {
        val dir = line.split(" ")[0]
        val amt = line.split(" ")[1].toInt()
        for (x in (0 until amt)) {
            if (dir == "U") {
                rope[0] = Pair(rope[0].first, rope[0].second-1)
            } else if (dir == "D") {
                rope[0] = Pair(rope[0].first, rope[0].second+1)
            } else if (dir == "L") {
                rope[0] = Pair(rope[0].first-1, rope[0].second)
            } else {
                rope[0] = Pair(rope[0].first+1, rope[0].second)
            }
            for (y in (1..9)) {
                if (rope[y].first == rope[y-1].first) {
                    val diff = rope[y-1].second - rope[y].second
                    if (abs(diff) >= 2) {
                        rope[y] = Pair(rope[y].first, rope[y].second + diff / abs(diff))
                    }
                } else if (rope[y-1].second == rope[y].second) {
                    val diff = rope[y-1].first - rope[y].first
                    if (abs(diff) >= 2) {
                        rope[y] = Pair(rope[y].first + diff / abs(diff), rope[y].second)
                    }
                } else {
                    val vDiff = rope[y-1].first - rope[y].first
                    val hDiff = rope[y-1].second - rope[y].second
                    if (abs(vDiff) >= 2 || abs(hDiff) >= 2) {
                        rope[y] = Pair(rope[y].first + vDiff / abs(vDiff), rope[y].second + hDiff / abs(hDiff))
                    }
                }
            }

            tailVisited.add(rope[9])
        }
    }

    println(tailVisited.size)
}

