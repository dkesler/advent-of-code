package d6

import utils.Grids
import utils.readList

fun main() {
    val lines = readList("/d6.txt").map {
        val sp = it.split(" " ).map{ it.trim() }.filter{it.isNotBlank()}
        sp
    }

    val transposed = Grids.transpose(lines)

    var total = 0L
    for (line in transposed) {
        val action = line.last()

        var sum = 0L
        if (action == "*") sum = 1L

        for (i in 0 until line.size-1) {
            if (action == "*") sum *= line[i].toLong()
            if (action == "+") sum += line[i].toLong()
        }
        total += sum
    }

    println(total)


}