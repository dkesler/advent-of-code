package d23

//ugly handcrafted move list.  would be better if we could dynamically generate the move list
//instead of relying on so much hardcoding
fun main() {
    val moveList = mapOf(
        Pair(0, listOf(
            Move(3, setOf(1, 7), 7),
            Move(4, setOf(1, 7, 8), 8),
            Move(5, setOf(1, 7, 8, 15), 15),
            Move(6, setOf(1, 7, 8, 15, 16), 16),

            Move(5, setOf(1, 2, 9), 9),
            Move(6, setOf(1, 2, 9, 10), 10),
            Move(7, setOf(1, 2, 9, 10, 17), 17),
            Move(8, setOf(1, 2, 9, 10, 17, 18), 18),

            Move(7, setOf(1, 2, 3, 11), 11),
            Move(8, setOf(1, 3, 2, 11, 12), 12),
            Move(9, setOf(1, 2, 3, 11, 12, 19), 19),
            Move(10, setOf(1, 3, 2, 11, 12, 19, 20), 20),

            Move(9, setOf(1, 2, 3, 4, 13), 13),
            Move(10, setOf(1, 2, 3, 4, 13, 14), 14),
            Move(11, setOf(1, 2, 3, 4, 13, 14, 21), 21),
            Move(12, setOf(1, 2, 3, 4, 13, 21, 22), 22)

        )),
        Pair(1, listOf(
            Move(2, setOf(7), 7),
            Move(3, setOf(7, 8), 8),
            Move(4, setOf(7, 8, 15), 15),
            Move(5, setOf(7, 8, 15, 16), 16),

            Move(4, setOf(2, 9), 9),
            Move(5, setOf(2, 9, 10), 10),
            Move(6, setOf(2, 9, 10, 17), 17),
            Move(7, setOf(2, 9, 10, 17, 18), 18),

            Move(6, setOf(2, 3, 11), 11),
            Move(7, setOf(3, 2, 11, 12), 12),
            Move(8, setOf(2, 3, 11, 12, 19), 19),
            Move(9, setOf(3, 2, 11, 12, 19, 20), 20),

            Move(8, setOf(2, 3, 4, 13), 13),
            Move(9, setOf(2, 3, 4, 13, 14), 14),
            Move(10, setOf(2, 3, 4, 13, 14, 21), 21),
            Move(11, setOf(2, 3, 4, 13, 21, 22), 22)

        )),
        Pair(2, listOf(
            Move(2, setOf(7), 7),
            Move(3, setOf(7, 8), 8),
            Move(4, setOf(7, 8, 15), 15),
            Move(5, setOf(7, 8, 14, 16), 16),

            Move(2, setOf(9), 9),
            Move(3, setOf(9, 10), 10),
            Move(4, setOf(9, 10, 17), 17),
            Move(5, setOf(9, 10, 17, 18), 18),

            Move(4, setOf(3, 11), 11),
            Move(5, setOf(3, 11, 12), 12),
            Move(6, setOf(3, 11, 12, 19), 19),
            Move(7, setOf(3, 11, 12, 19, 20), 20),

            Move(6, setOf(3, 4, 13), 13),
            Move(7, setOf(3, 4, 13, 14), 14),
            Move(8, setOf(3, 4, 13, 21), 21),
            Move(9, setOf(3, 4, 13, 21, 22), 22)

        )),
        Pair(3, listOf(
            Move(4, setOf(2, 7), 7),
            Move(5, setOf(2, 7, 8), 8),
            Move(6, setOf(2, 7, 8, 15), 15),
            Move(7, setOf(2, 7, 8, 15, 16), 16),

            Move(2, setOf(9), 9),
            Move(3, setOf(9, 10), 10),
            Move(4, setOf(9, 10, 17), 17),
            Move(5, setOf(9, 10, 17, 18), 18),

            Move(2, setOf(11), 11),
            Move(3, setOf(11, 12), 12),
            Move(4, setOf(11, 12, 19), 19),
            Move(5, setOf(11, 12, 19, 20), 20),

            Move(4, setOf(4, 13), 13),
            Move(5, setOf(4, 13, 14), 14),
            Move(6, setOf(4, 13, 14, 21), 21),
            Move(7, setOf(4, 13, 14, 21, 22),  22)

        )),
        Pair(4, listOf(
            Move(6, setOf(2, 3,  7), 7),
            Move(7, setOf(2, 3, 7, 8), 8),
            Move(8, setOf(2, 3, 7, 8, 15), 15),
            Move(9, setOf(2, 3, 7, 8, 15, 16), 16),

            Move(4, setOf(3, 9), 9),
            Move(5, setOf(3, 9, 10), 10),
            Move(6, setOf(3, 9, 10, 17), 17),
            Move(7, setOf(3, 9, 10, 17, 18), 18),

            Move(2, setOf(11), 11),
            Move(3, setOf(11, 12), 12),
            Move(4, setOf(11, 12, 19), 19),
            Move(5, setOf(11, 12, 19, 20), 20),

            Move(2, setOf(13), 13),
            Move(3, setOf(13, 14), 14),
            Move(4, setOf(13, 14, 21), 21),
            Move(5, setOf(13, 14, 21, 22), 22)

        )),
        Pair(5, listOf(
            Move(8, setOf(2, 3, 4, 7), 7),
            Move(9, setOf(2, 3, 4, 7, 8), 8),
            Move(10, setOf(2, 3, 4, 7, 8, 15), 15),
            Move(11, setOf(2, 3, 4, 5, 7, 8, 15, 16), 16),

            Move(6, setOf(3, 4, 9), 9),
            Move(7, setOf(3, 4, 9, 10), 10),
            Move(8, setOf(3, 4, 9, 10, 17), 17),
            Move(9, setOf(3, 4, 9, 10, 17, 18), 18),

            Move(4, setOf(4, 11), 11),
            Move(5, setOf(4, 11, 12), 12),
            Move(6, setOf(4, 11, 12, 19), 19),
            Move(6, setOf(4, 11, 12, 19, 20), 20),

            Move(2, setOf(13), 13),
            Move(3, setOf(13, 14), 14),
            Move(4, setOf(13, 14, 21), 21),
            Move(5, setOf(13, 14, 21, 22), 22)
        )),
        Pair(6, listOf(
            Move(9, setOf(2, 3, 4, 5,  7), 7),
            Move(10, setOf(2, 3, 4, 5, 7, 8), 8),
            Move(11, setOf(2, 3, 4, 5, 7, 8, 15), 15),
            Move(12, setOf(2, 3, 4, 5, 7, 8, 15, 16), 16),

            Move(7, setOf(3, 4, 5, 9), 9),
            Move(8, setOf(3, 4, 5, 9, 10), 10),
            Move(9, setOf(3, 4, 5, 9, 10, 17), 17),
            Move(10, setOf(3, 4, 5, 9, 10, 17, 18), 18),

            Move(5, setOf(4, 5, 11), 11),
            Move(6, setOf(4, 5, 11, 12), 12),
            Move(7, setOf(4, 5, 11, 12, 19), 19),
            Move(8, setOf(4, 5, 11, 12, 19, 20), 20),

            Move(3, setOf(5, 13), 13),
            Move(4, setOf(5, 13, 14), 14),
            Move(5, setOf(5, 13, 14, 21), 21),
            Move(6, setOf(5, 13, 14, 21, 22), 22)
        )),

        Pair(7, listOf(
            Move(3, setOf(0, 1), 0),
            Move(2, setOf(1), 1),
            Move(2, setOf(2), 2),
            Move(4, setOf(2, 3), 3),
            Move(6, setOf(2, 3, 4), 4),
            Move(8, setOf(2, 3, 4, 5), 5),
            Move(9, setOf(2, 3, 4, 5, 6), 6)
            )),
        Pair(8, listOf(
            Move(4, setOf(0, 1, 7), 0),
            Move(3, setOf(1, 7), 1),
            Move(3, setOf(2, 7), 2),
            Move(5, setOf(2, 3, 7), 3),
            Move(7, setOf(2, 3, 4, 7), 4),
            Move(9, setOf(2, 3, 4, 5, 7), 5),
            Move(10, setOf(2, 3, 4, 5, 6, 7), 6)
        )),
        Pair(15, listOf(
            Move(5, setOf(0, 1, 7, 8), 0),
            Move(4, setOf(1, 7, 8), 1),
            Move(4, setOf(2, 7, 8), 2),
            Move(6, setOf(2, 3, 7, 8), 3),
            Move(8, setOf(2, 3, 4, 7, 8), 4),
            Move(10, setOf(2, 3, 4, 5, 7, 8), 5),
            Move(11, setOf(2, 3, 4, 5, 6, 7, 8), 6)
        )),
        Pair(16, listOf(
            Move(6, setOf(0, 1, 7, 8, 15), 0),
            Move(5, setOf(1, 7, 8, 15), 1),
            Move(5, setOf(2, 7, 8, 15), 2),
            Move(7, setOf(2, 3, 7, 8, 15), 3),
            Move(9, setOf(2, 3, 4, 7, 8, 15), 4),
            Move(11, setOf(2, 3, 4, 5, 7, 8, 15), 5),
            Move(12, setOf(2, 3, 4, 5, 6, 7, 8, 15), 6)
        )),
        Pair(9, listOf(
            Move(5, setOf(0, 1, 2), 0),
            Move(4, setOf(1, 2), 1),
            Move(2, setOf(2), 2),
            Move(2, setOf(3), 3),
            Move(4, setOf(3, 4), 4),
            Move(6, setOf(3, 4, 5), 5),
            Move(7, setOf(3, 4, 5, 6), 6)
        )),
        Pair(10, listOf(
            Move(6, setOf(0, 1, 2, 9), 0),
            Move(5, setOf(1, 2, 9), 1),
            Move(3, setOf(2, 9), 2),
            Move(3, setOf(3, 9), 3),
            Move(5, setOf(3, 4, 9), 4),
            Move(7, setOf(3, 4, 5, 9), 5),
            Move(8, setOf(3, 4, 5, 6, 9), 6)
        )),
        Pair(17, listOf(
            Move(7, setOf(0, 1, 2, 9, 10), 0),
            Move(6, setOf(1, 2, 9, 10), 1),
            Move(4, setOf(2, 9, 10), 2),
            Move(4, setOf(3, 9, 10), 3),
            Move(6, setOf(3, 4, 9, 10), 4),
            Move(8, setOf(3, 4, 5, 9, 10), 5),
            Move(9, setOf(3, 4, 5, 6, 9, 10), 6)
        )),
        Pair(18, listOf(
            Move(8, setOf(0, 1, 2, 9, 10, 17), 0),
            Move(7, setOf(1, 2, 9, 10, 17), 1),
            Move(5, setOf(2, 9, 10, 17), 2),
            Move(5, setOf(3, 9, 10, 17), 3),
            Move(7, setOf(3, 4, 9, 10, 17), 4),
            Move(9, setOf(3, 4, 5, 9, 10, 17), 5),
            Move(10, setOf(3, 4, 5, 6, 9, 10, 17), 6)
        )),
        Pair(11, listOf(
            Move(7, setOf(0, 1, 2, 3), 0),
            Move(6, setOf(1, 2, 3), 1),
            Move(4, setOf(2, 3), 2),
            Move(2, setOf(3), 3),
            Move(2, setOf(4), 4),
            Move(4, setOf(4, 5), 5),
            Move(5, setOf(4, 5, 6), 6)
        )),
        Pair(12, listOf(
            Move(8, setOf(0, 1, 2, 3, 11), 0),
            Move(7, setOf(1, 2, 3, 11), 1),
            Move(5, setOf(2, 3, 11), 2),
            Move(3, setOf(3, 11), 3),
            Move(3, setOf(4, 11), 4),
            Move(5, setOf(4, 5, 11), 5),
            Move(6, setOf(4, 5, 6, 11), 6),
            Move(7, setOf(2, 3, 7, 11), 7)
        )),
        Pair(19, listOf(
            Move(9, setOf(0, 1, 2, 3, 11, 12), 0),
            Move(8, setOf(1, 2, 3, 11, 12), 1),
            Move(6, setOf(2, 3, 11, 12), 2),
            Move(4, setOf(3, 11, 12), 3),
            Move(4, setOf(4, 11, 12), 4),
            Move(6, setOf(4, 5, 11, 12), 5),
            Move(7, setOf(4, 5, 6, 11, 12), 6),
            Move(8, setOf(2, 3, 7, 11, 12), 7)
        )),
        Pair(20, listOf(
            Move(10, setOf(0, 1, 2, 3, 11, 12, 19), 0),
            Move(9, setOf(1, 2, 3, 11, 12, 19), 1),
            Move(7, setOf(2, 3, 11, 12, 19), 2),
            Move(5, setOf(3, 11, 12, 19), 3),
            Move(5, setOf(4, 11, 12, 19), 4),
            Move(7, setOf(4, 5, 11, 12, 19), 5),
            Move(8, setOf(4, 5, 6, 11, 12, 19), 6),
            Move(9, setOf(2, 3, 7, 11, 12, 19), 7)
        )),
        Pair(13, listOf(
            Move(9, setOf(0, 1, 2, 3, 4), 0),
            Move(8, setOf(1, 2, 3, 4), 1),
            Move(6, setOf(2, 3, 4), 2),
            Move(4, setOf(3, 4), 3),
            Move(2, setOf(4), 4),
            Move(2, setOf(5), 5),
            Move(3, setOf(5, 6), 6),
            Move(8, setOf(2, 3, 4, 7), 7)
        )),
        Pair(14, listOf(
            Move(10, setOf(0, 1, 2, 3, 4, 13), 0),
            Move(9, setOf(1, 2, 3, 4, 13), 1),
            Move(7, setOf(2, 3, 4, 13), 2),
            Move(5, setOf(3, 4, 13), 3),
            Move(3, setOf(4, 13), 4),
            Move(3, setOf(5, 13), 5),
            Move(4, setOf(5, 6, 13), 6)
        )),
        Pair(21, listOf(
            Move(11, setOf(0, 1, 2, 3, 4, 13, 14), 0),
            Move(10, setOf(1, 2, 3, 4, 13, 14), 1),
            Move(8, setOf(2, 3, 4, 13, 14), 2),
            Move(6, setOf(3, 4, 13, 14), 3),
            Move(4, setOf(4, 13, 14), 4),
            Move(4, setOf(5, 13, 14), 5),
            Move(5, setOf(5, 6, 13, 14), 6)
        )),
        Pair(22, listOf(
            Move(12, setOf(0, 1, 2, 3, 4, 13, 14, 21), 0),
            Move(11, setOf(1, 2, 3, 4, 13, 14, 21), 1),
            Move(9, setOf(2, 3, 4, 13, 14, 21), 2),
            Move(7, setOf(3, 4, 13, 14, 21), 3),
            Move(5, setOf(4, 13, 14, 21), 4),
            Move(5, setOf(5, 13, 14, 21), 5),
            Move(6, setOf(5, 6, 13, 14, 21), 6)
        )),
    )

    val locs = mutableMapOf(
        Pair(7, "D"),
        Pair(8, "D"),
        Pair(15, "D"),
        Pair(16, "C"),

        Pair(9, "A"),
        Pair(10, "C"),
        Pair(17, "B"),
        Pair(18, "A"),

        Pair(11, "D"),
        Pair(12, "B"),
        Pair(19, "A"),
        Pair(20, "B"),

        Pair(13, "C"),
        Pair(14, "A"),
        Pair(21, "C"),
        Pair(22, "B")
    )

    val start = System.currentTimeMillis()
    val res = solve2(locs, moveList)
    println("Execution time ${System.currentTimeMillis() - start} ms")
    println(res)



}

