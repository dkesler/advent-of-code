package d11

import utils.*
fun main() {
    var g = readLongGrid("/d11/d11p1")
    var tick = 0
    var synced = false
    while(!synced) {
        g = tick(g)
        tick++
        if (g.flatten().count { it == 0L } == 100) {
            synced = true
        }
    }
    println(tick)
}
