package d18

fun findOpen(line: String): Int {
    var closeCount = 1
    var i = 1
    while (closeCount > 0) {
        if (line[i] == '(') {
            closeCount -= 1
        } else if (line[i] == ')') {
            closeCount += 1
        }

        i++
    }

    return i-1
}

fun findClose(line: String): Int {
    var openCount = 1
    var i = 1
    while (openCount > 0) {
        if (line[i] == '(') {
            openCount += 1
        } else if (line[i] == ')') {
            openCount -= 1
        }

        i++
    }

    return i-1
}


fun doOp(op: Char, first: Long, second: Long): Long {
    if (op == '+') {
        return first + second
    } else {
        return first * second
    }
}

fun calculate(line: String): Long {
    if (line.length == 1) {
        return line[0].toString().toLong()
    }
    if (line[0] == ')') {
        val idxOfOpen = findOpen(line)
        val first = calculate(line.substring(1, idxOfOpen))
        if (idxOfOpen == line.length-1) {
            return first
        }
        val op = line[idxOfOpen+1]
        val second = calculate(line.substring(idxOfOpen+2))
        return doOp(op, first, second)
    } else {
        val first = line[0].toString().toLong()
        val op = line[1]
        val second = calculate(line.substring(2))
        return doOp(op, first, second)
    }
}

fun nextToken(line: String): String {
    if (line[0] != '(') {
        return line[0].toString()
    }

    val closeIdx = findClose(line)

    return line.substring(1, closeIdx)
}

//i should've just done it this way to begin with
fun calculate2(line: String): Long {
    if (line.length == 1) {
        return line.toLong()
    }

    val tokens = mutableListOf<Long>()
    val ops = mutableListOf<Char>()
    var idx = 0

    val nextToken = nextToken(line.substring(idx))
    if (nextToken.length == 1) {
        idx += 1
    } else {
        idx += nextToken.length + 2
    }
    tokens.add(calculate2(nextToken))

    while (idx < line.length) {
        ops.add(line[idx])
        idx++

        val nextToken = nextToken(line.substring(idx))
        if (nextToken.length == 1) {
            idx += 1
        } else {
            idx += nextToken.length + 2
        }

        tokens.add(calculate2(nextToken))
    }

    while(ops.contains('+')) {
        val firstAdd = ops.indexOfFirst {it == '+'}
        val added = doOp('+', tokens[firstAdd], tokens[firstAdd+1])
        tokens[firstAdd] = added
        tokens.removeAt(firstAdd+1)
        ops.removeAt(firstAdd)
    }

    return tokens.fold(1L,  {a, b -> a*b})
}

fun parenthesize(line: String): String {
    return line.replace(
        Regex("([0-9])\\+([0-9])"),
        { mr ->
            "(" + mr.groups[1] + "+" + mr.groups[2] + ")"
        }
    )
}

fun main() {
    val content = Class::class.java.getResource("/d18/d18p1").readText()
    val list = content.split("\n").filter{it != ""}.map{it.filter{ it != ' '}}

    println(list.map{ calculate(it.reversed())}.sum())

    println(list.map{ calculate2(it)}.sum())







}
