package d9

import utils.*

fun main() {
    val line = readList("/d9.txt")[0]


    var blockIdx = 0
    var maxMem = 0
    val blocks = mutableListOf<Triple<Int, Int, Int>>()
    val freeMem = mutableListOf<Pair<Int, Int>>()
    for (i  in line.indices) {
        if (i % 2 == 0) {
            blocks.add(Triple(blockIdx, maxMem, maxMem + line[i].toString().toInt() - 1))
            maxMem += line[i].toString().toInt()
            blockIdx++
        } else {
            freeMem.add(Pair(maxMem, maxMem + line[i].toString().toInt() - 1))
            maxMem += line[i].toString().toInt()
        }
    }
    val blocksView = blocks.toList()

    for (block in blocksView.reversed()) {
        freeMem.sortBy{ it.first }
        for (free in freeMem.toList()) {
            if (free.first < block.second) {
                if (free.second - free.first == block.third - block.second) {
                    freeMem.remove(free)
                    blocks.remove(block)
                    blocks.add(Triple(block.first, free.first, free.second))
                    break
                } else if (free.second - free.first > block.third - block.second) {
                    freeMem.remove(free)
                    freeMem.add(Pair(free.first+ (block.third - block.second)+1, free.second))
                    blocks.remove(block)
                    blocks.add(Triple(block.first, free.first, free.first + (block.third - block.second)))
                    break
                }
            }
        }
    }


    println(
        blocks.map{  b-> (b.second..b.third).map{ b.first * it.toLong() }.sum()  }.sum()
    )

}