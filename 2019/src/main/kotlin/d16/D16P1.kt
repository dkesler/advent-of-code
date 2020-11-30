package d16

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("2019/src/main/resources/d16/d16p1").readLines()
        .get(0)
        .trim()
        .split("")
        .filter{ it.isNotBlank() }
        .map{ Integer.parseInt(it) }

    val output = (1..100).fold(input, {acc, i -> fft2(acc) })

    println(output)
}

fun fft(input: List<Int>): List<Int> {
    val output = input.mapIndexed { idx, value -> genIthOutput(idx, input) }
    val modOutput = output.map{ abs(it) % 10 }
    println(modOutput)
    return modOutput
}


fun fft2(input: List<Int>): List<Int> {
    val quarter = input.size / 4

    val output = ArrayList<Int>(input.size).toMutableList()

    for (i in 0..(quarter-1)) {
        //output.add(genIthOutput(i, input))
        output.add(0)
    }

    var oneStartIdx = quarter
    var oneEndIdx = 2*quarter + 1
    var negStartIdx = 3*quarter + 2

    var runningTotal = input.subList(quarter, oneEndIdx).sum() - input.subList(negStartIdx, input.size).sum()

    for (i in quarter..(input.size-1)) {
        output.add(runningTotal)
        runningTotal -= input[i]
        if (oneEndIdx+1 < input.size) {
            runningTotal += input[oneEndIdx+1]
        }
        if (oneEndIdx < input.size) {
            runningTotal += input[oneEndIdx]
        }
        if (negStartIdx < input.size) {
            runningTotal += input[negStartIdx]
        }
        if (negStartIdx+1 < input.size) {
            runningTotal += input[negStartIdx+1]
        }
        if (negStartIdx+2 < input.size) {
            runningTotal += input[negStartIdx+2]
        }

        oneEndIdx += 2
        negStartIdx += 3
    }
    val modOutput = output.map{ abs(it) % 10 }
    //println(modOutput)
    return modOutput
}


fun genIthOutput(i: Int, input: List<Int>): Int {
    if (i % 1000 == 0) {
        println("generating $i th output")
    }
    return input.subList(i, input.size).mapIndexed{ pidx, value -> value * genMultiplier(i, pidx+i) }.sum()
}

//i: the ith value of the output
//pidx: the index within the input
//0,  1,  0, -1,  0,  1,  0, -1,  0
//0,  0,  1,  1,  0,  0, -1, -1,  0
//0,  0,  0,  1,  1,  1,  0,  0,  0, -1, -1, -1
//0,  0,  0,  0,  1,  1,  1,  1,  0,  0,  0,  0,  -1,  -1,  -1,  -1
fun genMultiplier(i: Int, pidx: Int): Int {
    val x = (pidx+1)/(i+1) % 4
    if (x == 1) return 1
    if (x == 3) return -1
    return 0
}


