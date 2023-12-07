package d5

import utils.*
import java.lang.Long.max
import java.lang.Long.min

data class Mapper(val dStart: Long, val sStart: Long, val range: Long) {
    fun matches(s: Long):Boolean {
        return s in (sStart until sStart + range)
    }

    fun toDest(s: Long): Long {
        return (dStart - sStart) + s
    }

    fun rangeToDest(src: LongRange): Pair<LongRange?, List<LongRange>> {
        val mapperRange = (sStart until sStart + range)
        val intersection = max(src.start, mapperRange.start) .. min(src.last, mapperRange.last)

        if (intersection.start <= intersection.last) {
            val mapped = toDest(intersection.start)..toDest(intersection.last)
            if (src.first in mapperRange && src.last !in mapperRange) {
                return Pair(mapped, listOf((mapperRange.last +1..src.last)) )
            } else if (src.first in mapperRange && src.last in mapperRange) {
                return Pair(mapped, listOf())
            } else if (src.first !in mapperRange && src.last in mapperRange) {
                return Pair(mapped, listOf((src.first until mapperRange.first )) )
            } else {
                return Pair(mapped, listOf(
                        (src.first until mapperRange.first ),
                        (mapperRange.last + 1  .. src.last ),
                ))
            }
        } else {
            return Pair(null, listOf(src))
        }
    }
}


fun toMapper(it: String): Mapper {
    val s = it.split(" ").filter{it != ""}
    return Mapper(s[0].toLong(), s[1].toLong(), s[2].toLong())
}


fun main() {
    val blocks = readBlocks("/d5.txt")

    val seeds = blocks[0][0].split(":")[1].split(" ").filter{it != ""}.map{ it.toLong() }
    val mappers = blocks.subList(1, blocks.size).map{ block -> block.subList(1, block.size).map{ toMapper(it)} }

    println(
    seeds.map{seed ->
        mappers.fold(seed) { s, mappers ->
            if (mappers.any { it.matches(s) }) {
                mappers.first { it.matches(s) }.toDest(s)
            } else {
                s
            }
        }
    }.min()
    )


}
