package d4

fun main() {
    val range = 246540..787419

    println(range.filter(::isValid).count())

}

fun isValid(d: Int): Boolean {
    val digits = (0..5).map { ithDigit(d, it) }

//    if (!hasDouble(digits)) {
    if (!hasDoubleThatIsNotATriple(digits)) {
        return false
    }

    if (!isNotDecreasing(digits)) {
        return false
    }

    return true
}

fun isNotDecreasing(digits: List<Int>): Boolean {
    return (0..4).all { digits[it] <= digits[it+1] }
}

fun hasDouble(digits: List<Int>): Boolean {
    return (0..4).any { digits[it] == digits[it+1] }
}

fun hasDoubleThatIsNotATriple(digits: List<Int>): Boolean {
    return (0..4).any { digits[it] == digits[it+1] && (it == 0 || digits[it] != digits[it-1]) && (it == 4 || digits[it+1] != digits[it+2])  }
}


fun ithDigit(d: Int, i: Int): Int {
    return Integer.parseInt(d.toString()[i].toString())
}
