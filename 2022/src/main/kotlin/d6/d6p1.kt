package d6


import utils.*

fun main() {
    val lines = readList("/d6.txt")
    val data = lines[0].split("").filter{it != ""}

    //val packetLength = 4 //part 1
    val packetLength = 14  //part 2
    for (i in (0..data.size)) {
        val sub = data.subList(i, i+packetLength)
        if (sub.toSet().size == packetLength) {
            println(i+packetLength)
            break
        }
    }
}