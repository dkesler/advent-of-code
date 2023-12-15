package d8

import utils.*

data class Node (val self: String, val l: String, val r: String) {
    fun get(c: Char): String {
        return if (c == 'L') l else r
    }

}

fun main() {
    val movement = "LRLRRRLRLLRRLRLRRRLRLRRLRRLLRLRRLRRLRRRLRRRLRLRRRLRLRRLRRLLRLRLLLLLRLRLRRLLRRRLLLRLLLRRLLLLLRLLLRLRRLRRLRRRLRRRLRRLRRLRRRLRLRLRRLRLRLRLRRLRRRLLRLLRRLRLRRRLRLRRRLRLRRRLRRRLRRLRLLLLRLRRRLRLRRLRLRRLRRLRRLLRRRLLLLLLRLRRRLRRLLRRRLRRLLLRLRLRLRRRLRRLRLRRRLRRLRRRLLRRLRRLLLRRRR"

    val lines = readList("/d8.txt")
    val nodes = lines.map{
        val s = it.split("= " ).filter{it != ""}
        val s2 = s[1].split(",").filter{it != ""}.map{ it.replace("(", "").replace(")", "")}
        Node(s[0].trim(), s2[0].trim(), s2[1].trim())
    }.associateBy{ it.self }

    var current = nodes["AAA"]!!
    var moveCt = 0
    while(current.self != "ZZZ") {
        val mvIdx = moveCt % movement.length
        current = nodes[current.get(movement[mvIdx])]!!
        moveCt ++
    }
    println(moveCt)



}