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
    /*
       x1 + vx1 * t = 0
       t = -x1 / vx1

       y + vy * t
       y + vy * -x1 / vx

       y = (vy / vx) * x + (y0 + vy/vx * -x0)

       y1 = m1 x1 + b1
       y2 = m2 x2 + b2
       m1 x + b1 = m2 x + b2
       m1 x- m2 x = b2 - b1
       x = (b2 - b1) / (m1 - m2)

       x0 + vx * t = x
       t = (x - x0) vx
     */

    var collisions = 0
    val targetRange = 200000000000000.0..400000000000000.0
    //val targetRange = 7.0..27.0
    for (i in stones.indices) {
        for (j in i+1..stones.size-1) {
            val si = stones[i]
            val sj = stones[j]

            //slope of the first line is rise over run
            val mi = (si.vy.toDouble() / si.vx)
            //intercept of the first line is derived from first finding the time at which x == 0:
            // x = x0 + vx * t = 0
            // t = -x0 / vx
            // then, find the y value at this time by substituting it into
            // y0 + vy * t
            // y0 + vy * -x0 / vx
            // y0 + m * -x0
            val bi = (si.y.toDouble() + mi * -si.x)
            val mj = (sj.vy.toDouble() / sj.vx)
            val bj = (sj.y.toDouble() + mj * -sj.x)
            if (mi - mj != 0.0) { //parallel, can't intercept
                //this is the x where the two lines intercept.  first, find the x value at the point when the y values are equal
                // y = m1 * x + b1
                // y = m2 * x + b2
                // m1 * x + b1 = m2 * x + b2
                // (m1 - m2) * x = b2 - b1
                // x = (b2 - b1) / (m1 - m2)
                val x = (bj - bi) / (mi - mj)
                //the y where the two lines intercept, just calculate from slope intercept form
                val y = mi * x + bi
                if (x in targetRange && y in targetRange) {
                    //derive the time each of the hailstones hit the intersection point
                    //and verify both are in the future
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