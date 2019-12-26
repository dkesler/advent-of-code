package d10

fun main() {

    println(toTheta(Space(10, 15, true), Space(10, 5, true))) // 0
    println(toTheta(Space(10, 15, true), Space(15, 15, true))) // Pi/2
    println(toTheta(Space(10, 15, true), Space(10, 25, true))) // Pi
    println(toTheta(Space(10, 15, true), Space(5, 15, true))) // 3Pi/2

}

