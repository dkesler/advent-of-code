package d21

import utils.*

fun main() {
    //val lines = utils.readList("/d21/d21p1")
    val p1Start = 10
    val p2Start = 6

    val res = playGame(p1Start, p2Start)

    if (res.first >= 1000) {
        println(res.second * res.third)
    } else {
        println(res.first * res.third)
    }

}

//p1 score, p2 score, rolls
fun playGame(p1Start: Int, p2Start: Int): Triple<Int, Int, Int> {
    var p1Score = 0
    var p2Score = 0
    var p1Pos = p1Start
    var p2Pos = p2Start
    val die = Die()

    while(p1Score < 1000 && p2Score < 1000) {
        val p1Roll = die.next() + die.next() + die.next()
        p1Pos = (p1Pos + p1Roll - 1) % 10 + 1
        p1Score += p1Pos
        if (p1Score >= 1000) {
            return Triple(p1Score, p2Score, die.totalRolls)
        }

        val p2Roll = die.next() + die.next() + die.next()
        p2Pos = (p2Pos + p2Roll - 1) % 10 + 1
        p2Score += p2Pos
    }

    return Triple(p1Score, p2Score, die.totalRolls)
}

class Die(var start:Int = 1, var totalRolls: Int = 0) {
    fun next(): Int {
        val v = start
        start++
        if (start > 100) {
            start = 1
        }
        totalRolls++
        return v
    }
}
