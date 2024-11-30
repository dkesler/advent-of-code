package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class GridsTests {

    @Test
    fun testTranspose() {
        Assertions.assertEquals(
                listOf(
                        listOf(1, 4),
                        listOf(2, 5),
                        listOf(3, 6)
                ),
                Grids.transpose(listOf(
                        listOf(1, 2, 3),
                        listOf(4, 5, 6)
                ))
        )
    }

    @Test
    fun testRot90Cw() {
        Assertions.assertEquals(
                listOf(
                        listOf(4, 1),
                        listOf(5, 2),
                        listOf(6, 3)
                ),
                Grids.rot90CW(listOf(
                        listOf(1, 2, 3),
                        listOf(4, 5, 6)
                ))
        )
    }

    @Test
    fun testRot90Ccw() {
        Assertions.assertEquals(
                listOf(
                        listOf(3, 6),
                        listOf(2, 5),
                        listOf(1, 4)
                ),
                Grids.rot90CCW(listOf(
                        listOf(1, 2, 3),
                        listOf(4, 5, 6)
                ))
        )
    }

    @Test
    fun testRot180() {
        Assertions.assertEquals(
                listOf(
                        listOf(6, 5, 4),
                        listOf(3, 2, 1)
                ),
                Grids.rot180(listOf(
                        listOf(1, 2, 3),
                        listOf(4, 5, 6)
                ))
        )
    }

    @Test
    fun testFlipH() {
        Assertions.assertEquals(
                listOf(
                        listOf(3, 2, 1),
                        listOf(6, 5, 4)
                ),
                Grids.flipH(listOf(
                        listOf(1, 2, 3),
                        listOf(4, 5, 6)
                ))
        )
    }

    @Test
    fun testFlipV() {
        Assertions.assertEquals(
                listOf(
                        listOf(4, 5, 6),
                        listOf(1, 2, 3)
                ),
                Grids.flipV(listOf(
                        listOf(1, 2, 3),
                        listOf(4, 5, 6)
                ))
        )
    }

    @Test
    fun testFloodfill() {
        val grid = listOf(
                listOf(1, 1, 2),
                listOf(1, 2, 1)
        )

        Assertions.assertEquals(
                setOf(
                        Point(0, 0),
                        Point(0, 1),
                        Point(1, 0)
                ),
                Grids.floodfill(
                        grid,
                        Point(0, 0)
                ) { pv -> pv.value == 1 }
        )
    }

    @Test
    fun testFullFloodfill() {
        val grid = listOf(
                listOf(1, 1, 2),
                listOf(1, 2, 1),
                listOf(2, 2, 1)
        )

        Assertions.assertEquals(
                setOf(
                        setOf(
                                Point(0, 0),
                                Point(0, 1),
                                Point(1, 0)
                        ),
                        setOf(Point(0, 2)),
                        setOf(
                                Point(1, 2),
                                Point(2, 2)
                        ),
                        setOf(
                                Point(1, 1),
                                Point(2, 1),
                                Point(2, 0)
                        )
                ),
                Grids.fullFloodFill(
                        grid,
                        { last, next -> last.value == next.value }
                )
        )
    }

    //test grid a* and graph a*




}