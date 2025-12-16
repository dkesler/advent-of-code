package d12

import utils.Grids
import utils.Point
import utils.readBlocks

data class Shape(val occupied: Set<Point>) {
    fun versions(): Set<Shape> {
        val rot90 = rot90()
        val rot180 = rot90.rot90()
        val rot270 = rot180.rot90()
        return setOf(
            this,
            rot90,
            rot180,
            rot270,
            hFlip(),
            vFlip(),
            rot90.hFlip(),
            rot90.vFlip(),
            rot180.hFlip(),
            rot180.vFlip(),
            rot270.hFlip(),
            rot270.vFlip()
        )
    }
    private fun hFlip(): Shape {
        return Shape(occupied.map{ Point(it.row, 2 - it.col) }.toSet())
    }
    private fun vFlip(): Shape {
        return Shape(occupied.map{ Point(2 - it.row, it.col) }.toSet())
    }
    private fun rot90(): Shape {
        //col -> row, row -> 2 - col
        return Shape(occupied.map{ Point(2 - it.col, it.row) }.toSet())
    }
}

data class Space(val rows: Int, val cols: Int, val shapesNeeded: List<Int>)
data class State(val occupied: Set<Point>, val placed: List<Int>) {
    fun place(shapeIdx: Int, shape: Shape, rowIdx: Int, colIdx: Int): State {
        return State(
            occupied + shape.occupied.map{ Point(it.row + rowIdx, it.col + colIdx) }.toSet(),
            placed.mapIndexed{ idx, p -> if (idx == shapeIdx) p + 1 else p }
        )
    }
}

fun main() {
    val blocks = readBlocks("/d12.txt")
    val shapes = toShapes(blocks.subList(0, blocks.size-1))
    val spaces = toSpaces(blocks[blocks.size-1])

    println(
        spaces.filter{ canFit(it, shapes) }.count()
    )
}

fun printState(space: Space, state: State) {
    for (row in 0 until space.rows) {
        for (col in 0 until space.cols) {
            if (Point(row, col) in state.occupied) print("#") else print(".")
        }
        println()
    }
    println()
    println()
}

