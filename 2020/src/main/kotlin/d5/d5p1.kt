package d5

fun toOneOrZero(c: Char): Char {
    if (c == 'F' || c == 'L') {
        return '0'
    } else {
        return '1'
    }
}
fun Seat(str: String): Seat  {
    val rowStr = str.substring(0, 7)
    val colStr = str.substring(7, 10)
    return Seat(
        Integer.parseInt(String(rowStr.map(::toOneOrZero).toCharArray()), 2),
        Integer.parseInt(String(colStr.map(::toOneOrZero).toCharArray()), 2)
    )
}

data class Seat(val row: Int, val col: Int) {
    fun seatNum(): Int {
        return row * 8 + col
    }
}



fun main() {
    val content = Class::class.java.getResource("/d5/d5p1").readText()
    val list = content.split("\n").filter{it != ""}

    val seatNumSet = list.map(::Seat).map(Seat::seatNum).toSet()
    println(seatNumSet.max())

    for (i in 0..2048) {
        if (!seatNumSet.contains(i) && seatNumSet.contains(i-1) && seatNumSet.contains(i+1)) {
            println(i)
            break
        }
    }

}
