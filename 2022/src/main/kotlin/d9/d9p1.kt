package d9

import utils.*
import kotlin.math.abs

fun main() {
    val lines = readList("/d9.txt")
    val tailVisited = mutableSetOf(Pair(0, 0))
    var head = Pair(0, 0)
    var tail = Pair(0, 0)

    for (line in lines) {
        val dir = line.split(" ")[0]
        val amt = line.split(" ")[1].toInt()
        for (x in (0 until amt)) {
            if (dir == "U") {
                head = Pair(head.first, head.second-1)
            } else if (dir == "D") {
                head = Pair(head.first, head.second+1)
            } else if (dir == "L") {
                head = Pair(head.first-1, head.second)
            } else {
                head = Pair(head.first+1, head.second)
            }

            if (head.first == tail.first) {
                val diff = head.second - tail.second
                if (abs(diff) >= 2) {
                    tail = Pair(tail.first, tail.second + diff / abs(diff))
                }
            } else if (head.second == tail.second) {
                val diff = head.first - tail.first
                if (abs(diff) >= 2) {
                    tail = Pair(tail.first + diff / abs(diff), tail.second)
                }

            } else {
                val vDiff = head.first - tail.first
                val hDiff = head.second - tail.second
                if (abs(vDiff) >= 2 || abs(hDiff) >= 2) {
                    tail = Pair(tail.first + vDiff / abs(vDiff), tail.second + hDiff / abs(hDiff))
                }
            }

            tailVisited.add(tail)
        }
    }

    println(tailVisited.size)
}

