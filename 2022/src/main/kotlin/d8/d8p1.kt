package d8

import utils.*


fun isVisible(tree: Pair<Int, Int>, trees: List<List<Long>>): Boolean {
    if (
            trees.indices.map{ Pair(tree.first, it) }
                    .filter{ it.second < tree.second }
                    .map{ trees[it.first][it.second] }
                    .none{ it >= trees[tree.first][tree.second]}
    ) {
        return true
    }

    if (
            trees.indices.map{ Pair(tree.first, it) }
                    .filter{ it.second > tree.second }
                    .map{ trees[it.first][it.second] }
                    .none{ it >= trees[tree.first][tree.second]}
    ) {
        return true
    }

    if (
            trees[0].indices.map{ Pair(it, tree.second) }
                    .filter{ it.first < tree.first }
                    .map{ trees[it.first][it.second] }
                    .none{ it >= trees[tree.first][tree.second]}
    ) {
        return true
    }


    if (
            trees[0].indices.map{ Pair(it, tree.second) }
                    .filter{ it.first > tree.first }
                    .map{ trees[it.first][it.second] }
                    .none{ it >= trees[tree.first][tree.second]}
    ) {
        return true
    }

    return false
}


fun main() {
    val trees = readLongGrid("/d8.txt")

    val count = trees.indices.flatMap { x ->
        trees[0].indices.map { y -> Pair(x, y) }
    }.filter{
        isVisible(it, trees)
    }

    println(count.count())
}
