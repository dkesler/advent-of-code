import java.io.File
import java.lang.Long.parseLong
import java.math.BigInteger
import java.util.regex.Pattern

fun main() {

    val deckSize = 119315717514047L
    val totalShuffles = 101741582076661L
    //val deckSize = 10007L //2019->6061->5233
    //val totalShuffles = 1L
//    val deckSize = 7L
//    val totalShuffles = 1L

    val moves = File("src/main/resources/d22p1").readLines()
        .map{toShuffleA(it, deckSize)}

    val movesRev = File("src/main/resources/d22p1").readLines()
        .map{toShuffleAInv(it, deckSize)}


/*
    val moves = listOf("cut 2", "deal with increment 2")
    //val moves = listOf("cut 500", "deal with increment 53", "cut 619")
        .map{toShuffleA(it, deckSize)}

    //val movesRev = listOf("cut 500", "deal with increment 53", "cut 619")
    val movesRev = listOf("cut 2", "deal with increment 2")
        .map{toShuffleAInv(it, deckSize)}
*/


    //val moves = File("src/main/resources/d22p1").readLines()
//        .map(::toShuffle)

    //val deck = (0..119315717514046L).toList()
    //val shuffled = moves.fold(deck, { acc, op -> op(acc) })


/*
    val seenPositions = mutableSetOf<Long>()

    fun checkSeen(i: Long, acc: Long) {
        if (seenPositions.contains(acc)) {
            println("Seen cycle at iteration $i with value $acc")
        } else {
            seenPositions.add(acc)
        }
    }
*/
    val ax = moves.fold(axplusbmodm(1, 0, deckSize), {acc, op -> op(acc) } )
    println(ax)
    val axInv = movesRev.foldRight(axplusbmodm(1, 0, deckSize), {op, acc -> op(acc) } )
    println(axInv)

/*
    for (x in 0 until deckSize) {
        print("$x ")
    }
    println("")

    val transformed = (0 until deckSize).map{ Pair(ax.solveForY(it), it) }.toMap()

    for (x in 0 until deckSize) {
        val y = transformed.getValue(x)
        print("$y ")
    }
    println("")

    for (x in 0 until deckSize) {
        val xp = axInv.solveForY(ax.solveForY(x))
        print("$xp ")
    }
    println("")
*/

    val axes = mutableListOf(axInv)
    for (exp in 1..46) {
        val axnext = axes[exp-1].combine(axes[exp-1])
        axes.add(axnext)
    }

    var shufflesRemaining = totalShuffles
    var totalAx = axplusbmodm(1, 0, deckSize)
    for (exp in 46.downTo(0)) {
        val exp2 = BigInteger.valueOf(2).pow(exp).toLong()
        while(exp2 <= shufflesRemaining) {
            totalAx = totalAx.combine(axes[exp])
            shufflesRemaining -= exp2
        }
    }

    println(totalAx)

    val x = axInv.solveForY(2020)
    val y = ax.solveForY(x)
    println("$x -> 2020 | $y")


}

var lastTiming = System.currentTimeMillis()
fun printWithTiming(i: Long) {
    val currentTime = System.currentTimeMillis()
    if (i % 100000 == 0L) {
        println("iteration: $i.  Previous 100000 iterations took ${currentTime - lastTiming} ms")
        lastTiming = currentTime
    }
}


fun toShuffleInv(line: String, deckSize: Long): (Long) -> Long {
    val incMatcher = Pattern.compile("deal with increment ([0-9]+)").matcher(line)
    if (incMatcher.matches()) {
        return { incrementNInv(it, parseLong(incMatcher.group(1)), deckSize)  }
    }

    val cutMatcher = Pattern.compile("cut (-?[0-9]+)").matcher(line)
    if (cutMatcher.matches()) {
        return { cutNInv(it, parseLong(cutMatcher.group(1)), deckSize) }
    }

    val newMatcher = Pattern.compile("deal into new stack").matcher(line)
    if (newMatcher.matches()) {
        return { newStackInv(it, deckSize) }
    }

    throw RuntimeException("Unparseable line $line")
}

