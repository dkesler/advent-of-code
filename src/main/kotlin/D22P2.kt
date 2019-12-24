import java.io.File
import java.lang.Long.parseLong
import java.math.BigInteger
import java.util.regex.Pattern

fun main() {

    val deckSize = 119315717514047L
    val totalShuffles = 101741582076661L
    //val deckSize = 10007L //2019->6061->5233
    //val totalShuffles = 1L

    val moves = File("src/main/resources/d22p1").readLines()
        .map{toShuffleA(it, deckSize)}

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
    val axes = mutableListOf(ax)
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
    println(totalAx.solveForX(2020))


/*
    val shuffled = (1..totalShuffles).fold(
        2020L,
        {acc, i -> moves.fold(acc, { acc2, op -> op(acc2) })}
    )
*/

}

var lastTiming = System.currentTimeMillis()
fun printWithTiming(i: Long) {
    val currentTime = System.currentTimeMillis()
    if (i % 10000 == 0L) {
        println("iteration: $i.  Previous 10000 iterations took ${currentTime - lastTiming} ms")
        lastTiming = currentTime
    }
}

fun toShuffleInv(line: String, deckSize: Long): (Long) -> Long {
    val incMatcher = Pattern.compile("deal with increment ([0-9]+)").matcher(line)
    if (incMatcher.matches()) {
        return { incrementNInv(it, java.lang.Long.parseLong(incMatcher.group(1)), deckSize)  }
    }

    val cutMatcher = Pattern.compile("cut (-?[0-9]+)").matcher(line)
    if (cutMatcher.matches()) {
        return { cutNInv(it, java.lang.Long.parseLong(cutMatcher.group(1)), deckSize) }
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

    if (afterPosition >= deckSize - cut) { //the card was cut to the back
        return afterPosition - (deckSize - cut)
    } else {
        return afterPosition + cut
    }
}

fun incrementNInv(afterPosition: Long, inc: Long, deckSize: Long): Long {
    //afterPosition = (beforePosition*increment) % deckSize
    //2 == (before * 3) % 10, before=4
    // before = (after + N*decksize)/increment
    var N = 0L
    while ( (afterPosition + N*deckSize) % inc != 0L) {
        N++
    }

    return (afterPosition + N*deckSize) / inc
}

data class axplusbmodm(val a: Long, val b: Long, val m: Long) {
    fun times(n: Long): axplusbmodm { return axplusbmodm((a*n) % m, (b*n) % m, m) }
    fun plus(c: Long): axplusbmodm { return axplusbmodm(a, (b+c) % m, m) }
    fun combine(ax: axplusbmodm): axplusbmodm { return ax.times(a).plus(b) }
    fun solveForX(y: Long) : Long {
        //y = (ax + b) % m
        //x = (y - b + N*m )/a for some N
        var N = 0L
        while ( (y - b + N*m) % a  != 0L) {
            N++
        }
        return (y - b + N*m) / a
    }
}//N>1884211519518

//1x -        91309475543446
//2x -        63303233572845
//4x -         7290749631643
//68x -        4627026223884
//1768 -        986964306937   //121
//213928 -      106963625330 //1116
//238743648 -    55688354233 //2142
//511627637664 - 24425607272    //4885
