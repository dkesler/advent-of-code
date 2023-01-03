package d22

import utils.readCharGrid
import utils.readList

fun left(map: List<List<Char>>, row: Int): Int {
    return map[row].indexOfFirst{ it != ' '}
}

fun right(map: List<List<Char>>, row: Int): Int {
    return map[row].indexOfLast{ it != ' '}
}

fun bottom(map: List<List<Char>>, col: Int): Int {
    for (row in map.indices.reversed()) {
        if (map[row][col] != ' ') return row
    }
    return -1
}

fun top(map: List<List<Char>>, col: Int): Int {
    for (row in map.indices) {
        if (map[row][col] != ' ') return row
    }
    return -1
}


enum class Facing(val pwVal: Int) {
    RIGHT(0),
    DOWN(1),
    LEFT(2),
    UP(3);


    fun turnRight(): Facing {
        return when(this) {
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            UP -> RIGHT
        }
    }

    fun turnLeft(): Facing {
        return when(this) {
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
            UP -> LEFT
        }
    }

    fun nextPos(pos: Pair<Int, Int>): Pair<Int, Int> {
        return when(this) {
            RIGHT -> Pair(pos.first, pos.second+1)
            DOWN -> Pair(pos.first+1, pos.second)
            LEFT -> Pair(pos.first, pos.second-1)
            UP -> Pair(pos.first-1, pos.second)
        }
    }
}
//156054 - too high
fun main() {
    val unpaddedMap = readCharGrid("/d22map.txt")
    val mapWidth = unpaddedMap.map{ it.size }.maxOrNull()!!
    val map = unpaddedMap.map {
        if (it.size < mapWidth)
            it + List(mapWidth - it.size) { ' ' }
        else
            it
    }
    val path = readList("/d22path.txt")[0]
            .replace("R", " R ")
            .replace("L", " L ")
            .split(" ")
            .filter{it != "" }

    var pos = Pair(0, left(map, 0))
    var facing = Facing.RIGHT
    for (move in path) {
        if (move == "R") {
            facing = facing.turnRight()
        } else if (move == "L") {
            facing = facing.turnLeft()
        } else {
            val moveAmount = move.toInt()
            for(i in (1..moveAmount)) {
                var next = facing.nextPos(pos)
                if (next.first < 0) {
                    next = Pair(bottom(map, next.second), next.second)
                } else if (next.first >= map.size) {
                    next = Pair(top(map, next.second), next.second)
                } else if  (next.second < 0) {
                    next = Pair(next.first, right(map, next.first))
                } else if (next.second >= map[0].size) {
                    next = Pair(next.first, left(map, next.first))
                }

                if (map[next.first][next.second] == ' ') {
                    when(facing) {
                        Facing.RIGHT -> next = Pair(next.first, left(map, next.first))
                        Facing.DOWN -> next = Pair(top(map, next.second), next.second)
                        Facing.LEFT -> next = Pair(next.first, right(map, next.first))
                        Facing.UP -> next = Pair(bottom(map, next.second), next.second)
                    }
                }

                if (map[next.first][next.second] == '.') {
                    pos = next
                }
            }
        }
        if (map[pos.first][pos.second] != '.') {
            println("wat")
        }
        //printMap(map, pos, facing)
    }

    println( 1000 * (pos.first + 1) + 4 * (pos.second + 1) + facing.pwVal)
}

fun printMap(map: List<List<Char>>, pos: Pair<Int, Int>, facing: Facing) {
    for (row in map.indices) {
        for (col in map[row].indices) {
            if (Pair(row, col) == pos) {
                when(facing) {
                    Facing.RIGHT -> print('>')
                    Facing.DOWN -> print('V')
                    Facing.LEFT -> print('<')
                    Facing.UP -> print('^')
                }
            } else {
                print(map[row][col])
            }
        }
        println("")
    }
    println("\n\n\n")
}
