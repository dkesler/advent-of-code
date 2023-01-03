package d7

import utils.*

fun main() {
    val lines = readList("/d7.txt")

    val files = mutableMapOf<String, Long>()
    var curDir = mutableListOf("")
    val dirs = mutableSetOf("/")

    for (line in lines) {
        if (line == "$ cd /") {
            curDir = mutableListOf("")
        } else if (line == "$ cd ..") {
            curDir.removeLast()
        } else if (line.startsWith("$ cd")) {
            val newDir = line.split(" ")[2]
            curDir.add(newDir)
            dirs.add(curDir.joinToString("/"))
        } else if (line != "$ ls" && !line.startsWith("dir ")) {
            val split = line.split(" ")
            files[curDir.joinToString("/") + split[1]] = split[0].toLong()
        }
    }

    val totalUsed = files.values.sum()
    val free = 70000000 - totalUsed
    val toDelete = 30000000 - free

    println(
    dirs.map { dir ->
        files.filter{it.key.startsWith(dir)}.values.sum()
    }.filter{it >= toDelete }
            .min()
    )


}