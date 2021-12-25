package d23


data class Move(val steps: Int, val needsFree: Set<Int>, val dest: Int)
fun main() {
    val moveList = mapOf(
        Pair(0, listOf(
            Move(3, setOf(1, 7), 7),
            Move(4, setOf(1, 7, 8), 8),
            Move(5, setOf(1, 2, 9), 9),
            Move(6, setOf(1, 2, 9, 10), 10),
            Move(7, setOf(1, 2, 3, 11), 11),
            Move(8, setOf(1, 3, 2, 11, 12), 12),
            Move(9, setOf(1, 2, 3, 4, 13), 13),
            Move(10, setOf(1, 2, 3, 4, 13, 14), 14)
        )),
        Pair(1, listOf(
            Move(2, setOf(7), 7),
            Move(3, setOf(7, 8), 8),
            Move(4, setOf(2, 9), 9),
            Move(5, setOf(2, 9, 10), 10),
            Move(6, setOf(2, 3, 11), 11),
            Move(7, setOf(3, 2, 11, 12), 12),
            Move(8, setOf(2, 3, 4, 13), 13),
            Move(9, setOf(2, 3, 4, 13, 14), 14)
        )),
        Pair(2, listOf(
            Move(2, setOf(7), 7),
            Move(3, setOf(7, 8), 8),
            Move(2, setOf(9), 9),
            Move(3, setOf(9, 10), 10),
            Move(4, setOf(3, 11), 11),
            Move(5, setOf(3, 11, 12), 12),
            Move(6, setOf(3, 4, 13), 13),
            Move(7, setOf(3, 4, 13, 14), 14)
        )),
        Pair(3, listOf(
            Move(4, setOf(2, 7), 7),
            Move(5, setOf(2, 7, 8), 8),
            Move(2, setOf(9), 9),
            Move(3, setOf(9, 10), 10),
            Move(2, setOf(11), 11),
            Move(3, setOf(11, 12), 12),
            Move(4, setOf(4, 13), 13),
            Move(5, setOf(4, 13, 14), 14)
        )),
        Pair(4, listOf(
            Move(6, setOf(2, 3,  7), 7),
            Move(7, setOf(2, 3, 7, 8), 8),
            Move(4, setOf(3, 9), 9),
            Move(5, setOf(3, 9, 10), 10),
            Move(2, setOf(11), 11),
            Move(3, setOf(11, 12), 12),
            Move(2, setOf(13), 13),
            Move(3, setOf(13, 14), 14)
        )),
        Pair(5, listOf(
            Move(8, setOf(2, 3, 4, 7), 7),
            Move(9, setOf(2, 3, 4, 7, 8), 8),
            Move(6, setOf(3, 4, 9), 9),
            Move(7, setOf(3, 4, 9, 10), 10),
            Move(4, setOf(4, 11), 11),
            Move(5, setOf(4, 11, 12), 12),
            Move(2, setOf(13), 13),
            Move(3, setOf(13, 14), 14)
        )),
        Pair(6, listOf(
            Move(9, setOf(2, 3, 4, 5,  7), 7),
            Move(10, setOf(2, 3, 4, 5, 7, 8), 8),
            Move(7, setOf(3, 4, 5, 9), 9),
            Move(8, setOf(3, 4, 5, 9, 10), 10),
            Move(5, setOf(4, 5, 11), 11),
            Move(6, setOf(4, 5, 11, 12), 12),
            Move(3, setOf(5, 13), 13),
            Move(4, setOf(5, 13, 14), 14)
        )),
        Pair(7, listOf(
            Move(3, setOf(0, 1), 0),
            Move(2, setOf(1), 1),
            Move(2, setOf(2), 2),
            Move(4, setOf(2, 3), 3),
            Move(6, setOf(2, 3, 4), 4),
            Move(8, setOf(2, 3, 4, 5), 5),
            Move(9, setOf(2, 3, 4, 5, 6), 6),
            Move(4, setOf(2, 9), 9),
            Move(5, setOf(2, 9, 10), 10),
            Move(6, setOf(2, 3, 11), 11),
            Move(7, setOf(2, 3, 11, 12), 12),
            Move(8, setOf(2, 3, 4, 13), 13),
            Move(9, setOf(2, 3, 4, 13, 14), 14)
        )),
        Pair(8, listOf(
            Move(4, setOf(0, 1, 7), 0),
            Move(3, setOf(1, 7), 1),
            Move(3, setOf(2, 7), 2),
            Move(5, setOf(2, 3, 7), 3),
            Move(7, setOf(2, 3, 4, 7), 4),
            Move(9, setOf(2, 3, 4, 5, 7), 5),
            Move(10, setOf(2, 3, 4, 5, 6, 7), 6),
            Move(5, setOf(2, 9, 7), 9),
            Move(6, setOf(2, 9, 10, 7), 10),
            Move(7, setOf(2, 3, 11, 7), 11),
            Move(8, setOf(2, 3, 11, 12, 7), 12),
            Move(9, setOf(2, 3, 4, 13, 7), 13),
            Move(10, setOf(2, 3, 4, 13, 14, 7), 14)
        )),
        Pair(9, listOf(
            Move(5, setOf(0, 1, 2), 0),
            Move(4, setOf(1, 2), 1),
            Move(2, setOf(2), 2),
            Move(2, setOf(3), 3),
            Move(4, setOf(3, 4), 4),
            Move(6, setOf(3, 4, 5), 5),
            Move(7, setOf(3, 4, 5, 6), 6),
            Move(4, setOf(2, 7), 7),
            Move(5, setOf(2, 7, 8), 8),
            Move(4, setOf(3, 11), 11),
            Move(5, setOf(3, 11, 12), 12),
            Move(6, setOf(3, 4, 13), 13),
            Move(7, setOf(3, 4, 13, 14), 14)
        )),
        Pair(10, listOf(
            Move(6, setOf(0, 1, 2, 9), 0),
            Move(5, setOf(1, 2, 9), 1),
            Move(3, setOf(2, 9), 2),
            Move(3, setOf(3, 9), 3),
            Move(5, setOf(3, 4, 9), 4),
            Move(7, setOf(3, 4, 5, 9), 5),
            Move(8, setOf(3, 4, 5, 6, 9), 6),
            Move(5, setOf(2, 7, 9), 7),
            Move(6, setOf(2, 7, 8, 9), 8),
            Move(5, setOf(3, 11, 9), 11),
            Move(6, setOf(3, 11, 12, 9), 12),
            Move(7, setOf(3, 4, 13, 9), 13),
            Move(8, setOf(3, 4, 13, 14, 9), 14)
        )),
        Pair(11, listOf(
            Move(7, setOf(0, 1, 2, 3), 0),
            Move(6, setOf(1, 2, 3), 1),
            Move(4, setOf(2, 3), 2),
            Move(2, setOf(3), 3),
            Move(2, setOf(4), 4),
            Move(4, setOf(4, 5), 5),
            Move(5, setOf(4, 5, 6), 6),
            Move(6, setOf(2, 3, 7), 7),
            Move(7, setOf(2, 3, 7, 8), 8),
            Move(4, setOf(3, 9), 9),
            Move(5, setOf(3, 9, 10), 10),
            Move(4, setOf(4, 13), 13),
            Move(5, setOf(4, 13, 14), 14)
        )),
        Pair(12, listOf(
            Move(8, setOf(0, 1, 2, 3, 11), 0),
            Move(7, setOf(1, 2, 3, 11), 1),
            Move(5, setOf(2, 3, 11), 2),
            Move(3, setOf(3, 11), 3),
            Move(3, setOf(4, 11), 4),
            Move(5, setOf(4, 5, 11), 5),
            Move(6, setOf(4, 5, 6, 11), 6),
            Move(7, setOf(2, 3, 7, 11), 7),
            Move(8, setOf(2, 3, 7, 8, 11), 8),
            Move(5, setOf(3, 9, 11), 9),
            Move(6, setOf(3, 9, 10, 11), 10),
            Move(5, setOf(4, 13, 11), 13),
            Move(6, setOf(4, 13, 14, 11), 14)
        )),
        Pair(13, listOf(
            Move(9, setOf(0, 1, 2, 3, 4), 0),
            Move(8, setOf(1, 2, 3, 4), 1),
            Move(6, setOf(2, 3, 4), 2),
            Move(4, setOf(3, 4), 3),
            Move(2, setOf(4), 4),
            Move(2, setOf(5), 5),
            Move(3, setOf(5, 6), 6),
            Move(8, setOf(2, 3, 4, 7), 7),
            Move(9, setOf(2, 3, 4, 7, 8), 8),
            Move(6, setOf(3, 4, 9), 9),
            Move(7, setOf(3, 4, 9, 10), 10),
            Move(4, setOf(4, 11), 11),
            Move(5, setOf(4, 11, 12), 12)
        )),
        Pair(14, listOf(
            Move(10, setOf(0, 1, 2, 3, 4, 13), 0),
            Move(9, setOf(1, 2, 3, 4, 13), 1),
            Move(7, setOf(2, 3, 4, 13), 2),
            Move(5, setOf(3, 4, 13), 3),
            Move(3, setOf(4, 13), 4),
            Move(3, setOf(5, 13), 5),
            Move(4, setOf(5, 6, 13), 6),
            Move(9, setOf(2, 3, 4, 7, 13), 7),
            Move(10, setOf(2, 3, 4, 7, 8, 13), 8),
            Move(7, setOf(3, 4, 9, 13), 9),
            Move(8, setOf(3, 4, 9, 10, 13), 10),
            Move(5, setOf(4, 11, 13), 11),
            Move(6, setOf(4, 11, 12, 13), 12)
        )),
    )

    val locs = mutableMapOf(
        Pair(7, "D"),
        Pair(8, "C"),
        Pair(9, "A"),
        Pair(10, "A"),
        Pair(11, "D"),
        Pair(12, "B"),
        Pair(13, "C"),
        Pair(14, "B")
    )

    val res = solve(locs, moveList)
    println(res)



/*
#############
#01.2. 3. 4. 56#
###7#9 #11#13###
  #8#10#12#14#
  #########*/
}

