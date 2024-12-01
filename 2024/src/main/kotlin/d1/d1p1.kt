package d1
import utils.*
import kotlin.math.abs

fun main() {
    val lines = readList("/d1.txt").map{ it.split(" ").filter{it.isNotBlank()}.map{ (it.trim().toLong())} }
    val x = lines.map{ it[0] }.sorted()
    val y = lines.map{ it[1] }.sorted()

    var sum = 0L
    for (i in x.indices) {
        sum += abs(x[i] - y[i])
    }
    println(sum)



}