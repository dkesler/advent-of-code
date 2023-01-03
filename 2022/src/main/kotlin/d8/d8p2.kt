package d8

import utils.*

fun getScenicScore(tree: Pair<Int, Int>, trees: List<List<Long>>): Long {
    var scenicScore = 1L
    val treesLeft = trees.indices.map { Pair(tree.first, it) }
            .filter { it.second < tree.second }
            .reversed()
            .map { trees[it.first][it.second] }
    var visibleLeft = treesLeft
            .takeWhile{ it < trees[tree.first][tree.second] }
            .count()
    if (treesLeft.any{ it >= trees[tree.first][tree.second] }) visibleLeft += 1
    scenicScore *= visibleLeft

    val treesRight = trees.indices.map { Pair(tree.first, it) }
            .filter { it.second > tree.second }
            .map { trees[it.first][it.second] }
    var visibleRight = treesRight
            .takeWhile{ it < trees[tree.first][tree.second]}
            .count()
    if (treesRight.any{ it >= trees[tree.first][tree.second] }) visibleRight += 1
    scenicScore *= visibleRight

    val treesUp = trees[0].indices.map { Pair(it, tree.second) }
            .filter { it.first < tree.first }
            .reversed()
            .map { trees[it.first][it.second] }
    var visibleUp = treesUp
            .takeWhile{ it < trees[tree.first][tree.second]}
            .count()
    if (treesUp.any{ it >= trees[tree.first][tree.second] }) visibleUp += 1
    scenicScore *= visibleUp


    val treesDown = trees[0].indices.map { Pair(it, tree.second) }
            .filter { it.first > tree.first }
            .map { trees[it.first][it.second] }
    var visibleDown = treesDown
            .takeWhile{ it < trees[tree.first][tree.second]}
            .count()
    if (treesDown.any{ it >= trees[tree.first][tree.second] }) visibleDown += 1
    scenicScore *= visibleDown

    return scenicScore
}


fun main() {
    val trees = readLongGrid("/d8.txt")

    val count = trees.indices.flatMap { x ->
        trees[0].indices.map { y -> Pair(x, y) }
    }.map{
        getScenicScore(it, trees)
    }

    println(count.max())
}
