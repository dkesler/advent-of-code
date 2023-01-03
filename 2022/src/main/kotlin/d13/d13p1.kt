package d13

import com.google.gson.Gson
import utils.readBlocks

enum class Order {
    IN_ORDER,
    OUT_OF_ORDER,
    EVEN
}

fun get(p1: List<*>, idx: Int): List<*> {
    if (p1[idx] is List<*>) return p1[idx] as List<*>
    return listOf(p1[idx] as Double)
}

fun inOrder(p1: List<*>, p2: List<*>): Order {
    for (i in p1.indices) {
        if (i !in p2.indices) {
            return Order.OUT_OF_ORDER
        }

        if (p1[i] is Double && p2[i] is Double) {
            val p1d = p1[i] as Double
            val p2d = p2[i] as Double
            if (p1d < p2d) return Order.IN_ORDER
            if (p1d > p2d) return Order.OUT_OF_ORDER
        } else {
            val p1i = get(p1, i)
            val p2i = get(p2, i)

            val sub = inOrder(p1i, p2i)
            if (sub != Order.EVEN) return sub
        }
    }

    if (p1.size == p2.size) return Order.EVEN

    return Order.IN_ORDER
}

fun main() {
    val blocks = readBlocks("/d13.txt")

    var result = 0
    val gson = Gson()
    for (idx in blocks.indices) {

        val p1 = gson.fromJson(blocks[idx][0], List::class.java)
        val p2 = gson.fromJson(blocks[idx][1], List::class.java)
        if (inOrder(p1, p2) == Order.IN_ORDER) {
            result += (idx + 1)
        }
    }

    println(result)

}