package d6

import utils.Grids
import utils.readCharGrid
import utils.readGrid
import utils.readList

fun main() {
    val lines = readList2("/d6.txt").map{ it.toCharArray().toList() }

    val transposed = Grids.transpose(lines)

    var total = 0L

    val blocks = mutableListOf<List<List<Char>>>()
    var cur = mutableListOf<List<Char>>(transposed[0])
    for (line in transposed.subList(1, transposed.size)) {
        if (line.all{ it == ' ' }) {
            blocks.add(cur.toList())
            cur = mutableListOf()
        } else {
            cur.add(line)
        }
    }
    blocks.add(cur.toList())

    for (block in blocks) {
        val action = block[0].last()
        var sum = 0L
        if (action == '*') sum = 1L
        for (line in block) {
            val str = line.joinToString("").replace("*", "").replace("+", "").replace("_", "").trim()
            if (str.isNotBlank()) {
                val value = str.toLong()
                if (action == '*') sum *= value else sum += value
            }
        }
        total += sum
    }



    println(total)


}

fun readList2(filename: String): List<String> {
    val content = {}.javaClass.getResource(filename)!!.readText()
    return content.split("\n").filter{it != ""}
}