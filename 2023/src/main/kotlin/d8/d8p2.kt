package d8

import utils.*


fun main() {
    val movement = "LRLRRRLRLLRRLRLRRRLRLRRLRRLLRLRRLRRLRRRLRRRLRLRRRLRLRRLRRLLRLRLLLLLRLRLRRLLRRRLLLRLLLRRLLLLLRLLLRLRRLRRLRRRLRRRLRRLRRLRRRLRLRLRRLRLRLRLRRLRRRLLRLLRRLRLRRRLRLRRRLRLRRRLRRRLRRLRLLLLRLRRRLRLRRLRLRRLRRLRRLLRRRLLLLLLRLRRRLRRLLRRRLRRLLLRLRLRLRRRLRRLRLRRRLRRLRRRLLRRLRRLLLRRRR"
    //val movement = "LR"

    val lines = readList("/d8.txt")
    val nodes = lines.map{
        val s = it.split("= " ).filter{it != ""}
        val s2 = s[1].split(",").filter{it != ""}.map{ it.replace("(", "").replace(")", "")}
        Node(s[0].trim(), s2[0].trim(), s2[1].trim())
    }.associateBy{ it.self }


    var starts = nodes.keys.filter{ it.endsWith("A")}.map{nodes[it]!! }
    for (start in starts) {
        val visited = mutableSetOf(Pair(start, 0))
        var moveCt = 0
        var c = start
        do {

            val mvIdx = moveCt % movement.length
            val move = movement[mvIdx]
            visited.add(Pair(c, mvIdx))
            c = nodes[c.get(move)]!!
            moveCt++

            //this printed 1 value per start, meaning each start only ever finds a single end node before it cycles
            //I then took each value for the 6 starts and found the least common mulitple via wolfram alpha to figure
            //out when they would converge.
            //I don't think this actually should have worked on a generic problem.  It only worked because the number
            //of moves to get from the start node to the end node the first time happens to equal the time to cycle from
            //the end node back to the end node, which seems to be a quirk of the test input construction
            if (c.self.endsWith("Z")) {
                println(moveCt)
            }
        } while (Pair(c, moveCt % movement.length) !in visited)
    }
}