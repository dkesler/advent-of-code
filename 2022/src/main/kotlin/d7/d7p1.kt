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

    println(files)
    println(dirs)

    var total = 0L
    for (dir in dirs) {
        val dirSize = files.filter{it.key.startsWith(dir)}.values.sum()
        if (dirSize <= 100000) {
            total += dirSize
        }
    }
    println(total)


}