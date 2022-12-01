package utils

fun readBlocks(filename: String): List<List<String>> {
    val blocks = mutableListOf<List<String>>()
    var currentBlock = mutableListOf<String>()
    val content = {}.javaClass.getResource(filename)!!.readText()
    for (line in content.split("\n")) {
        if (line == "" && currentBlock.isNotEmpty()) {
            blocks.add(currentBlock.toList())
            currentBlock = mutableListOf()
        } else {
            currentBlock.add(line)
        }
    }

    return blocks.toList()
}

fun readLongBlocks(filename: String): List<List<Long>> {
    return readBlocks(filename).map{ it.map{ it.toLong() } }
}

fun <T> read3dGrid(filename: String, gridSeparator: String = "", cellProcessor: (String)->T ):  List<List<List<T>>> {
    val blocks = readBlocks(filename)
    return blocks.map{ block ->
        block.map{it.split(gridSeparator).filter{it != ""}.map(cellProcessor)}
    }
}

fun read3dCharGrid(filename: String):  List<List<List<Char>>> {
    return read3dGrid(filename, "", ::toChar)
}

fun read3dLongGrid(filename: String):  List<List<List<Long>>> {
    return read3dGrid(filename, "", String::toLong)
}

fun readList(filename: String): List<String> {
    val content = {}.javaClass.getResource(filename)!!.readText()
    return content.split("\n").filter{it != ""}
}

fun readLongList(filename: String): List<Long> {
    val content = {}.javaClass.getResource(filename)!!.readText()
    return content.split("\n").filter{it != ""}.map{it.toLong()}
}

fun <T> readGrid(filename: String, gridSeparator: String = "", cellProcessor: (String)->T ): List<List<T>> {
    val content = {}.javaClass.getResource(filename)!!.readText()
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



