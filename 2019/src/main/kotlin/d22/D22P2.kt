package d22

import java.io.File
import java.lang.Long.parseLong
import java.math.BigInteger
import java.util.regex.Pattern

fun main() {

    val deckSize = BigInteger.valueOf(119315717514047L)
    val totalShuffles = 101741582076661L
    //val deckSize = 10007L //2019->6061->5233
    //val totalShuffles = 1L
//    val deckSize = 7L
//    val totalShuffles = 1L

    val moves = File("2019/src/main/resources/d22/d22p1").readLines()
        .map{toShuffleA(it, deckSize)}

    val movesRev = File("2019/src/main/resources/d22/d22p1").readLines()
        .map{toShuffleAInv(it, deckSize)}

    val ax = moves.fold(axplusbmodm(BigInteger.ONE, BigInteger.ZERO, deckSize), { acc, op -> op(acc) } )
    println(ax)
    val axInv = movesRev.foldRight(axplusbmodm(BigInteger.ONE, BigInteger.ZERO, deckSize), {op, acc -> op(acc) } )
    println(axInv)

    val axes = mutableListOf(axInv)
    for (exp in 1..46) {
        val axnext = axes[exp-1].combine(axes[exp-1])
        axes.add(axnext)
    }

    var shufflesRemaining = totalShuffles
    var totalAx = axplusbmodm(BigInteger.ONE, BigInteger.ZERO, deckSize)
    for (exp in 46.downTo(0)) {
        val exp2 = BigInteger.valueOf(2).pow(exp).toLong()
        while(exp2 <= shufflesRemaining) {
            totalAx = totalAx.combine(axes[exp])
            shufflesRemaining -= exp2
        }
    }

    println(totalAx)

    val x = axInv.solveForY(BigInteger.valueOf(2020))
    val y = ax.solveForY(x)
    println("$x -> 2020 | $y")

    println(totalAx.solveForY(BigInteger.valueOf(2020)))
}

fun toShuffleA(line: String, deckSize: BigInteger): (axplusbmodm) -> axplusbmodm {
    val incMatcher = Pattern.compile("deal with increment ([0-9]+)").matcher(line)
    if (incMatcher.matches()) {
        return { ax -> ax.times(BigInteger.valueOf(parseLong(incMatcher.group(1)))) }
    }

    val cutMatcher = Pattern.compile("cut (-?[0-9]+)").matcher(line)
    if (cutMatcher.matches()) {
        return { ax -> ax.plus(BigInteger.valueOf(-1 * parseLong( cutMatcher.group(1)))) }
    }

    val newMatcher = Pattern.compile("deal into new stack").matcher(line)
    if (newMatcher.matches()) {
        return { ax -> ax.times(BigInteger.valueOf(-1)).plus(deckSize-BigInteger.ONE) }
    }

    throw RuntimeException("Unparseable line $line")
}

fun toShuffleAInv(line: String, deckSize: BigInteger): (axplusbmodm) -> axplusbmodm {
    val incMatcher = Pattern.compile("deal with increment ([0-9]+)").matcher(line)
    if (incMatcher.matches()) {
        return { ax -> ax.times( axplusbmodm(BigInteger.valueOf(parseLong(incMatcher.group(1))), BigInteger.ZERO, deckSize).solveForX(BigInteger.ONE) ) }
    }

    val cutMatcher = Pattern.compile("cut (-?[0-9]+)").matcher(line)
    if (cutMatcher.matches()) {
        return { ax -> ax.plus(BigInteger.valueOf(parseLong( cutMatcher.group(1)))) }
    }

    val newMatcher = Pattern.compile("deal into new stack").matcher(line)
    if (newMatcher.matches()) {
        return { ax -> ax.times(BigInteger.valueOf(-1)).plus(deckSize-BigInteger.ONE) }
    }

    throw RuntimeException("Unparseable line $line")
}

data class axplusbmodm(val a: BigInteger, val b: BigInteger, val m: BigInteger) {
    fun times(n: BigInteger): axplusbmodm { return axplusbmodm(mod(a*n), mod(b*n), m) }
    fun plus(c: BigInteger): axplusbmodm { return axplusbmodm(a, mod(b+c), m) }
    fun combine(ax: axplusbmodm): axplusbmodm { return ax.times(a).plus(b) }
    private fun mod(i: BigInteger): BigInteger {
        return i.mod(m)
    }

    fun solveForX(y: BigInteger) : BigInteger {
        var N = BigInteger.ZERO
        while ( (y - b + N*m) % a  != BigInteger.ZERO) {
            N++
        }
        return mod((y - b + N*m) / a)

    }
    fun solveForY(x: BigInteger) : BigInteger {
        val l = (a * x + b) % m
        if (l < BigInteger.ZERO) {
            return l + m
        }
        return l
    }
}

