package d22

import utils.*

fun main() {
    val lines = readLongList("/d22.txt")

    var secretNumbers = lines
    for (iter in 1..2000) {
        val newNumbers = secretNumbers.map{evolve(it)}
        secretNumbers = newNumbers
    }

    println(secretNumbers.sum())
    println(secretNumbers)

}

fun evolve(it: Long): Long {
    val a = (it * 64).xor(it) % 16777216
    val b = (a / 32).xor(a) % 16777216
    val c = (b * 2048).xor(b) % 16777216
    return c
}
