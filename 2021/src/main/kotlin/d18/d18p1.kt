package d18

import utils.*

fun main() {
    val list = readList("/d18/d18p1").map{toSnailfishNumber(it)}
    //list.forEach { println(it) }
    val added = list.reduce{ l,r -> l.add(r) }
    println(added)
    println(added.magnitude())
}

fun toSnailfishNumber(line: String): SnailfishNumber {
    if (line[0] == '[') {
        val topmostCommaIdx = getTopmostComma(line)
        return PairSnailfishNumber(
            toSnailfishNumber(line.substring(1, topmostCommaIdx)),
            toSnailfishNumber(line.substring(topmostCommaIdx+1, line.length-1))
        )
    } else {
        return RegularSnailfishNumber(line.toInt())
    }
}

fun getTopmostComma(line: String): Int {
    var openCount = 0
    var idx = 1
    while(line[idx] != ',' || openCount > 0) {
        if (line[idx] == '[') openCount++
        if (line[idx] == ']') openCount--
        ++idx
    }
    return idx
}

abstract class SnailfishNumber() {
    fun add(r: SnailfishNumber): SnailfishNumber {
        return PairSnailfishNumber(this, r).reduce()
    }

    abstract fun reduce(): SnailfishNumber
    abstract fun explodeIfNeeded(parentPairs: Int): ExplodeResult
    abstract fun splitIfNeeded(): SplitResult
    abstract fun magnitude(): Int
    abstract fun applyExplodeResult(amt: Int, fromLeft: Boolean): SnailfishNumber

}

data class ExplodeResult(val exploded: Boolean, val leftRemainder: Int, val rightReminder: Int, val result: SnailfishNumber)
data class SplitResult(val split: Boolean, val result: SnailfishNumber)

data class RegularSnailfishNumber(val literal: Int): SnailfishNumber() {
    override fun reduce(): SnailfishNumber {
        return this
    }

    override fun explodeIfNeeded(parentPairs: Int): ExplodeResult {
        return ExplodeResult(false, 0, 0, this)
    }

    override fun splitIfNeeded(): SplitResult {
        if (literal >= 10) {
            val l = RegularSnailfishNumber(literal/2)
            val r = RegularSnailfishNumber(literal - l.literal)
            return SplitResult(true, PairSnailfishNumber(l, r))
        } else {
            return SplitResult(false, this)
        }
    }

    override fun magnitude(): Int {
        return literal
    }

    override fun applyExplodeResult(amt: Int, fromLeft: Boolean): SnailfishNumber {
        return RegularSnailfishNumber(literal+amt)
    }

    override fun toString(): String {
        return literal.toString()
    }
}

data class PairSnailfishNumber(val l: SnailfishNumber, val r: SnailfishNumber ): SnailfishNumber() {
    override fun reduce(): SnailfishNumber {
        val explodeResult = this.explodeIfNeeded(0)

        if (explodeResult.exploded) {
            return explodeResult.result.reduce()
        }

        val splitResult = this.splitIfNeeded()
        if (splitResult.split) {
            return splitResult.result.reduce()
        }

        return this
    }

    override fun explodeIfNeeded(parentPairs: Int): ExplodeResult {
        if (parentPairs == 4) {
            return ExplodeResult(true, l.magnitude(), r.magnitude(), RegularSnailfishNumber(0))
        }

        val lExplode = l.explodeIfNeeded(parentPairs+1)
        if (lExplode.exploded) {
            val rWithExplodeResultApplied = r.applyExplodeResult(lExplode.rightReminder, true)
            return ExplodeResult(
                true,
                lExplode.leftRemainder,
                0,
                PairSnailfishNumber(lExplode.result, rWithExplodeResultApplied)
            )
        }

        val rExplode = r.explodeIfNeeded(parentPairs+1)
        if (rExplode.exploded) {
            val lWithExplodeResultApplied = l.applyExplodeResult(rExplode.leftRemainder, false)
            return ExplodeResult(
                true,
                0,
                rExplode.rightReminder,
                PairSnailfishNumber(lWithExplodeResultApplied, rExplode.result)
            )
        }

        return ExplodeResult(false, 0, 0, this)
    }

    override fun splitIfNeeded(): SplitResult {
        val lSplit = l.splitIfNeeded()
        if (lSplit.split) {
            return SplitResult(true, PairSnailfishNumber(lSplit.result, r))
        }

        val rSplit = r.splitIfNeeded()
        if (rSplit.split) {
            return SplitResult(true, PairSnailfishNumber(l, rSplit.result))
        }

        return SplitResult(false, this)
    }

    override fun magnitude(): Int {
        return 3*l.magnitude() + 2*r.magnitude()
    }

    override fun applyExplodeResult(amt: Int, fromLeft: Boolean): SnailfishNumber {
        if (amt == 0) {
            return this
        }

        if (fromLeft) {
            return PairSnailfishNumber(
                l.applyExplodeResult(amt, fromLeft),
                r
            )
        } else {
            return PairSnailfishNumber(
                l,
                r.applyExplodeResult(amt, fromLeft)
            )
        }
    }

    override fun toString(): String {
        return "[$l,$r]"
    }

}
