package d2

import utils.*


fun toMove(them: String, me: String): String {
    if (them == "A" && me == "X") {
        return "Z"
    } else if (them == "A" && me == "Y") {
        return "X"
    } else if (them == "A" && me == "Z") {
        return "Y"
    } else if (them == "B" && me == "Y") {
        return "Y"
    } else if (them == "B" && me == "Z") {
        return "Z"
    } else if (them == "B" && me == "X") {
        return "X"
    } else if (them == "C" && me == "Y") {
        return "Z"
    } else if (them == "C" && me == "Z") {
        return "X"
    } else if (them == "C" && me == "X") {
        return "Y"
    }
    return "X"
}

fun main() {
    val lines = readList("/d2.txt")

    val split = lines.map{it.split(" ")}

    println(split.map{toScore(it[0], toMove(it[0], it[1]))}.sum())
}

