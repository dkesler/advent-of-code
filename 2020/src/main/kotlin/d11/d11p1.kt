package d11

fun tick2(seats: List<List<Char>>) : List<List<Char>> {
    fun occupied(row: Int, col: Int, rowInc: Int, colInc: Int): Int {
        if (row < 0 || row >= seats.size) {
            return 0
        }
        if (col < 0 || col >= seats[row].size) {
            return 0
        }

        if (seats[row][col] == '.') {
            return occupied(row + rowInc, col+colInc, rowInc, colInc)
        }

        return if (seats[row][col] == '#') 1 else 0
    }

    return seats.mapIndexed{ rowIdx, row ->
        row.mapIndexed { colIdx, seat ->
            if (seat == '.') {
                '.'
            } else {
                val adjOccupied = occupied(rowIdx-1, colIdx-1, -1, -1) +
                        occupied(rowIdx, colIdx-1, 0, -1) +
                        occupied(rowIdx+1, colIdx-1, +1, -1) +
                        occupied(rowIdx-1, colIdx, -1, 0) +
                        occupied(rowIdx+1, colIdx, +1, 0) +
                        occupied(rowIdx-1, colIdx+1, -1, +1) +
                        occupied(rowIdx, colIdx+1, 0 ,+1) +
                        occupied(rowIdx+1, colIdx+1, 1, 1)


                if (seat == 'L' && adjOccupied == 0) {
                    '#'
                } else if (seat == '#' && adjOccupied >= 5) {
                    'L'
                } else {
                    seat
                }
            }
        }
    }
}


fun tick(seats: List<List<Char>>) : List<List<Char>> {
    fun occupied(row: Int, col: Int): Int {
        if (row < 0 || row >= seats.size) {
            return 0
        }
        if (col < 0 || col >= seats[row].size) {
            return 0
        }
        return if (seats[row][col] == '#') 1 else 0
    }

    return seats.mapIndexed{ rowIdx, row ->
        row.mapIndexed { colIdx, seat ->
            if (seat == '.') {
                '.'
            } else {
                val adjOccupied = occupied(rowIdx-1, colIdx-1) +
                        occupied(rowIdx, colIdx-1) +
                        occupied(rowIdx+1, colIdx-1) +
                        occupied(rowIdx-1, colIdx) +
                        occupied(rowIdx+1, colIdx) +
                        occupied(rowIdx-1, colIdx+1) +
                        occupied(rowIdx, colIdx+1) +
                        occupied(rowIdx+1, colIdx+1)


                if (seat == 'L' && adjOccupied == 0) {
                    '#'
                } else if (seat == '#' && adjOccupied >= 4) {
                    'L'
                } else {
                    seat
                }
            }
        }
    }
}

fun main() {
    val content = Class::class.java.getResource("/d11/d11p1").readText()
    val list = content.split("\n").filter{it != ""}
        .map{ it.toList() }

    var lastSeats = list
    var curSeats = tick2(list)
    while (lastSeats != curSeats) {
        lastSeats = curSeats
        curSeats = tick2(curSeats)
    }

    println(curSeats.flatMap { it }.filter{it == '#'}.count())


}
