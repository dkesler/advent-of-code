import java.io.File
import java.lang.Long.parseLong
import java.util.regex.Pattern

fun main() {
    val deckSize = 119315717514047L
    //val totalShuffles = 101741582076661L
    val totalShuffles = 1L

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
    println(ax)


/*
    val shuffled = (1..totalShuffles).fold(
        2020L,
        {acc, i -> moves.fold(acc, { acc2, op -> op(acc2) })}
    )
*/

    val out = (ax.a * 65523374817902 + ax.b) % deckSize
    println(out)

    //65523374817902
    //println(shuffled)
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
}




