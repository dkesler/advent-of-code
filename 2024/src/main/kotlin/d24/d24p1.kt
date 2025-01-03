package d24

import utils.*

data class Gate(val in1: String, val in2: String, val op: String, val out: String)
fun main() {
    val blocks = readBlocks("/d24.txt")

    val wireVals = blocks[0].map{ it.split(":") }.map{ Pair(it[0], it[1].trim().toInt())}.toMap().toMutableMap()
    //mfp XOR spf -> z26
    val gates = blocks[1].map{ it.split(" ")}.map{ Gate(it[0], it[2] ,it[1], it[4])}

    val num = compute(gates, wireVals, mapOf())
    println(num)


}

fun compute(gates: List<Gate>, wireVals: MutableMap<String, Int>, swaps: Map<Gate, Gate>): Long {
    var changed = true
    while (changed) {
        changed = false
        for (gate in gates) {
            val swapGate = if (gate in swaps.keys) {
                swaps[gate]!!
            } else {
                gate
            }
            if (gate.in1 in wireVals.keys && gate.in2 in wireVals.keys && swapGate.out !in wireVals.keys) {
                changed = true
                val newVal =  if (gate.op == "AND") {
                    if (wireVals[gate.in1]!! == 1 && wireVals[gate.in2]!! == 1) 1 else 0
                } else  if (gate.op == "OR") {
                    if (wireVals[gate.in1]!! == 1 || wireVals[gate.in2]!! == 1) 1 else 0
                } else {
                    if (wireVals[gate.in1]!! == wireVals[gate.in2]!!) 0 else 1
                }
                wireVals[swapGate.out] = newVal
            }
        }
    }

    val keys = wireVals.keys.filter { it.startsWith("z") }.sorted().reversed()
    val binNum = keys.map { wireVals[it]!! }.joinToString("")
    val num = binNum.toLong(2)
    return num
}