package d4

fun main() {
    val lines = Class::class.java.getResource("/d4/d4p1").readText().split("\n")
    val calls = lines[0].split(",")

    var boards = parseBoards(lines.subList(1, lines.size))

    var callsIdx = 0
    var lastWon: Board? = null
    var lastWinningCall: Int? = null
    while (boards.size > 0 && callsIdx < calls.size) {
        val winners = boards.filter { it.won() }
        if (winners.size > 0) {
            lastWon = winners.first()
            lastWinningCall = calls[callsIdx-1].toInt()
        }
        boards = boards.filter{!it.won()}
        boards = boards.map{it.withCall(calls[callsIdx])}
        callsIdx++
    }

    if (lastWon != null && lastWinningCall != null) {
        println(lastWon.unmarkedTotal() * lastWinningCall)
    }
}
