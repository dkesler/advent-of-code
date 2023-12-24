package d24

import utils.*

data class Hailstone(val x: Long, val y: Long, val z: Long, val vx: Long, val vy: Long, val vz: Long)
fun main() {
    val lines = readList("/d24.txt")
    val stones = lines.map {
        val s = it.split("@").map{it.trim()}
        val l = s[0].split(",").map{it.trim()}
        val r = s[1].split(",").map{it.trim()}
        Hailstone(l[0].toLong(), l[1].toLong(), l[2].toLong(), r[0].toLong(), r[1].toLong(), r[2].toLong())
    }

    var collisions = 0
    val targetRange = 200000000000000.0..400000000000000.0
    //val targetRange = 7.0..27.0
    for (i in stones.indices) {
        for (j in i+1..stones.size-1) {
            val si = stones[i]
            val sj = stones[j]

            val mi = (si.vy.toDouble() / si.vx)
            val bi = (si.y.toDouble() + mi * -si.x)
            val mj = (sj.vy.toDouble() / sj.vx)
            val bj = (sj.y.toDouble() + mj * -sj.x)
            if (mi - mj != 0.0) {
                val x = (bj - bi) / (mi - mj)
                val y = mi * x + bi
                if (x in targetRange && y in targetRange) {
                    val ti = (x - si.x) / si.vx
                    val tj = (x - sj.x) / sj.vx
                    if (ti > 0 && tj > 0) {
                        collisions++
                    }
                }
            }
        }
    }
    println(collisions)
}