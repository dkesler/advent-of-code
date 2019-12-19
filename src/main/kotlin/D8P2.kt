import java.io.File

fun main() {
    val layerWithFewestZeros = File("src/main/resources/d8p1").readText().trim()
        .split("")
        .filter{ it != "" }
        .map { Integer.parseInt(it) }
        .chunked(25 * 6)
        .map(::Layer)
        .reduce{ l1, l2 -> l1.mergeLayer(l2) }
        .prettyPrint()


}

