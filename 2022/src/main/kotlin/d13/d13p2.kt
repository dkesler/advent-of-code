package d13

import com.google.gson.Gson
import utils.readList

fun main() {
    val lines = readList("/d13.txt")
            .toMutableList()

    lines.add("[[2]]")
    lines.add("[[6]]")

    val gson = Gson()
    val asJson = lines.map{gson.fromJson(it, List::class.java) }

    val sorted = asJson.sortedWith { a, b ->
        val res = inOrder(a, b)
        when (res) {
            Order.IN_ORDER -> -1
            Order.OUT_OF_ORDER -> 1
            else -> 0
        }
    }

    val idx2 = sorted.indexOf(gson.fromJson("[[2]]", List::class.java))
    val idx6 = sorted.indexOf(gson.fromJson("[[6]]", List::class.java))
    println((idx2+1) * (idx6+1))



}