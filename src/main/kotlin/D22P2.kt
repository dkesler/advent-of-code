import java.io.File
import java.lang.Long.parseLong
import java.math.BigInteger
import java.util.regex.Pattern

fun main() {

    //val deckSize = 119315717514047L
    //val totalShuffles = 101741582076661L
    val deckSize = 10007L //2019->6061->5233
    val totalShuffles = 1L
    //val deckSize = 10L
    //val totalShuffles = 1L

/*
    val moves = File("src/main/resources/d22p1").readLines()
        .map{toShuffleAInv(it, deckSize)}
*/
    /*
    0 1 2 3 4 5 6 //inc 2
    0 4 1 5 2 6 3 //inc 4
    0 1 . . 4 5 .

    0 1 2 3 4 5 6 //inc 3
    0 5 3 1 6 4 2 //inc 5
    0 . . 3 . 5 .

    0 1 2 3 4 5 6 //inc 4
    0 2 4 6 1 3 5 //inc 2

    0 1 2 3 4 5 6 //inc 6
    0 6 5 4 3 2 1 //inc 6
    0 . . . . 5 6


    N*inc % m == 1
    0 1 2 3 4 //inc 2
    0 3 1 4 2 //inc 3
    0 1 . 3 .



    0 1 2 3 4 5 6 7 8 9 //inc 3
    0 7 4 1 8 5 2 9 6 3 //inc 7
    0 . . . 4 . . 7 . .

     */


    val moves = listOf("deal with increment 53")
        .map{toShuffleA(it, deckSize)}

    val movesRev = listOf("deal with increment 53")
        .map{toShuffleAInv(it, deckSize)}


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
/*
    for (x in (0..deckSize)) {
        val y = totalAx.solveForY(x)
        if (y == 2020L) {
            println(x)
            break
        }
        printWithTiming(x)
    }
*/

//1x -          91309475543446
//2x -          63303233572845
//4x -           7290749631643
//68x -          4627026223884
//1768 -          986964306937   //121
//213928 -        106963625330 //1116
//238743648 -      55688354233 //2142
//511627637664 -   24425607272    //4885
//112986659707700 - 3374009673

/*    println(totalAx.solveForY(0))
    println(totalAx.solveForY(1))
    println(totalAx.solveForY(2))
    println(totalAx.solveForY(4))
    println(totalAx.solveForY(68))
    println(totalAx.solveForY(1768))
    println(totalAx.solveForY(213928L))
    println(totalAx.solveForY(238743648))
    println(totalAx.solveForY(511627637664))
    println(totalAx.solveForY(112986659707700))*/
    //println(totalAx.solveForX(2020))
    println(totalAx.solveForY(6061))
    println(totalAx.solveForX(2019))


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
    if (i % 100000 == 0L) {
        println("iteration: $i.  Previous 100000 iterations took ${currentTime - lastTiming} ms")
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

fun toShuffleAInv(line: String, deckSize: Long): (axplusbmodm) -> axplusbmodm {
    val incMatcher = Pattern.compile("deal with increment ([0-9]+)").matcher(line)
    if (incMatcher.matches()) {
        return { ax -> ax.times(deckSize - parseLong(incMatcher.group(1)) - 1) }
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
        while ( (y - b - N*m) % a  != 0L) {
            N++
        }
        return mod((y - b - N*m) / a)
    }
    fun solveForY(x: Long) : Long {
        val l = (a * x + b) % m
        if (l < 0) {
            return l + m
        }
        return l
    }
}//N>1884211519518

