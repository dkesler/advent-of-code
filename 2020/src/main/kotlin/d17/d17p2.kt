package d17

data class Grid2(
    val grid: Map<Int, Map<Int, Map<Int, Map<Int, Char>>>>,
    val minX: Int,
    val maxX: Int,
    val minY: Int,
    val maxY: Int,
    val minZ: Int,
    val maxZ: Int,
    val minW: Int,
    val maxW: Int
    ) {

    fun tick(): Grid2 {
        val newMap = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, Char>>>>()
        var newMinX = minX
        var newMaxX = maxX
        var newMinY = minY
        var newMaxY = maxY
        var newMinZ = minZ
        var newMaxZ = maxZ
        var newMinW = minW
        var newMaxW = maxW


        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    for (w in minW..maxW) {
                        val at = get(x, y, z, w)
                        if (at == '.') {
                            if (numActiveNeighbors(x, y, z, w) == 3) {
                                put(x, y, z, w, '#', newMap)
                                newMinX = if (newMinX < x-1) newMinX else x-1
                                newMaxX = if (newMaxX > x+1) newMaxX else x+1
                                newMinY = if (newMinY < y-1) newMinY else y-1
                                newMaxY = if (newMaxY > y+1) newMaxY else y+1
                                newMinZ = if (newMinZ < z-1) newMinZ else z-1
                                newMaxZ = if (newMaxZ > z+1) newMaxZ else z+1
                                newMinW = if (newMinW < w-1) newMinW else w-1
                                newMaxW = if (newMaxW > w+1) newMaxW else w+1

                            } else {
                                put(x, y, z, w, '.', newMap)
                            }
                        } else if (at == '#') {
                            if (!(numActiveNeighbors(x, y, z, w) in 2..3)) {
                                put(x, y, z, w, '.', newMap)
                            } else {
                                put(x, y, z, w, '#', newMap)
                                newMinX = if (newMinX < x-1) newMinX else x-1
                                newMaxX = if (newMaxX > x+1) newMaxX else x+1
                                newMinY = if (newMinY < y-1) newMinY else y-1
                                newMaxY = if (newMaxY > y+1) newMaxY else y+1
                                newMinZ = if (newMinZ < z-1) newMinZ else z-1
                                newMaxZ = if (newMaxZ > z+1) newMaxZ else z+1
                                newMinW = if (newMinW < w-1) newMinW else w-1
                                newMaxW = if (newMaxW > w+1) newMaxW else w+1
                            }
                        }
                    }
                }
            }
        }

        return Grid2(
            newMap,
            newMinX,
            newMaxX,
            newMinY,
            newMaxY,
            newMinZ,
            newMaxZ,
            newMinW,
            newMaxW

        )
    }

    fun get(x: Int, y: Int, z: Int, w: Int): Char {
        return grid.get(x)?.get(y)?.get(z)?.get(w) ?: '.'
    }

    fun put(x: Int, y: Int, z: Int, w: Int, c: Char, newMap: MutableMap<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, Char>>>>) {
        val xMap = newMap.getOrPut(x, { mutableMapOf() })
        val yMap = xMap.getOrPut(y, { mutableMapOf() })
        val zMap = yMap.getOrPut(z, { mutableMapOf() })
        zMap.put(w, c)
    }

    fun numActiveNeighbors(x: Int, y: Int, z: Int, w: Int): Int {
        var numActive = 0
        for (ix in x-1..x+1) {
            for (iy in y-1..y+1) {
                for (iz in z-1..z+1) {
                    for (iw in w-1..w+1) {
                        if (ix != x || iy != y || iz != z || iw != w) {
                            if (get(ix, iy, iz, iw) == '#') {
                                numActive++
                            }
                        }
                    }
                }
            }
        }
        return numActive
    }

    fun print() {
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    for (w in minW..maxW) {
                        print(get(x, y, z, w))
                    }
                    println()
                }
                println()
                println()
            }
            println()
            println()
            println()
        }
    }

    fun totalActive(): Long {
        var active = 0L
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    for (w in minW..maxW) {
                        if (get(x, y, z, w) == '#') {
                            active++
                        }
                    }
                }
            }
        }
        return active
    }

}

fun main() {
    val content = Class::class.java.getResource("/d17/d17p1").readText()
    val list = content.split("\n").filter{it != ""}.map{it.toCharArray().toList()}

    val map = list.map { it.mapIndexed { i, char -> Pair(i, char) }.toMap() }
    val pairs = map.mapIndexed { i, line -> Pair(i, line) }.toMap()
    val grid = Grid2(
        mapOf(Pair(
            0,
            mapOf(Pair(0, pairs))
        )),
        -1,
        1,
        -1,
        1,
        -1,
        pairs.keys.size,
        -1,
        pairs.getValue(0).keys.size
    )

    val finalGrid = grid.tick().tick().tick().tick().tick().tick()

    //finalGrid.print()

    println(finalGrid.totalActive())





}
