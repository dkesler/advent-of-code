package d1

import utils.*

fun main() {
    val lines = readList("/d1.txt")
    println(
        lines.sumBy{
            val s = it.split("")
            var x:Int? = null
            var last: Int? = null
            for (c in s) {
                if (x == null && c.toIntOrNull() != null) x = c.toInt()
                if (c.toIntOrNull() != null) last = c.toInt()
            }
            x!!*10  + last!!
        }
    )
}