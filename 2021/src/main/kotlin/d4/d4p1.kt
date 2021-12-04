package d4

import utils.*

fun main() {
    val lines = Class::class.java.getResource("/d4/d4p1").readText().split("\n")
    val calls = lines[0].split(",")

    var boards = parseBoards(lines.subList(1, lines.size))

    var callsIdx = 0
    while (boards.all{ !it.won()} && callsIdx < calls.size) {
        boards = boards.map{it.withCall(calls[callsIdx])}
        callsIdx++
    }

    val winner = boards.filter { it.won() }

    println(winner[0].unmarkedTotal() * calls[callsIdx-1].toInt())
}

fun parseBoards(subList: List<String>): List<Board> {
    val filtered = subList.filter{it != ""}
    return filtered.chunked(5).map{ toBoard(it) }
}

fun toBoard(lines: List<String>): Board {
    val grid = lines.map { it.split(" ").filter { it != "" } }
    return Board(grid.map{ it.map{Cell(it, false)}})
}

class Cell(val v: String, val called: Boolean) {
    fun withCall(call: String): Cell {
        if (call == v) {
            return Cell(v, true)
        } else {
            return this
        }
    }
}

class Board(val grid: List<List<Cell>>) {
    fun won(): Boolean {
        if (grid.any{ it.all { it.called } }) return true

        for (col in 0..4) {
            if ((0..4).all{ grid[it][col].called })  return true
        }

        return false
    }

    fun unmarkedTotal(): Int {
        return grid.map{ it.filter{!it.called}}.flatten().map{it.v}.map{it.toInt()}.sum()
    }

    fun withCall(call: String): Board {
        return Board(grid.map{ it.map{ it.withCall(call) }})
    }

}
