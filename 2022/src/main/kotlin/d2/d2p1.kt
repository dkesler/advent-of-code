package d2

import utils.*

fun toScore(them: String, me: String): Long {
    if (them == "A" && me == "X") {
        return 1 + 3
    } else if (them == "A" && me == "Y") {
        return 2 + 6
    } else if (them == "A" && me == "Z") {
        return 3 + 0
    } else if (them == "B" && me == "Y") {
        return 2 + 3
    } else if (them == "B" && me == "Z") {
        return 3 + 6
    } else if (them == "B" && me == "X") {
        return 1 + 0
    } else if (them == "C" && me == "Y") {
        return 2 + 0
    } else if (them == "C" && me == "Z") {
        return 3 + 3
    } else if (them == "C" && me == "X") {
        return 1 + 6
    }
return 0
}
fun main() {
    val lines = readList("/d2.txt")

    val split = lines.map{it.split(" ")}

    println(split.map{toScore(it[0], it[1])}.sum())
}