fun canFit(space: Space, shapes: List<Shape>): Boolean {
    var loopCalls = 0
    var backtracks = 0
    var cantFits = 0
    var memoTableHits = 0
    var culledMemoTableHits = 0
    fun getNW3x3(point: Point): Set<Point> {
        return (0..2).flatMap{ drow ->
            (0..2).map{ dcol ->
                Point(point.row - drow, point.col-dcol)
            }
        }.toSet()
    }

    fun getPlacesToCheck(state: State): List<Point> {
        val points = mutableListOf<Point>()
        for (row in 0 until space.rows) {
            for (col in 0 until space.cols) {
                points.add(Point(row, col))
                if (row >= 2 && col >= 2) {
                    if (getNW3x3(Point(row, col)).none{ state.occupied.contains(it)}) {
                        return points
                    }
                }
            }
        }

        return points
    }

    fun placeable(row: Int, col: Int, shape: Shape, state: State): Boolean {
        val newOccupied = shape.occupied.map{ Point(it.row + row, it.col + col) }
        if (newOccupied.any{ it in state.occupied }) return false
        if (newOccupied.any{ it.row >= space.rows || it.row < 0 || it.col >= space.cols || it.col < 0}) return false
        return true
    }


    fun cullState(state: State): State {
        val placeable = mutableSetOf<Point>()
        for (shapeIdx in shapes.indices) {
            if (state.placed[shapeIdx] < space.shapesNeeded[shapeIdx]) {
                for (version in shapes[shapeIdx].versions()) {
                    for (rowIdx in 0 until space.rows) {
                        for (colIdx in 0 until space.cols) {
                            if (placeable(rowIdx, colIdx, version, state)) {
                                placeable.addAll( version.occupied.map{ Point(it.row + rowIdx, it.col + colIdx)})
                            }
                        }
                    }
                }
            }
        }

        val unplaceable = mutableSetOf<Point>()
        for (rowIdx in 0 until space.rows) {
            for (colIdx in 0 until space.cols) {
                if (Point(rowIdx, colIdx) !in placeable) unplaceable.add(Point(rowIdx, colIdx))
            }
        }

        return State(state.occupied + unplaceable, state.placed)

    }

    fun isNWCornerOfEmpty3x3(pt: Point, state: State): Boolean {
        if (pt.row <= space.rows-3) {
            if (pt.col < space.cols-3) {
                val pointsToCheck = (0..2).flatMap{ drow ->
                    (0..2).map{ dcol ->
                        Point(pt.row + drow, pt.col+dcol)
                    }
                }
                if (pointsToCheck.none{ state.occupied.contains(it) }) return true
            }
        }
        return false
    }

    val memoTable = mutableMapOf<State, Boolean>()

    fun canFitLoop(state: State, candidates: List<Point>): Boolean {
        loopCalls++
        if (state in memoTable) {
            memoTableHits++
            return memoTable[state]!!
        }

        if (state.placed == space.shapesNeeded) {
//            printState(space, state)
            return true
        }

        //printState(space, state)

        val culledState = cullState(state)

        if (culledState in memoTable) {
            culledMemoTableHits++
            return memoTable[culledState]!!
        }


        val remainingToPlace = space.shapesNeeded.zip(culledState.placed)
            .map{ it.first - it.second }
            .withIndex()
            .map{ it.value * shapes[it.index].occupied.size }
            .sum()
        val remainingPlaceable = space.rows * space.cols - culledState.occupied.size
        if (remainingToPlace > remainingPlaceable) {
            cantFits++
            memoTable[culledState] = false
            return false
        }


        //TODO: check memo table
        for (ptIdx in candidates.indices) {
            val pt = candidates[ptIdx]
            val finalChance = isNWCornerOfEmpty3x3(pt, state)

            for (shapeIdx in shapes.indices) {
                if (culledState.placed[shapeIdx] < space.shapesNeeded[shapeIdx]) {
                    for (version in shapes[shapeIdx].versions()) {
                        if (placeable(pt.row, pt.col, version, culledState)) {
                            val cutIdx = if (ptIdx >= 2) ptIdx - 2 else 0
                            val placedState = culledState.place(shapeIdx, version, pt.row, pt.col)
                            val truncatedState = State(placedState.occupied + candidates.subList(0, cutIdx), placedState.placed)
                            if (canFitLoop(truncatedState, candidates.subList(cutIdx, candidates.size))) {
                                memoTable[culledState] = true
                                return true
                            }
                        }
                    }
                }
            }
            if (finalChance) {
                backtracks++
                return false
            }
        }

        memoTable[culledState] = false
        return false
    }

    val candidates = (0 until space.rows).flatMap { row ->
        (0 until space.cols).map {
            Point(row, it)
        }
    }
    val res = canFitLoop(State(setOf(), shapes.map { 0 }), candidates)
    println("loops: $loopCalls, backtracks: $backtracks, cant fits: $cantFits, memotablehits: $memoTableHits, culledmemo table hits: $culledMemoTableHits")
    return res
}

fun toSpaces(strings: List<String>): List<Space> {
    return strings.map {
        val s = it.split(": ")
        val sizes = s[0].split("x")
        val reqs = s[1].split(" ").map{ it.toInt() }
        Space(sizes[1].toInt(), sizes[0].toInt(), reqs)
    }
}

fun toShapes(blocks: List<List<String>>): List<Shape> {
    return blocks.map{ toShape(it) }

}

fun toShape(block: List<String>): Shape {
    val shapeParts = block.subList(1, block.size)

    return Shape(
        Grids.pointValues(shapeParts.map { it.toCharArray().toList()})
            .filter{ it.value == '#' }
            .map{ it.toPoint() }
            .toSet()
    )
}