fun solve2(locs: Map<Int, String>, moveList: Map<Int, List<Move>>): Long {
    val energyMap = mapOf(
        Pair("A", 1L),
        Pair("B", 10),
        Pair("C", 100),
        Pair("D", 1000),
    )
    //separate tables for real results and "no solution" because storing max long into the memo table
    //led to some weirdness.
    /*
      execution time benefits of memo tables
      40s w/ both
      600s w/ memo table w/o deadlocs
      never fihishes w/o memo table
    */

    val memoTable = mutableMapOf<Map<Int, String>, Long>()
    val deadLocs = mutableSetOf<Map<Int, String>>()
    fun solveIter(locs: Map<Int, String>, energy: Long): Long {

        if (memoTable.containsKey(locs)) {
            return memoTable[locs]!! + energy
        }

        if (deadLocs.contains(locs)) {
            return Long.MAX_VALUE
        }
        //find all pods we might want to move
        val podsWantingToMove = locs.entries.filter { !inFinalSpot2(it.value, it.key, locs) }

        //if no pods need to move, we're in a solved state
        if (podsWantingToMove.isEmpty()) {
            return energy
        }

        //for each one, find all valid moves
        val moves = podsWantingToMove.map { pod ->
            val possibleMoves = moveList[pod.key]!!.filter { move ->
                val necessarySpotsFree = move.needsFree.intersect(locs.keys).isEmpty()
                val mayTakeRoom = mayTakeRoom2(pod.value, move.dest, locs)
                mayTakeRoom && necessarySpotsFree
            }

            //do that move, then call solve

            possibleMoves.map { move ->
                solveIter(
                    locs.filter { it.key != pod.key }.plus(Pair(move.dest, pod.value)),
                    energy + move.steps * energyMap[pod.value]!!
                )
            }

        }
            .flatten()
            //filter any responses w max long because it means they didnt actually solve
            .filter{it != Long.MAX_VALUE}


        if (moves.isNotEmpty()) {
            memoTable[locs] = moves.minOrNull()!! - energy
        } else {
            deadLocs += locs
        }

        //choose option with min energy
        return if (moves.isEmpty()) Long.MAX_VALUE else moves.minOrNull()!!
    }

    val res = solveIter(locs, 0)
    return res

}

