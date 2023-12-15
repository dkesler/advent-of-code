package d12

import utils.*

val memoTable = mutableMapOf<Springs3, Long>()

data class Springs3(val arrangement: List<Char>, val groups: List<Int>) {
    fun legalArrangements(): Long {
        if (memoTable.keys.contains(this)) return memoTable[this]!!

        if (arrangement.isEmpty() && groups.isEmpty()) {
            memoTable[this] = 1
            return 1
        }

        if (!couldBeLegal()) {
            memoTable[this] = 0
            return 0
        }

        if (arrangement[0] == '.') {
            val l = Springs3(arrangement.dropWhile { it == '.' }, groups).legalArrangements()
            memoTable[this] = l
            return l
        } else if (arrangement[0] == '#') {
            if (groups.isEmpty() || arrangement.size < groups[0] || arrangement.subList(0, groups[0]).any{ it == '.'} || (arrangement.size > groups[0] && arrangement[groups[0]] == '#' ) ) {
                memoTable[this] = 0
                return 0
            }
            val l = Springs3(arrangement.drop(groups[0]+1), groups.subList(1, groups.size)).legalArrangements()
            memoTable[this] = l
            return l
        } else {
            val isDot = Springs3(listOf('.') + arrangement.drop(1), groups).legalArrangements()
            val isHash = Springs3(listOf('#') + arrangement.drop(1), groups).legalArrangements()
            memoTable[this] = isDot + isHash
            return isDot + isHash
        }
    }

    fun couldBeLegal(): Boolean {
        return arrangement.count{ it == '?' || it == '#'} >= groups.sum()
    }
}

fun main() {
    val lines = readList("/d12.txt")

    val springs = lines.map {
        val s = it.split(" ")
        val arrangement = s[0].toList()
        val groups = s[1].split(",").map { it.toInt() }
        Springs3(arrangement + listOf('?') + arrangement + listOf('?') + arrangement + listOf('?') + arrangement + listOf('?') + arrangement, groups + groups + groups + groups + groups)
    }
    val counts = springs.mapIndexed { idx, springs ->
        println("starting ${idx}")
        springs.legalArrangements()
    }

    println(
      counts.sum()
    )
}