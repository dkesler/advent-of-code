package d21

import utils.*

fun main() {
    val p1Start = 10
    val p2Start = 6

    val wins = wins(10, 6)
    if (wins.first > wins.second) {
        println(wins.first)
    } else {
        println(wins.second)
    }

}

data class GameState(val p1Pos: Int, val p2Pos: Int, val p1Score: Int, val p2Score: Int, val rolling: Int)

fun wins(p1Start: Int, p2Start: Int): Pair<Long, Long> {
    val memoTable = mutableMapOf<GameState, Pair<Long, Long>>()
    var tc = 0

    fun winsIter(p1Start: Int, p2Start: Int, p1Score: Int, p2Score: Int, playerRolling: Int): Pair<Long, Long> {
        tc++
        if (p1Score >= 21) return Pair(1, 0)
        if (p2Score >= 21) return Pair(0, 1)
        val gs = GameState(p1Start, p2Start, p1Score, p2Score, playerRolling)
        if (memoTable.containsKey(gs)) {
            return memoTable[gs]!!
        }

        val newWins = if (playerRolling == 1) {
            rolls().map{
                val p1Pos = (p1Start + it - 1) % 10 + 1
                winsIter(p1Pos, p2Start, p1Score+p1Pos, p2Score, 2)
            }.reduce { a, b ->
                Pair(a.first + b.first, a.second + b.second)
            }
        } else {
            rolls().map{
                val p2Pos = (p2Start + it - 1) % 10 + 1
                winsIter(p1Start, p2Pos, p1Score, p2Score + p2Pos, 1)
            }.reduce { a, b ->
                Pair(a.first + b.first, a.second + b.second)
            }
        }

        memoTable.put(gs, newWins)
        return newWins
    }

    val res = winsIter(p1Start, p2Start, 0, 0, 1)
    return res
}

fun rolls(): List<Int> {
    return (1..3).map{ r1 ->
        (1..3).map{ r2 ->
            (1..3).map { r3 ->
                r1+r2+r3
            }
        }
    }.flatten().flatten()
}
