package d15

import intcode.Computer
import intcode.loadProgramFromFile

fun main() {
    val computer = Computer(loadProgramFromFile("2019/src/main/resources/d15/d15p1"))
    val tiles = Tiles()
    val strategy = RightHandRuleStrategy()
    var iters = 10000
    do {
        val input = strategy.nextMove(tiles)
        val outputs = computer.runBlockingProgram(listOf(input.toLong())).second
        tiles.applyMove(input.toLong(), outputs.get(0))
        strategy.applyMove(input, outputs.get(0))
        //tiles.print()

    } while (iters-- > 0)

    tiles.droneX = 0
    tiles.droneY = 0
    tiles.print()

    var minutes = 0
    while(tiles.hasEmptyTiles()) {
        tiles.spreadOxygen()
        tiles.print()
        minutes++
    }

    println(minutes)
}

//right hand rule:  if you can turn right, turn right.
//else if you can go straight, go straight
//else if you can go left, go left
//else go back
class RightHandRuleStrategy(var facing: Int = 1) {
    fun nextMove(tiles: Tiles): Int {
        if (tiles.getTileTypeAt( tiles.droneX + xOffsetRight(), tiles.droneY + yOffsetRight() ) != 0) {
            return turnRight()
        } else if (tiles.getTileTypeAt( tiles.droneX + xOffsetStraight(), tiles.droneY + yOffsetStraight() ) != 0) {
            return facing
        } else if (tiles.getTileTypeAt( tiles.droneX + xOffsetLeft(), tiles.droneY + yOffsetLeft() ) != 0) {
            return turnLeft()
        } else {
            return turnBack()
        }
    }

    private fun turnRight(): Int {
        return when(facing) {
            1 -> 4
            2 -> 3
            3 -> 1
            4 -> 2
            else -> 0
        }
    }

    private fun turnLeft(): Int {
        return when(facing) {
            1 -> 3
            2 -> 4
            3 -> 2
            4 -> 1
            else -> 0
        }
    }

    private fun turnBack(): Int {
        return when(facing) {
            1 -> 2
            2 -> 1
            3 -> 4
            4 -> 3
            else -> 0
        }
    }

    private fun xOffsetRight(): Int {
        return when(facing) {
            1 -> 1
            2 -> -1
            else -> 0
        }
    }

    private fun yOffsetRight(): Int {
        return when(facing) {
            3 -> -1
            4 -> 1
            else -> 0
        }
    }

    private fun xOffsetStraight(): Int {
        return when(facing) {
            3 -> -1
            4 -> 1
            else -> 0
        }
    }

    private fun yOffsetStraight(): Int {
        return when(facing) {
            1 -> -1
            2 -> 1
            else -> 0
        }
    }

    private fun xOffsetLeft(): Int {
        return -xOffsetRight()
    }

    private fun yOffsetLeft(): Int {
        return -yOffsetRight()
    }


    fun applyMove(input: Int, result: Long) {
        if (result == 1L || result == 2L) {
            facing = input
        }
    }
}

//tile types:  0 -> wall, 1 -> empty, 2 -> o2 system, 3 -> unknown
class Tiles(val tiles: MutableMap<Int, MutableMap<Int, Int>>, var droneX: Int, var droneY: Int, var minX: Int, var maxX: Int, var minY: Int, var maxY : Int) {
    constructor() : this(mutableMapOf(Pair(0, mutableMapOf(Pair(0, 1)))), 0, 0, 0, 0, 0, 0)

    fun print() {
        println("")
        println("")
        println("")

        for (y in (minY).rangeTo(maxY)) {
            for (x in (minX).rangeTo(maxX)) {
                val tileType: Int = getTileTypeAt(x, y)
                if (x == 0 && y == 0) {
                    print("S")
                } else if (x == droneX && y == droneY) {
                    print("D")
                } else {
                    when(tileType) {
                        0 -> print("#")
                        1 -> print(".")
                        2 -> print("O")
                        3 -> print("?")
                    }
                }
            }
            println("")
        }
    }

    fun getTileTypeAt(x: Int, y: Int) = tiles.get(y)?.get(x) ?: 3

    fun applyMove(input: Long, result: Long) {
        val newX = when(input) {
            3L -> droneX - 1
            4L -> droneX + 1
            else -> droneX
        }

        val newY = when(input) {
            1L -> droneY - 1
            2L -> droneY + 1
            else -> droneY
        }

        when(result) {
            0L -> insert(newX, newY, 0)
            1L -> {
                insert(newX, newY, 1)
                droneX = newX
                droneY = newY
            }
            2L -> {
                insert(newX, newY, 2)
                droneX = newX
                droneY = newY
            }
        }
    }

    private fun insert(x: Int, y: Int, type: Int) {
        if (!tiles.containsKey(y)) {
            tiles.put(y, mutableMapOf())
        }

        if (!tiles.getValue(y).containsKey(x)) {
            tiles.getValue(y).put(x, type)
        } else {
            val existingValue = tiles.getValue(y).getValue(x)
            if (existingValue != type) {
                throw RuntimeException("Value of tile ($x, $y) somehow changed from $existingValue to $type")
            }
        }

        if (x > maxX) maxX = x
        if (x < minX) minX = x
        if (y > maxY) maxY = y
        if (y < minY) minY = y

    }

    fun hasEmptyTiles(): Boolean {
        return tiles.any{ it.value.any { t -> t.value == 1} }
    }

    fun spreadOxygen() {
        val newOxygenTiles = mutableListOf<Pair<Int, Int>>()

        for (y in (minY).rangeTo(maxY)) {
            for (x in (minX).rangeTo(maxX)) {
                val tileType: Int = getTileTypeAt(x, y)
                if (tileType == 2) {
                    if (getTileTypeAt(x+1, y) == 1) newOxygenTiles.add(Pair(x+1, y))
                    if (getTileTypeAt(x-1, y) == 1) newOxygenTiles.add(Pair(x-1, y))
                    if (getTileTypeAt(x, y+1) == 1) newOxygenTiles.add(Pair(x, y+1))
                    if (getTileTypeAt(x, y-1) == 1) newOxygenTiles.add(Pair(x, y-1))
                }
            }
        }

        newOxygenTiles.forEach{ tiles.getValue(it.second).put(it.first, 2) }
    }
}
