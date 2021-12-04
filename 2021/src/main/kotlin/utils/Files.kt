package utils

//blocks/3d grid

fun readList(filename: String): List<String> {
    val content = Class::class.java.getResource(filename).readText()
    return content.split("\n").filter{it != ""}
}

fun readLongList(filename: String): List<Long> {
    val content = Class::class.java.getResource(filename).readText()
    return content.split("\n").filter{it != ""}.map{it.toLong()}
}

fun <T> readGrid(filename: String, gridSeparator: String = "", cellProcessor: (String)->T ): List<List<T>> {
    val content = Class::class.java.getResource(filename).readText()
    val list = content.split("\n").filter{it != ""}
    return list.map{it.split(gridSeparator).filter{it != ""}.map(cellProcessor)}
}

fun readCharGrid(filename: String, gridSeparator: String = ""): List<List<Char>> {
    return readGrid(filename, gridSeparator, ::toChar)
}

fun readLongGrid(filename: String, gridSeparator: String = ""): List<List<Long>> {
    return readGrid(filename, gridSeparator, String::toLong)
}

fun toChar(str: String): Char {
    if (str.length != 1) {
        println("String [$str] is not length 1")
    }
    return str[0]
}



