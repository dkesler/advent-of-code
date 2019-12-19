import java.io.File
import java.util.stream.Collectors

fun toFuel(mass: Int): Int {
    return (mass / 3) - 2
}

fun main() {
    val fuel = File("src/main/resources/d1p1").readLines()
        .map { Integer.parseInt(it) }
        .map(::toFuel)
        .sum()

    println(fuel)
}