fun toShuffleA(line: String, deckSize: Long): (axplusbmodm) -> axplusbmodm {
    val incMatcher = Pattern.compile("deal with increment ([0-9]+)").matcher(line)
    if (incMatcher.matches()) {
        return { ax -> ax.times(parseLong(incMatcher.group(1))) }
    }

    val cutMatcher = Pattern.compile("cut (-?[0-9]+)").matcher(line)
    if (cutMatcher.matches()) {
        return { ax -> ax.plus(-1 * parseLong( cutMatcher.group(1))) }
    }

    val newMatcher = Pattern.compile("deal into new stack").matcher(line)
    if (newMatcher.matches()) {
        return { ax -> ax.times(-1).plus(deckSize-1) }
    }

    throw RuntimeException("Unparseable line $line")
}

fun toShuffleAInv(line: String, deckSize: Long): (axplusbmodm) -> axplusbmodm {
    val incMatcher = Pattern.compile("deal with increment ([0-9]+)").matcher(line)
    if (incMatcher.matches()) {
        return { ax -> ax.times( axplusbmodm(parseLong(incMatcher.group(1)), 0, deckSize).solveForX(1) ) }
    }

    val cutMatcher = Pattern.compile("cut (-?[0-9]+)").matcher(line)
    if (cutMatcher.matches()) {
        return { ax -> ax.plus(parseLong( cutMatcher.group(1))) }
    }

    val newMatcher = Pattern.compile("deal into new stack").matcher(line)
    if (newMatcher.matches()) {
        return { ax -> ax.times(-1).plus(deckSize-1) }
    }

    throw RuntimeException("Unparseable line $line")
}



fun newStackInv(afterPosition: Long, deckSize: Long): Long {
    return deckSize - afterPosition - 1

    //afterPosition = decksize-1 - beforePosition
    //afterPosition =
}

fun cutNInv(afterPosition: Long, cut: Long, deckSize: Long): Long {
    if (cut < 0) {
        return cutNInv(afterPosition, deckSize + cut, deckSize)
    }

    //if beforePosition < cut
    //afterPosition = beforePosition + (decksize - cut)
    //else
    //afterPosition = beforePosition - cut

    //afterPosition = (beforePosition - cut) % decksize
    //y = (x + b) % m

    //0 1 2 3 4 5   //cut 2
    //2 3 4 5 0 1   //


    //beforePosition = (afterPosition + Nm + cut) % m for some N

    if (afterPosition >= deckSize - cut) { //the card was cut to the back
        return afterPosition - (deckSize - cut)
    } else {
        return afterPosition + cut
    }
}

fun incrementNInv(afterPosition: Long, inc: Long, deckSize: Long): Long {
    //afterPosition = (beforePosition*increment) % deckSize
    //before = (after * inc') % deckSize
    //2 == (before * 3) % 10, before=4
    // before = (after + N*decksize)/increment
    var N = 0L
    while ( (afterPosition + N*deckSize) % inc != 0L) {
        N++
    }

    return (afterPosition + N*deckSize) / inc
}

data class axplusbmodm(val a: Long, val b: Long, val m: Long) {
    fun times(n: Long): axplusbmodm { return axplusbmodm(mod(a*n), mod(b*n), m) }
    fun plus(c: Long): axplusbmodm { return axplusbmodm(a, mod(b+c), m) }
    fun combine(ax: axplusbmodm): axplusbmodm { return ax.times(a).plus(b) }
    private fun mod(i: Long): Long {
        val j = i % m
        if (j < 0) {
            return j + m
        } else {
            return j
        }
    }

    fun solveForX(y: Long) : Long {
        //y = (ax + b) % m
        //y = ax + b + N*m for some N
        //x = (y - b - N*m )/a for some N
        //N = (y - ax - b)/m
        var N = 0L
        while ( (y - b + N*m) % a  != 0L) {
            N++
        }
        return mod((y - b + N*m) / a)
    }
    fun solveForY(x: Long) : Long {
        val l = (a * x + b) % m
        if (l < 0) {
            return l + m
        }
        return l
    }
}//N>1884211519518

