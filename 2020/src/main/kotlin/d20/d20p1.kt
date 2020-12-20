package d20

fun toPhoto(lines: List<String>): Photo {
    val photoId = lines[0].substring(5, 9).toInt()
    val pic = lines.subList(1, lines.size)
    return Photo(photoId, pic)
}

data class Photo(
    val id: Int,
    val pic: List<String>,
    val top: Int? = null,
    val right: Int? = null,
    val bot: Int? = null,
    val left: Int? = null
) {
    fun topEdge(): String {
        return pic[0]
    }

    fun botEdge(): String {
        return pic[pic.size-1].reversed()
    }

    fun rightEdge(): String {
        return String(pic.map{ it[it.length-1] }.toCharArray())
    }

    fun leftEdge(): String {
        return String(pic.map{ it[0] }.toCharArray()).reversed()
    }

    fun mirror(): Photo {
        return Photo(
            id,
            pic.map{it.reversed()},
            top,
            left,
            bot,
            right
        )
    }

    fun rotate(): Photo {
        val width = pic[0].length
        val height = pic.size
        val newPic = (0 until width).map{ oldCol ->
            String((height-1 downTo 0).map{ oldRow ->
                pic[oldRow][oldCol]
            }.toCharArray())
        }

        return Photo(
            id,
            newPic,
            left,
            top,
            right,
            bot
        )
    }

    fun print() {
        for (row in pic) {
            println(row)
        }
        println()
    }

    fun permutations(): List<Photo> {
        val r1 = rotate()
        val r2 = r1.rotate()
        val r3 = r2.rotate()
        val mr0 = mirror()
        val mr1 = mr0.rotate()
        val mr2 = mr1.rotate()
        val mr3 = mr2.rotate()

        return listOf(this, r1, r2, r3, mr0, mr1, mr2, mr3)
    }

    fun stripEdges(): Photo {
        val newPic = pic.subList(1, pic.size-1).map{it.substring(1, it.length-1)}
        return Photo(id, newPic, top, right, bot, left)
    }

    fun mergeRight(other: Photo): Photo {
        val newPic = pic.zip(other.pic).map{ (l1, l2) ->
            l1 + l2
        }
        return Photo(
            id,
            newPic
        )
    }

    fun mergeBot(other: Photo): Photo {
        return Photo(id, pic + other.pic)
    }
}


fun findMatch(p: Map<String, List<Photo>>, edge: String, id: Int): Int? {
    val matches = (p[edge]?.filter{it.id != id} ?: listOf()) +
            (p[edge.reversed()]?.filter{it.id != id} ?: listOf())

    if (matches.size > 1) {
        println("Too many matches for ${id} edge: ${edge}")
    }

    return matches.firstOrNull()?.id
}

fun orient(photo: Photo, predicate: (Photo) -> Boolean): Photo {
    val matchingOrientations = photo.permutations().filter(predicate)

    if (matchingOrientations.size > 1) {
        println("Too many matching orientations for photo id ${photo.id}")
    }

    return matchingOrientations[0]
}

fun findRow(rowStart: Photo, photos: List<Photo>): List<Photo> {
    var row = listOf(rowStart)
    while(row.last().right != null) {
        val next = orient(photos.first{it.id == row.last().right}) { p->
            p.leftEdge() == row.last().rightEdge().reversed()
        }
        row = row + next
    }
    return row
}

val seaMonsterOffsets = listOf(
    Pair(0, 18),
    Pair(1, 0),
    Pair(1, 5),
    Pair(1, 6),
    Pair(1, 11),
    Pair(1, 12),
    Pair(1, 17),
    Pair(1, 18),
    Pair(1, 19),
    Pair(2, 1),
    Pair(2, 4),
    Pair(2, 7),
    Pair(2, 10),
    Pair(2, 13),
    Pair(2, 16)
)

fun seaMonsterAt(photo: Photo, row: Int, col: Int): Boolean {
    if (row >= photo.pic.size - 2) {
        return false
    }

    if (col >= photo.pic[0].length - 19) {
        return false
    }

    return seaMonsterOffsets.all {
        photo.pic[row + it.first][col + it.second] == '#'
    }
}

fun countSeaMonsters(photo:Photo): Int {
    return (0..photo.pic.size-3).flatMap{ row -> (0..photo.pic[row].length-20).map{ Pair(row, it) }}
        .filter{ seaMonsterAt(photo, it.first, it.second) }
        .count()
}


fun main() {
    val content = Class::class.java.getResource("/d20/d20p1").readText()
    val list = content.split("\n").filter{it != ""}

    val photos = (0..(list.size/11)-1).map{ list.subList(it * 11, it*11 + 11) }.map(::toPhoto)

    val photosByEdge = photos.flatMap {
        listOf(
            Pair(it.topEdge(), it),
            Pair(it.botEdge(), it),
            Pair(it.leftEdge(), it),
            Pair(it.rightEdge(), it)
        )
    }
        .groupBy { it.first }
        .mapValues { it.value.map{it.second} }

    val matchedPhotos = photos.map{p ->
        val topMatch = findMatch(photosByEdge, p.topEdge(), p.id)
        val botMatch = findMatch(photosByEdge, p.botEdge(), p.id)
        val leftMatch = findMatch(photosByEdge, p.leftEdge(), p.id)
        val rightMatch = findMatch(photosByEdge, p.rightEdge(), p.id)
        Photo(
            p.id,
            p.pic,
            topMatch,
            rightMatch,
            botMatch,
            leftMatch
        )
    }

    val corners = matchedPhotos.filter{p ->
        val topMatch = if (p.top != null) 1 else 0
        val botMatch = if (p.bot != null) 1 else 0
        val leftMatch = if (p.left != null) 1 else 0
        val rightMatch = if (p.right != null) 1 else 0
        topMatch + botMatch + leftMatch + rightMatch == 2
    }

    val m = corners.map{it.id}.fold(1L, {a,b -> a*b})
    println(m)

    val topLeft = orient(corners[0]) { p -> p.top == null && p.left == null }

    var stitchedPic = listOf(findRow(topLeft, matchedPhotos))

    while(stitchedPic.last().first().bot != null) {
        val startOfPrevRow = stitchedPic.last().first()
        val startOfNewRow = orient(matchedPhotos.first{it.id == startOfPrevRow.bot}) { p->
            p.topEdge() == startOfPrevRow.botEdge().reversed()
        }
        val newRow = findRow(startOfNewRow, matchedPhotos)
        stitchedPic = stitchedPic.plusElement(newRow)
    }

    val strippedStitchedPic = stitchedPic.map{ it.map { it.stripEdges() }}

    val mergedPic = strippedStitchedPic.map{ it.reduce{p1, p2 -> p1.mergeRight(p2)}}
        .reduce{p1, p2 -> p1.mergeBot(p2)}

    val numSeaMonsters = mergedPic.permutations().map(::countSeaMonsters).first{it != 0}
    val numHashes = mergedPic.pic.map{ it.filter{it == '#'}.count()}.sum()
    println(numHashes - numSeaMonsters * seaMonsterOffsets.size)
}