fun solve(locs: Map<Int, String>, moveList: Map<Int, List<Move>>): Long {
    val energyMap = mapOf(
        Pair("A", 1L),
        Pair("B", 10),
        Pair("C", 100),
        Pair("D", 1000),
    )
    val memoTable = mutableMapOf<Map<Int, String>, Long>()
    var tableHits = 0
    val deadLocs = mutableSetOf<Map<Int, String>>()
    fun solveIter(locs: Map<Int, String>, energy: Long): Long {
        //find all pods we might want to move
        //for each one, find all valid moves
        //do that move, then call solve
        //choose option with min energy

        if (memoTable.containsKey(locs)) {
            tableHits++
            return memoTable[locs]!! + energy
        }

        if (deadLocs.contains(locs)) {
            return Long.MAX_VALUE
        }

        val podsWantingToMove = locs.entries.filter { !inFinalSpot(it, locs) }

        if (podsWantingToMove.isEmpty()) {
            return energy
        }

        val moves = podsWantingToMove.map { pod ->
            val possibleMoves = moveList[pod.key]!!.filter { move ->
                val necessarySpotsFree = move.needsFree.intersect(locs.keys).isEmpty()
                val mayTakeRoom = mayTakeRoom(pod.value, move.dest, locs)
                mayTakeRoom && necessarySpotsFree
            }

            possibleMoves.map { move ->
                solveIter(
                    locs.filter { it.key != pod.key }.plus(Pair(move.dest, pod.value)),
                    energy + move.steps * energyMap[pod.value]!!
                )
            }

        }.flatten().filter{it != Long.MAX_VALUE}

        if (moves.isNotEmpty()) {
            memoTable[locs] = moves.minOrNull()!! - energy
        } else {
            deadLocs += locs
        }

        return if (moves.isEmpty()) Long.MAX_VALUE else moves.minOrNull()!!
    }

    val res = solveIter(locs, 0)
    return res

}

