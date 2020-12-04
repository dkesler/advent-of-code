package d3

fun toTreeList(lines: List<String>): List<List<Boolean>> {
    return lines.map{ line ->
        line.toCharArray().map{ it == '#' }.toList()
    }
}

fun main() {
    val content = Class::class.java.getResource("/d3/d3p1").readText()
    val list = content.split("\n").filter{it != ""}

    val treeList = toTreeList(list)

    val slopes: List<Pair<Int, Int>> = listOf(
        Pair(1, 1),
        Pair(1, 3),
        Pair(1, 5),
        Pair(1, 7),
        Pair(2, 1)
    )

    var treeMult = 1L
    for (slope in slopes) {
        var treeCount = 0
        for (row in 0 until treeList.size step slope.first) {
            val column = (row / slope.first * slope.second) % treeList[row].size
            if (treeList[row][column]) treeCount++
        }
        treeMult *= treeCount
        println(slope)
        println(treeCount)
    }
    println(treeMult);
}
