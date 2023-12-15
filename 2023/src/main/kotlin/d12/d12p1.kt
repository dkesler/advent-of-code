package d12

import utils.*

data class Springs(val arrangement: List<Char>, val groups: List<Int>) {
    fun legalArrangements(): Int {
        fun legalArrangementsFrom(arr: List<Char>, idx: Int): Int {
            if (idx == arrangement.size) {
                if (couldBeLegal(arr)) return 1 else return 0
            }
            if (arrangement[idx] != '?') {
              return legalArrangementsFrom(arr, idx + 1)
            }

            return legalArrangementsFrom(
                    listOf(arr.subList(0, idx), listOf('#'), arr.subList(idx+1, arr.size)).flatten(),
                    idx+1
            ) + legalArrangementsFrom(
                    listOf(arr.subList(0, idx), listOf('.'), arr.subList(idx+1, arr.size)).flatten(),
                    idx+1
            )
        }

        return legalArrangementsFrom(arrangement, 0)
    }

    fun couldBeLegal(arr: List<Char>): Boolean {
        val chunks = String(arr.toCharArray()).split(".").filter{it != ""}
                .map{it.length}
        return chunks == groups
    }
}

fun main() {
    val lines = readList("/d12.txt")

    val springs = lines.map {
        val s = it.split(" ")
        Springs(s[0].toList(), s[1].split(",").map { it.toInt() })
    }

    println(
            springs.map{it.legalArrangements()}.sum()
    )




}