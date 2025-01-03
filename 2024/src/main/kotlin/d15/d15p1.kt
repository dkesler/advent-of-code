package d15

import utils.*

fun main() {
    val grid = readCharGrid("/d15.txt")

    val boxes = Grids.pointValues(grid).filter{it.value == 'O' }.map{it.toPoint()}.toSet().toMutableSet()
    val walls = Grids.pointValues(grid).filter{it.value == '#' }.map{it.toPoint()}.toSet()
    var robot = Grids.pointValues(grid).filter{it.value == '@' }.map{it.toPoint()}.first()

    val moves = readList("/d15-2.txt")

    for (line in moves) {
        for (move in line) {
            if (move == '>') {
                robot = maybeMoveBoxes(robot, boxes, walls) { pv -> Point(pv.row, pv.col + 1) }
            } else if(move == 'v') {
                robot = maybeMoveBoxes(robot, boxes, walls) { pv -> Point(pv.row+1, pv.col) }
            } else if (move == '<') {
                robot = maybeMoveBoxes(robot, boxes, walls) { pv -> Point(pv.row, pv.col-1) }
            } else {
                robot = maybeMoveBoxes(robot, boxes, walls) { pv -> Point(pv.row-1, pv.col) }
            }
        }
    }

    println(boxes.map{ it.row * 100 + it.col }.sum())


}

private fun maybeMoveBoxes(robot: Point, boxes: MutableSet<Point>, walls: Set<Point>, nextMap: (Point) -> Point): Point {
    val affected = mutableSetOf<Point>()
    var lastAffected = robot
    while (nextMap(lastAffected) in boxes) {
        val next = nextMap(lastAffected)
        affected.add(next)
        lastAffected = next
    }
    if (nextMap(lastAffected) !in walls) {
        boxes.removeAll(affected)
        boxes.addAll(affected.map(nextMap))
        return nextMap(robot)
    }

    return robot
}