fun inFinalSpot2(podType: String, dest: Int, locs: Map<Int, String>): Boolean {
    if (podType == "A") {
        return dest == 16
                || (dest == 15 && locs[16] == "A")
                || (dest == 8 && locs[16] == "A" && locs[15] == "A")
                || (dest == 7 && locs[8] == "A" && locs[16] == "A" && locs[15] == "A")
    }
    if (podType == "B") {
        return dest == 18
                || (dest == 17 && locs[18] == "B")
                || (dest == 10 && locs[18] == "B" && locs[17] == "B")
                || (dest == 9 && locs[10] == "B" && locs[18] == "B" && locs[17] == "B")
    }

    if (podType == "C") {
        return dest == 20
                || (dest == 19 && locs[20] == "C")
                || (dest == 12 && locs[19] == "C" && locs[20] == "C")
                || (dest == 11 && locs[12] == "C" && locs[19] == "C" && locs[20] == "C")
    }

        return dest == 22
                || (dest == 21 && locs[22] == "D")
                || (dest == 14 && locs[22] == "D" && locs[21] == "D")
                || (dest == 13 && locs[14] == "D" && locs[22] == "D" && locs[21] == "D")
}

fun mayTakeRoom2(podType: String, dest: Int, locs: Map<Int, String>): Boolean {
    if (dest < 7) {
        return true
    }

    return inFinalSpot2(podType, dest, locs)
}
