package d20

import utils.*

fun main() {
    val enhancementAlgorithm = readList("/d20/d20p1")[0]
    val image = readCharGrid("/d20/d20p2")
    val pixels = image.indices.map{ r -> image[r].indices.map{ c -> Pair(r, c)}}.flatten().filter {
        image[it.first][it.second] == '#'
    }.toSet()

    val enh1 = enhance(enhancementAlgorithm, pixels, '0')
    val enhanced = enhance(enhancementAlgorithm, enh1, '1')
    println(enhanced.size)

    val enhanced50 = (1..25).fold(pixels, { img, run ->
        enhance(enhancementAlgorithm, enhance(enhancementAlgorithm, img, '0'), '1')
    })
    println(enhanced50.size)

}

fun printImage(image: Set<Pair<Int, Int>>) {
    val minX = image.map{it.first}.minOrNull()!!-1
    val maxX = image.map{it.first}.maxOrNull()!!+1
    val minY = image.map{it.second}.minOrNull()!!-1
    val maxY = image.map{it.second}.maxOrNull()!!+1
    for (x in (minX..maxX)) {
        for (y in (minY..maxY)) {
            if (Pair(x, y) in image) {
                print("#")
            } else {
                print(".")
            }
        }
        println("")
    }
    println("")
    println("")

}


fun enhance(algo: String, image: Set<Pair<Int, Int>>, outerBoundsChar: Char): Set<Pair<Int, Int>> {
    val minX = image.map{it.first}.minOrNull()!!-1
    val maxX = image.map{it.first}.maxOrNull()!!+1
    val minY = image.map{it.second}.minOrNull()!!-1
    val maxY = image.map{it.second}.maxOrNull()!!+1

    val enhanced = mutableSetOf<Pair<Int, Int>>()

    for (x in (minX..maxX)) {
        for (y in (minY..maxY)) {
            val adj = listOf(
                Pair(x-1, y-1),
                Pair(x-1, y),
                Pair(x-1, y+1),
                Pair(x, y-1),
                Pair(x, y),
                Pair(x, y+1),
                Pair(x+1, y-1),
                Pair(x+1, y),
                Pair(x+1, y+1),
            )

            val idx = ("0" + String(adj.map{ (r, c) ->
                val outOfBounds = r <= minX || r >= maxX || c <= minY || c >= maxY
                val char = if (outOfBounds) outerBoundsChar else if (image.contains(Pair(r, c))) '1' else '0'
                char
            }.toCharArray())).toInt(2)
            if (algo[idx] == '#') {
                enhanced.add(Pair(x, y))
            }
        }
    }

    return enhanced.toSet()

}
