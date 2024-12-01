package d1
import utils.*

fun main() {
    val lines = readList("/d1.txt").filter{ it.isNotBlank()}.map{ it.split(" ").filter{it.isNotBlank()}.map{ (it.trim().toLong())} }
    val x = lines.map{ it[0] }.sorted()
    val y = lines.map{ it[1] }.sorted()

    println(x.map{ xit -> y.count { it == xit } * xit}.sum())
}