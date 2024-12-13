package d13

import utils.readBlocks
import kotlin.math.min

fun main() {
    val blocks = readBlocks("/d13.txt")

    val prizes = blocks.map{ toPrize(it) }

    println(prizes.map{ minWin(it) }.filter{ it != Long.MAX_VALUE}.sum())
}

fun minWin(prize: Prize): Long {

    var minTokens = Long.MAX_VALUE

    val maxA = min(prize.prizeX / prize.aX, prize.prizeY / prize.aY)
    for (a in 0..maxA) {
        val dAX = a * prize.aX
        val dAY = a * prize.aY
        val leftX = prize.prizeX - dAX
        val leftY = prize.prizeY - dAY

        if (leftX % prize.bX == 0L && leftY % prize.bY == 0L) {
            val bFromX = leftX / prize.bX
            val bFromY = leftY / prize.bY
            if (bFromX == bFromY) {
                val tokens = 3*a + bFromX
                if (tokens < minTokens) minTokens = tokens
            }
        }


    }


    return minTokens
}

data class Prize( val aX: Long, val aY: Long, val bX: Long, val bY: Long, val prizeX: Long, val prizeY: Long)
fun toPrize(it: List<String>): Prize {
    val asplit = it[0].split(":")[1].split(",")
    val aX = asplit[0].split("+")[1].trim().toLong()
    val aY = asplit[1].split("+")[1].trim().toLong()

    val bsplit = it[1].split(":")[1].split(",")
    val bX = bsplit[0].split("+")[1].trim().toLong()
    val bY = bsplit[1].split("+")[1].trim().toLong()

    val psplit = it[2].split(":")[1].split(",")
    val pX = psplit[0].split("=")[1].trim().toLong()
    val pY = psplit[1].split("=")[1].trim().toLong()

    return Prize(aX, aY, bX, bY, pX, pY)
}
