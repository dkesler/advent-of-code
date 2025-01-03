package d15

import utils.*

fun main() {
    val grid = readCharGrid("/d15.txt")

    val leftBoxes = Grids.pointValues(grid).filter{it.value == 'O' }.map{it.toPoint()}.map{ Point(it.row, it.col*2)}.toMutableSet()
    val rightBoxes = leftBoxes.map{ Point(it.row, it.col+1) }.toMutableSet()
    val walls = Grids.pointValues(grid).filter{it.value == '#' }.map{it.toPoint()}.map{ Point(it.row, it.col*2)}.flatMap{ listOf(it, Point(it.row, it.col+1)) }.toSet()
    var robot = Grids.pointValues(grid).filter{it.value == '@' }.map{it.toPoint()}.map{ Point(it.row, it.col*2)}.first()

    val moves = readList("/d15-2.txt")

    for (line in moves) {
        for (move in line) {

            if (move == '>') {
                robot = maybeMoveBoxes(robot, leftBoxes, rightBoxes, walls) { pv -> Point(pv.row, pv.col + 1) }
            } else if(move == 'v') {
                robot = maybeMoveBoxes(robot, leftBoxes, rightBoxes, walls) { pv -> Point(pv.row+1, pv.col) }
            } else if (move == '<') {
                robot = maybeMoveBoxes(robot, leftBoxes, rightBoxes, walls) { pv -> Point(pv.row, pv.col-1) }
            } else {
                robot = maybeMoveBoxes(robot, leftBoxes, rightBoxes, walls) { pv -> Point(pv.row-1, pv.col) }
            }
        }
    }

    println(leftBoxes.map{ it.row * 100 + it.col }.sum())
}

private fun maybeMoveBoxes(robot: Point, leftBoxes: MutableSet<Point>, rightBoxes: MutableSet<Point>, walls: Set<Point>, nextMap: (Point) -> Point): Point {
    val affected = mutableSetOf<Point>()
    var toPropagate = mutableSetOf(robot)
    while(toPropagate.isNotEmpty()) {
        val cur = toPropagate.first()
        toPropagate.remove(cur)
        val next = nextMap(cur)

        if ((next in leftBoxes || next in rightBoxes) && next !in affected) {
            toPropagate.add(next)
            affected.add(next)

            if (next in leftBoxes) {
                val correspondingRightBox = Point(next.row, next.col + 1)
                if (correspondingRightBox !in affected) {
                    toPropagate.add(correspondingRightBox)
                    affected.add(correspondingRightBox)
                }
            }

            if (next in rightBoxes) {
                val correspondingLeftBox = Point(next.row, next.col - 1)
                if (correspondingLeftBox !in affected) {
                    toPropagate.add(correspondingLeftBox)
                    affected.add(correspondingLeftBox)
                }
            }
        }
    }

    if (nextMap(robot) !in walls && affected.all{ nextMap(it) !in walls }) {
        val leftToMove = affected.filter{ it in leftBoxes }
        leftBoxes.removeAll(leftToMove.toSet())
        leftBoxes.addAll(leftToMove.map(nextMap))
        val rightToMove = affected.filter{ it in rightBoxes }
        rightBoxes.removeAll(rightToMove.toSet())
        rightBoxes.addAll(rightToMove.map(nextMap))

        return nextMap(robot)
    }

    return robot
}