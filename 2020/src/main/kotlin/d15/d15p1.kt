package d15

fun main() {
    val content = Class::class.java.getResource("/d15/d15p1").readText()
    val list = content.split("\n").filter{it != ""}[0].split(",").map{it.toLong()}

    val lastSpokenOn = mutableMapOf<Long, Long>()

    list.mapIndexed{ i, num -> lastSpokenOn.put(num, i.toLong()) }

    var lastNum = 0L
    for (turn in list.size until 30000000L -1) {
        if (lastSpokenOn.containsKey(lastNum)) {
            val lastNumSpokenOn = lastSpokenOn.getValue(lastNum)
            val interval = turn - lastNumSpokenOn
            lastSpokenOn[lastNum] = turn
            lastNum = interval
        } else {
            lastSpokenOn[lastNum] = turn
            lastNum = 0
        }

    }
    println(lastNum)






}
