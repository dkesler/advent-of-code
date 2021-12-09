package d9
import utils.readList
import utils.readLongGrid

fun main() {
    val g = readLongGrid("/d9/d9p1")

    val m = g.mapIndexed{ rowIdx, row ->
        row.mapIndexed{ colIdx, cell ->

            if (rowIdx > 0 && g[rowIdx-1][colIdx] <= cell) 0
            else if (rowIdx < g.size-1 && g[rowIdx+1][colIdx] <= cell) 0
            else if (colIdx > 0 && g[rowIdx][colIdx-1] <= cell) 0
            else if (colIdx < row.size-1 && g[rowIdx][colIdx+1] <= cell) 0
            else cell+1
        }
    }

    println(m.flatten().sum())

}
