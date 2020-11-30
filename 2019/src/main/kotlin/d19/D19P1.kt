package d19

import intcode.runProgramFromFile

fun main() {
    val size = 400L
    val offset = 800L

    val output = (offset..(offset+size)).map{y -> (offset..(offset+size)).map{ listOf(it, y) } }.flatten()
        .map{
            val output = runProgramFromFile("2019/src/main/resources/d19/d19p1", it)
            output
        }

    output.chunked(size.toInt()+1).forEachIndexed{ y, line ->
        line.forEach{ print(it[0]) }
        println("")
    }
}
