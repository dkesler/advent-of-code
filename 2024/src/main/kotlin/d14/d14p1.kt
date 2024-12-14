package d14

import utils.*

data class Robot(val row: Long, val col: Long, val vRow: Long, val vCol: Long)

fun main() {
    var robots = readList("/d14.txt").map{
        val split = it.split(" ")
        val pos=split[0].split("=")[1].split(",")
        val col = pos[0].toLong()
        val row = pos[1].toLong()
        val v = split[1].split("=")[1].split(",")
        val vCol = v[0].toLong()
        val vRow = v[1].toLong()
        Robot(row, col, vRow, vCol)
    }
    val rowMax = 102
    val colMax = 100

    for (s in 1..7672) {
        val newRobots = robots.map{

            Robot( (it.row + it.vRow + rowMax + 1) % (rowMax + 1), (it.col + it.vCol + colMax + 1) % (colMax + 1), it.vRow, it.vCol )
        }

        robots = newRobots

        //Part 2:  looked at locations second by second.  Noticed horizontal pattern appeared at s=50 and repeating
        //every 103 seconds, vertical pattern appearing at s=97 and repeating every 101 seconds.  Used wolfram alpha
        //to solve 50 + 103*x == 97 + 101 * y, picked the lowest integer solution for both, and verified the christmas
        //tree got printed
/*
        println(s)
        for (row in 0L..rowMax) {
            for (col in 0L..colMax) {
                val roboCount = robots.filter { it.row == row && it.col == col }.count()
                print( if (roboCount == 0) " " else roboCount)
            }
            println("")
        }
*/
    }

    for (row in 0L..rowMax) {
        for (col in 0L..colMax) {
            val roboCount = robots.filter { it.row == row && it.col == col }.count()
            print( if (roboCount == 0) " " else roboCount)
        }
        println("")
    }

    val ul = robots.filter { it.row < rowMax/2 && it.col < colMax / 2  }.count()
    val ur = robots.filter { it.row < rowMax/2 && it.col > colMax / 2  }.count()
    val dr = robots.filter { it.row > rowMax/2 && it.col > colMax / 2  }.count()
    val dl = robots.filter { it.row > rowMax/2 && it.col < colMax / 2  }.count()

    println(ul * ur * dr * dl)
}
