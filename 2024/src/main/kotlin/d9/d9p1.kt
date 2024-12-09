package d9

import utils.*

fun main() {
    val line = readList("/d9.txt")[0]

    val mem = mutableListOf<String>()
    var blockIdx = 0
    for (i  in line.indices) {
        if (i % 2 == 0) {
            (0 until line[i].toString().toInt()).forEach{ mem.add(blockIdx.toString()) }
            blockIdx++
        } else {
            (0 until line[i].toString().toInt()).forEach{ mem.add(".") }
        }
    }

    //val emptyPointer = mem.indexOf(".")
    for (i in mem.indices.reversed()) {
        if (mem[i] != ".")  {
            val emptyPointer = mem.indexOf(".")
            mem[emptyPointer] = mem[i]
            mem[i] = "."
        }
    }

    println(
        mem.filter{ it != "." }.map{ it.toLong() }.mapIndexed{ i, id -> id * i  }.sum()
    )



}