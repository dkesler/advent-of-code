package d24

import utils.*

fun main() {
    val list = readList("/d24/d24p1").map{
        it.split(" ")
    }

    val res = alu2(list, mapOf(Pair("x", 0), Pair("y", 0), Pair("z", 0), Pair("w", 0)), listOf(), Long.MAX_VALUE)
    println(res.first)
    println(res.second.sortedDescending().first())
}

fun extractSecond(s: String, vars: Map<String, Long>): Long {
    if (s in setOf("w", "x", "y", "z")) {
        return vars[s]!!
    } else {
        return s.toLong()
    }
}

fun alu2(list: List<List<String>>, initialVars: Map<String, Long>, inputsSoFar: List<Long>, bestZ: Long): Pair<Long, List<Long>>  {
    val vars = initialVars.toMutableMap()
    var newBestZ = bestZ

    //the only way z ever decreases is by divisions by 26.  if we ever reach a point where
    //every possible division by 26 still can't get the current z below the best z we've
    //seen so far, there's no point in continuing down this path
    val zDivsBy26Left = zDivsBy26Remaining(list)
    val bestPossibleZ = calcBestPossibleZ(zDivsBy26Left, vars["z"]!!)
    if (bestPossibleZ > bestZ ) {
        return Pair(Long.MAX_VALUE, listOf())
    }

    list.indices.map { idx ->
        val cmd = list[idx]
        when(cmd[0]) {
            "inp" -> {
                if (vars["x"]!! >= 1 && vars["x"]!! <= 9) {
                    vars["w"] = vars["x"]!!
                    return alu2(list.subList(idx+1, list.size), vars.toMap(), inputsSoFar + vars["x"]!!, newBestZ)
                } else {
                    //for (i in (9L downTo 1)) { //part 1
                    for (i in (1..9L)) { //part 2
                        vars[cmd[1]] = i
                        val res = alu2(list.subList(idx+1, list.size), vars.toMap(), inputsSoFar + i, newBestZ)
                        if (res.second.isNotEmpty()) {
                            return res
                        }
                        if (res.first < newBestZ) newBestZ = res.first
                    }
                    return Pair(newBestZ, listOf())
                }
            }
            "add" -> vars[cmd[1]] = vars[cmd[1]]!! + extractSecond(cmd[2], vars)
            "mul" -> vars[cmd[1]] = vars[cmd[1]]!! * extractSecond(cmd[2], vars)
            "div" -> vars[cmd[1]] = vars[cmd[1]]!! / extractSecond(cmd[2], vars)
            "mod" -> vars[cmd[1]] = vars[cmd[1]]!! % extractSecond(cmd[2], vars)
            "eql" -> if (vars[cmd[1]] == extractSecond(cmd[2], vars)) vars[cmd[1]] = 1 else vars[cmd[1]] = 0
        }
    }

    if (vars["z"] == 0L) {
        return Pair(0, listOf(String(inputsSoFar.map{it.toString()[0]}.toCharArray()).toLong()))
    } else {
        return Pair(vars["z"]!!, listOf())
    }
}

//need to iterate the divisions because each one is truncated.
private fun calcBestPossibleZ(zDivsBy26Left: Int, z: Long): Long {
    var newZ = z
    for (i in 1..zDivsBy26Left) {
        newZ /= 26
    }
    return newZ
}

fun zDivsBy26Remaining(list: List<List<String>>): Int {
    return list.filter{it == listOf("div", "z", "26")}.size
}