fun inFinalSpot(pod: Map.Entry<Int, String>, locs: Map<Int, String>): Boolean {
    if (pod.value == "A") {
        return pod.key == 8 || (pod.key == 7 && locs[8] == "A")
    }
    if (pod.value == "B") {
        return pod.key == 10 || (pod.key == 9 && locs[10] == "B")
    }

    if (pod.value == "C") {
        return pod.key == 12 || (pod.key == 11 && locs[12] == "C")
    }

    return pod.key == 14 || (pod.key == 13 && locs[14] == "D")
}

fun mayTakeRoom(podType: String, dest: Int, locs: Map<Int, String>): Boolean {
    if (dest < 7) {
        return true
    }

    if (podType == "A") {
        if (dest == 8) return true
        if (dest == 7 && locs.containsKey(8) && locs[8] == "A") return true
    }

    if (podType == "B") {
        if (dest == 10) return true
        if (dest == 9 && locs.containsKey(10) && locs[10] == "B") return true
    }

    if (podType == "C") {
        if (dest == 12) return true
        if (dest == 11 && locs.containsKey(12) && locs[12] == "C") return true
    }

    if (podType == "D") {
        if (dest == 14) return true
        if (dest == 13 && locs.containsKey(14) && locs[14] == "D") return true
    }

    return false
}


