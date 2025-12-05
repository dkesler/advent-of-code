package d1

import utils.readList

fun main() {
    val moves = readList("/d1.txt").map{ Pair(it[0], it.substring(1).toLong())  }

    var dial = 50L
    var zeros = 0L
    for (move in moves) {
        val moveVal = move.second % 100
        zeros += move.second / 100
        if (move.first == 'R') {
            dial = (dial + moveVal)
        } else {
            if (dial == 0L) dial = 100
            dial = (dial - moveVal)
        }

        if (dial == 0L) zeros++

        while (dial < 0) {
            zeros++
            dial += 100
        }
        while (dial >= 100) {
            zeros++
            dial -= 100
        }
    }

    println(zeros)
}