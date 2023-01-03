package d23

import utils.readCharGrid

fun main() {
    val list = readCharGrid("/d23.txt")
    var elves = mutableSetOf<Pair<Long, Long>>()
    for (row in list.indices) {
        for (col in list[0].indices) {
            if (list[row][col] == '#') elves.add(Pair(row.toLong(), col.toLong()))
        }
    }

    val dirPriority = listOf("N", "S", "W", "E")
    for (round in (0..9)) {
        val proposals = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
        for (elf in elves) {
            val allNeighbors = listOf(
                    Pair(elf.first-1, elf.second-1),
                    Pair(elf.first-1, elf.second),
                    Pair(elf.first-1, elf.second+1),
                    Pair(elf.first, elf.second-1),
                    Pair(elf.first, elf.second+1),
                    Pair(elf.first+1, elf.second-1),
                    Pair(elf.first+1, elf.second),
                    Pair(elf.first+1, elf.second+1)
            )
            if (allNeighbors.any{ it in elves } ) {
                for (dirOffset in (0..3)) {
                    val dir = dirPriority[(round + dirOffset) % 4]
                    val neighbors = when (dir) {
                        "N" -> listOf(Pair(elf.first - 1, elf.second - 1), Pair(elf.first - 1, elf.second), Pair(elf.first - 1, elf.second + 1))
                        "S" -> listOf(Pair(elf.first + 1, elf.second - 1), Pair(elf.first + 1, elf.second), Pair(elf.first + 1, elf.second + 1))
                        "E" -> listOf(Pair(elf.first - 1, elf.second + 1), Pair(elf.first, elf.second + 1), Pair(elf.first + 1, elf.second + 1))
                        else -> listOf(Pair(elf.first - 1, elf.second - 1), Pair(elf.first, elf.second - 1), Pair(elf.first + 1, elf.second - 1))
                    }

                    if (neighbors.none { it in elves }) {
                        proposals[elf] = when (dir) {
                            "N" -> Pair(elf.first - 1, elf.second)
                            "S" -> Pair(elf.first + 1, elf.second)
                            "E" -> Pair(elf.first, elf.second + 1)
                            else -> Pair(elf.first, elf.second - 1)
                        }
                        break
                    }
                }
            }
        }
        val newElves = mutableSetOf<Pair<Long, Long>>()
        for (elf in elves) {
            if (proposals.values.count{ it == proposals[elf]} == 1) {
                newElves.add(proposals[elf]!!)
            } else {
                newElves.add(elf)
            }
        }

        elves = newElves
        //printElves(elves)
    }

    val minRow = elves.minOf{ it.first }
    val maxRow = elves.maxOf{ it.first }
    val minCol = elves.minOf{ it.second }
    val maxCol = elves.maxOf{ it.second }

    println(
    (minRow..maxRow).flatMap{ row -> (minCol..maxCol).map{ Pair(row, it) }}
            .filter{ it !in elves}
            .count()
    )
}

fun printElves(elves: MutableSet<Pair<Long, Long>>) {
    val minRow = elves.minOf{ it.first }
    val maxRow = elves.maxOf{ it.first }
    val minCol = elves.minOf{ it.second }
    val maxCol = elves.maxOf{ it.second }

    for (row in (minRow..maxRow)) {
        for(col in (minCol..maxCol)) {
            if (Pair(row, col) in elves) print('#') else print('.')
        }
        println("")
    }

    println("\n\n\n")
}
