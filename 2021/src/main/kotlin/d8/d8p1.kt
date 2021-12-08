package d8
import utils.*

fun main() {
    val list = readList("/d8/d8p1").map{it.split("|")}.map{
        Pair(
            it[0].trim().split(" ").map{ it.trim().split("").filter{it != ""}.toSet()},
            it[1].trim().split(" ").map{ it.trim().split("").filter{it != ""}.toSet()}
        )
    }

    val filtered = list.map { it.second }.map { it.filter { it.size in listOf(2, 3, 4, 7) } }
    println(filtered.flatten().count())



}
