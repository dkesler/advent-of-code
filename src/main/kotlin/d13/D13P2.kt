package d13

import intcode.Computer
import intcode.loadProgramFromFile

fun main() {
    val computer = Computer(loadProgramFromFile("src/main/resources/d13/d13p2"))
    val board = Board(toBoard(computer.runBlockingProgram(listOf()).second))
    var cmd = 0L

    do {
        board.print()
        //val input = getInput()
        val output = computer.runBlockingProgram(listOf(cmd))
        cmd = board.update(output.second)

    } while (output.first)

    board.print()
}

fun getInput(): Long {
    val line = readLine()
    if (line == "a") {
        return -1
    } else if (line == "d") {
        return 1
    } else {
        return 0
    }
}

fun toBoard(output: List<Long>): MutableList<MutableList<Long>> {
    return output.chunked(3).map{ Triple(it[0], it[1], it[2]) }
        .filter{it.first >= 0 }
        .groupBy({it.second}, { Pair(it.first, it.third) } )
        .mapValues { it.value.sortedBy { it.first }.map{it.second } }
        .toList()
        .sortedBy { it.first }
        .map { it.second.toMutableList() }.toMutableList()
}

class Board(val board: MutableList<MutableList<Long>>, var score: Long = 0) {
    fun update(update: List<Long>): Long {
        val ballXBefore = ballX()
        val ballYBefore = tileY(4L)

        val updates = update.chunked(3).map { Triple(it[0], it[1], it[2]) }
        updates.filter{it.first >= 0}.forEach{ board[it.second.toInt()][it.first.toInt()] = it.third }
        val scores = updates.filter { it.first == -1L }
        if (scores.isNotEmpty()) {
            score = scores[0].third
        }

        val ballXAfter = ballX()
        val ballYAfter = tileY(4L)
        val paddleX = paddleX()

        var ballXAdj = (ballXAfter - ballXBefore)
        var ballXNext = ballXAfter + ballXAdj
        var ballYAdj = (ballYAfter - ballYBefore)
        var ballYNext = ballYAfter + ballYAdj
        val willCollideWith = mutableSetOf<Pair<Long, Long>>()

        while (reflectorAt(willCollideWith, ballXNext, ballYAfter) || reflectorAt(willCollideWith, ballXAfter, ballYNext) || reflectorAt(willCollideWith, ballXNext, ballYNext)) {
            if (reflectorAt(willCollideWith, ballXNext, ballYAfter)) {
                willCollideWith.add(Pair(ballXNext, ballYAfter))
                ballXAdj *= -1
                ballXNext = ballXAfter + ballXAdj

            }

            if (reflectorAt(willCollideWith, ballXAfter, ballYNext)) {
                willCollideWith.add(Pair(ballXAfter, ballYNext))
                ballYAdj *= -1
                ballYNext = ballYAfter + ballYAdj

            }

            if (reflectorAt(willCollideWith, ballXNext, ballYNext)) {
                willCollideWith.add(Pair(ballXNext, ballYNext))
                ballXAdj *= -1
                ballYAdj *= -1
                ballXNext = ballXAfter + ballXAdj
                ballYNext = ballYAfter + ballYAdj
            }
        }

        val target = ballXNext

        println("ballXAfter: $ballXAfter")
        println("ballXNext: $ballXNext")
        println("paddleX: $paddleX")

        if (ballYAfter == 20L && ballXAfter == paddleX) {
            return 0
        }

        if (paddleX > target) {
            return -1L
        } else if (paddleX < target) {
            return 1L
        } else {
            return 0L
        }
    }


    private fun reflectorAt(alreadyHit: MutableSet<Pair<Long, Long>>, x: Long, y: Long): Boolean {
        if (y >= board.size) {
            return false
        }

        if (alreadyHit.contains(Pair(x, y))) {
            return false
        }

        return setOf(1L, 2, 3).contains(board[y.toInt()][x.toInt()])
    }

    fun print() {
        println("")
        println("")
        println("")
        println("")
        println("")

        for (line in board) {
            for (tile in line) {
                print(toOutputTile(tile))
            }
            println("")
        }

        println("Score: $score")
    }

    fun paddleX(): Long {
        return tileX(3L)
    }

    fun ballX(): Long {
        return tileX(4L)
    }

    private fun tileX(tile: Long): Long {
        for (line in board) {
            for (x in 0.rangeTo(line.size - 1)) {
                if (line[x] == tile) {
                    return x.toLong()
                }
            }
        }
        return 0
    }

    private fun tileY(tile: Long): Long {
        for (y in 0.rangeTo(board.size - 1)) {
            if (board[y].contains(tile)) {
                return y.toLong()
            }
        }
        return 0
    }


    private fun toOutputTile(tile: Long): String {
        return when(tile.toInt()) {
            0 -> " "
            1 -> "."
            2 -> "#"
            3 -> "_"
            4 -> "o"
            else -> "?"
        }
    }
}
