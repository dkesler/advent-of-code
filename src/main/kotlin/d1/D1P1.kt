package d1

import java.io.File


fun toFuel(mass: Int): Int {
    return (mass / 3) - 2
}

fun main() {
    val fuel = File("src/main/resources/d1/d1p1").readLines()
        .map { Integer.parseInt(it) }
        .map(::toFuel)
        .sum()

    println(fuel)
}
