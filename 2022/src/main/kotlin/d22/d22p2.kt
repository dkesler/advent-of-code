package d22

import utils.readCharGrid
import utils.readList

fun main() {
    val unpaddedMap = readCharGrid("/d22map.txt")
    val mapWidth = unpaddedMap.map{ it.size }.maxOrNull()!!
    val map = unpaddedMap.map {
        if (it.size < mapWidth)
            it + List(mapWidth - it.size) { ' ' }
        else
            it
    }
/*
_AB
_C_
DE_
F__
 */


    val edgeMap = mutableMapOf<Triple<Int, Int, Facing>, Triple<Int, Int, Facing>>()
    //Aleft->Dleft~
    //Dl->Al~
    (0..49).forEach{
        edgeMap[Triple(it, 50, Facing.LEFT)] = Triple(149-it, 0, Facing.RIGHT)
        edgeMap[Triple(149-it, 0, Facing.LEFT)] = Triple(it, 50, Facing.RIGHT)
    }
    //Atop->Fleft
    //Fl->At
    (50..99).forEach{
        edgeMap[Triple(0, it, Facing.UP)] = Triple(100+it, 0, Facing.RIGHT)
        edgeMap[Triple(100+it, 0, Facing.LEFT)] = Triple(0, it, Facing.DOWN)
    }
    //Bt->Fd
    //Fd->Bt
    (100..149).forEach{
        edgeMap[Triple(0, it, Facing.UP)] = Triple(199, it - 100, Facing.UP)
        edgeMap[Triple(199, it-100, Facing.DOWN)] = Triple(0, it, Facing.DOWN)
    }
    //Br->Er~
    //Er->Br~
    (0..49).forEach{
        edgeMap[Triple( it, 149, Facing.RIGHT)] = Triple(149-it, 99, Facing.LEFT)
        edgeMap[Triple( 149-it, 99, Facing.RIGHT)] = Triple(it, 149, Facing.LEFT)
    }
    //Bd->Cr
    //Cr->Bd
    (100..149).forEach{
        edgeMap[Triple(49, it, Facing.DOWN)] = Triple(it-50, 99, Facing.LEFT)
        edgeMap[Triple(it-50, 99, Facing.RIGHT)] = Triple(49,  it, Facing.UP)
    }
    //Cl->Dt
    //Dt->Cl
    (50..99).forEach {
        edgeMap[Triple(it, 50, Facing.LEFT)] = Triple(100, it - 50, Facing.DOWN)
        edgeMap[Triple(100, it-50, Facing.UP)] = Triple(it, 50, Facing.RIGHT)
    }

    //Ed->Fr
    //Fr->Ed
    (50..99).forEach {
        edgeMap[Triple(149, it, Facing.DOWN)] = Triple(100 + it, 49, Facing.LEFT)
        edgeMap[Triple(100+it, 49, Facing.RIGHT)] = Triple(149, it, Facing.UP)
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
                var nextFacing = facing
                if (next.first < 0 || next.first >= map.size || next.second < 0 || next.second >= map[0].size || map[next.first][next.second] == ' ') {
                    val n = edgeMap[Triple(pos.first, pos.second, facing)]!!
                    next = Pair(n.first, n.second)
                    nextFacing = n.third
                }

                if (map[next.first][next.second] == '.') {
                    pos = next
                    facing = nextFacing
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
