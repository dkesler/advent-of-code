package d5

import utils.Ranges
import utils.readBlocks

fun main() {
    val lines = readBlocks("/d5.txt")

    val ranges = lines[0].map{
        val split = it.split("-")
        Ranges.rangeFrom(split[0].toLong(), split[1].toLong() )
    }

    val ingredients = lines[1].map{ it.toLong() }

    println(
        ingredients.filter{ isFresh(ranges, it) }.count()
    )
}

fun isFresh(ranges: List<Ranges.Range>, ingredient: Long): Boolean {
    return ranges.any{ it.contains(ingredient) }
}
