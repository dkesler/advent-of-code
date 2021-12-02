package d2

import utils.readList
import utils.readLongList

fun main() {
    val list = readList("/d2/d2p1")
    val splitList = list.map{it.split(" ")}

    var hpos = 0
    var vpos = 0

    for (l in splitList) {
        if (l[0] == "forward") hpos += l[1].toInt()
        if (l[0] == "down") vpos += l[1].toInt()
        if (l[0] == "up") vpos -= l[1].toInt()
    }
    println(hpos*vpos)


}

