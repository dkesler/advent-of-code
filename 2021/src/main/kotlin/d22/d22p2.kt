package d22
import utils.readList


data class Cube(val x: IntRange, val y: IntRange, val z: IntRange) {
    fun subCubes(other: Cube): List<Cube> {
        val newCubes = mutableListOf<Cube>()

        if (other.x.start > x.endInclusive || x.start > other.x.endInclusive)
            return listOf(this)

        if (other.y.start > y.endInclusive || y.start > other.y.endInclusive)
            return listOf(this)

        if (other.z.start > z.endInclusive || z.start > other.z.endInclusive)
            return listOf(this)

        //at this point we know we have some overlap in each dimension

        if (other.x.first > x.first) {
            if (other.x.first in x) {
                newCubes.add(Cube(IntRange(x.first, other.x.first-1), y, z))
            }
        }

        if (other.x.endInclusive < x.endInclusive) {
            if (other.x.endInclusive in x) {
                newCubes.add(Cube(IntRange(other.x.endInclusive+1, x.endInclusive), y, z))
            }
        }

        val xOverlap = getOverlap(x, other.x)

        if (other.y.first > y.first) {
            if (other.y.first in y) {
                newCubes.add(Cube(xOverlap, IntRange(y.first, other.y.first-1), z))
            }
        }

        if (other.y.endInclusive < y.endInclusive) {
            if (other.y.endInclusive in y) {
                newCubes.add(Cube(xOverlap,IntRange(other.y.endInclusive+1, y.endInclusive), z))
            }
        }

        val yOverlap = getOverlap(y, other.y)

        if (other.z.first > z.first) {
            if (other.z.first in z) {
                newCubes.add(Cube(xOverlap, yOverlap, IntRange(z.first, other.z.first-1)))
            }
        }

        if (other.z.endInclusive < z.endInclusive) {
            if (other.z.endInclusive in z) {
                newCubes.add(Cube(xOverlap, yOverlap, IntRange(other.z.endInclusive+1, z.endInclusive)))
            }
        }

        return newCubes.toList()
    }

    fun size(): Long {
        return (x.endInclusive - x.start + 1).toLong() * (y.endInclusive - y.start +1) * (z.endInclusive - z.start + 1)
    }
}

//assumes there is some overlap
fun getOverlap(a: IntRange, b: IntRange): IntRange {
    if (a.start in b && a.endInclusive in b) return a
    if (b.start in a && b.endInclusive in a) return b

    if (a.start in b) return IntRange(a.start, b.endInclusive)
    return IntRange(b.start, a.endInclusive)
}

fun main() {
    val lines = readList("/d22/d22p1").map{it.split(" ")}
        .map{ Pair(it[0], it[1].split(","))}
        .map{
            InitCommand(it.first == "on", toIntRange(it.second[0]), toIntRange(it.second[1]), toIntRange(it.second[2]))
        }
    println(lines)

    var onCubes = mutableListOf<Cube>()

    for (line in lines) {
        val newOnCubes = mutableListOf<Cube>()
        val newCube = Cube(line.x, line.y, line.z)
        for (cube in onCubes) {
            newOnCubes.addAll(cube.subCubes(newCube))
        }
        if (line.on) {
            newOnCubes.add(newCube)
        }
        onCubes = newOnCubes
    }

    println(onCubes.map{it.size()}.sum())
}
