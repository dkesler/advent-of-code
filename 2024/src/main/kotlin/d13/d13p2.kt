package d13

import utils.readBlocks
import kotlin.math.round

fun main() {
    val blocks = readBlocks("/d13.txt")

    val prizes = blocks.map{ toPrize2(it) }

    println(prizes.map{ minWin2(it) }.filter{ it != Long.MAX_VALUE}.sum())

}

fun minWin2(prize: Prize): Long {

    val bn = round((prize.prizeX.toDouble() - prize.aX.toDouble() * prize.prizeY.toDouble() / prize.aY.toDouble()) / (prize.bX.toDouble() - prize.aX.toDouble() * prize.bY.toDouble() / prize.aY.toDouble())).toLong()
    val an = round(((prize.prizeX - bn * prize.bX).toDouble() / prize.aX.toDouble())).toLong()

    if (bn * prize.bX + an * prize.aX == prize.prizeX && bn * prize.bY + an * prize.aY == prize.prizeY) {
        return an*3 + bn
    } else {
        return Long.MAX_VALUE
    }
}

fun toPrize2(it: List<String>): Prize {
    val asplit = it[0].split(":")[1].split(",")
    val aX = asplit[0].split("+")[1].trim().toLong()
    val aY = asplit[1].split("+")[1].trim().toLong()

    val bsplit = it[1].split(":")[1].split(",")
    val bX = bsplit[0].split("+")[1].trim().toLong()
    val bY = bsplit[1].split("+")[1].trim().toLong()

    val psplit = it[2].split(":")[1].split(",")
    val pX = psplit[0].split("=")[1].trim().toLong() + 10000000000000
    val pY = psplit[1].split("=")[1].trim().toLong() + 10000000000000

    return Prize(aX, aY, bX, bY, pX, pY)
}
