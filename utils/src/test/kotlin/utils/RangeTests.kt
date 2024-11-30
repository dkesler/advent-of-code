package utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.Ranges.EmptyRange
import utils.Ranges.IRange
import utils.Ranges.Rangeable
import utils.Ranges.RangeableValue
import utils.Ranges.rangeFrom
import java.util.stream.Stream

class RangeTests {
    @ParameterizedTest
    @MethodSource("getCompareToTestData")
    fun compareToTests(x: Rangeable, y:Rangeable, expected: Int) {
        assertEquals(expected, x.compareTo(y))
        assertEquals(-expected, y.compareTo(x))
    }

                       @ParameterizedTest
    @MethodSource("getContainsTestData")
    fun containsTests(range: IRange, t: Rangeable, doesContain: Boolean) {
        assertEquals(doesContain, range.contains(t))
    }

    @ParameterizedTest(name = "Intersection({0}, {1}) == {2}")
    @MethodSource("getIntersectionTestData")
    fun IntersectionTests(x: IRange, y: IRange, intersection: IRange) {
        assertEquals(intersection, x.intersection(y))
        assertEquals(intersection, y.intersection(x))
    }

    @ParameterizedTest(name = "union({0},{1}) == {2}")
    @MethodSource("getUnionTestData")
    fun unionTests(x: IRange, y: IRange, union: IRange) {
        assertEquals(union, x.union(y))
        assertEquals(union, y.union(x))
    }

    @Test
    fun givenNoncontiguousUnion_union_throws() {
        assertThrows(
            Error::class.java
        ) { -> rangeFrom(0, 10).union(rangeFrom(20, 30)) }
    }

    @ParameterizedTest(name = "overlaps({0},{1}) == {2}")
    @MethodSource("getOverlapsTestData")
    fun overlapsTests(x: IRange, y: IRange, overlaps: Boolean) {
        assertEquals(overlaps, x.overlaps(y))
        assertEquals(overlaps, y.overlaps(x))
    }

    @ParameterizedTest(name = "diff({0},{1}) == {2}, diff({1}, {0}) == {3}")
    @MethodSource("getDiffTestData")
    fun overlapsTests(x: IRange, y: IRange, diff: List<IRange>, reverseDiff: List<IRange>) {
        assertEquals(diff, x.difference(y))
        assertEquals(reverseDiff, y.difference(x))
    }

    @ParameterizedTest(name = "split({0},{1}) == {2}")
    @MethodSource("getSplitTestData")
    fun overlapsTests(x: IRange, pivot: Long, split: List<IRange>) {
        assertEquals(split, x.split(pivot))
    }


    companion object {
        @JvmStatic
        fun getCompareToTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(RangeableValue(5), RangeableValue(6), -1),
                Arguments.of(RangeableValue(6), RangeableValue(5), 1),
                Arguments.of(RangeableValue(5), RangeableValue(5), 0),
                Arguments.of(Ranges.PositiveInfinity(), Ranges.PositiveInfinity(), 0),
                Arguments.of(RangeableValue(5), Ranges.PositiveInfinity(), -1),
                Arguments.of(Ranges.NegativeInfinity(), Ranges.NegativeInfinity(), 0),
                Arguments.of(Ranges.NegativeInfinity(), Ranges.PositiveInfinity(), -1),
            )
        }
        @JvmStatic
        fun getContainsTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(rangeFrom(0, 10), RangeableValue(5), true),
                Arguments.of(rangeFrom(0, 10), RangeableValue(10), true),
                Arguments.of(rangeFrom(0, 10), RangeableValue(20), false),
                Arguments.of(rangeFrom(0, 10), RangeableValue(-10), false),
                Arguments.of(rangeFrom(0, 10), Ranges.PositiveInfinity(), false),
                Arguments.of(rangeFrom(0, 10), Ranges.NegativeInfinity(), false),
                Arguments.of(rangeFrom(0, null), RangeableValue(10), true),
                Arguments.of(rangeFrom(null, 10), RangeableValue(0), true),
                Arguments.of(rangeFrom(null, 10), Ranges.NegativeInfinity(), true),
                Arguments.of(rangeFrom(null, 10), Ranges.PositiveInfinity(), false),
                Arguments.of(rangeFrom(10, null), Ranges.NegativeInfinity(), false),
                Arguments.of(rangeFrom(10, null), Ranges.PositiveInfinity(), true),
                Arguments.of(EmptyRange(), Ranges.PositiveInfinity(), false),
                Arguments.of(EmptyRange(), Ranges.NegativeInfinity(), false),
                Arguments.of(EmptyRange(), RangeableValue(5), false),
            )
        }
        @JvmStatic
        fun getIntersectionTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of( EmptyRange(), EmptyRange(), EmptyRange()),
                Arguments.of( EmptyRange(), rangeFrom(0, 10), EmptyRange()),
                Arguments.of( EmptyRange(), rangeFrom(null, null), EmptyRange()),
                Arguments.of( rangeFrom(0, 10), rangeFrom(10, 20), rangeFrom(10, 10)),
                Arguments.of( rangeFrom(0, 10), rangeFrom(11, 20), EmptyRange()),
                Arguments.of( rangeFrom(0, 10), rangeFrom(5, 20), rangeFrom(5, 10)),
                Arguments.of( rangeFrom(0, 10), rangeFrom(2, 5), rangeFrom(2, 5)),
                Arguments.of( rangeFrom(0, 10), rangeFrom(-10, 5), rangeFrom(0, 5)),
                Arguments.of( rangeFrom(null, null), rangeFrom(null, null), rangeFrom(null, null)),
                Arguments.of( rangeFrom(null, null), rangeFrom(null, 5), rangeFrom(null, 5)),
                Arguments.of( rangeFrom(null, null), rangeFrom(0, 5), rangeFrom(0, 5)),
                Arguments.of( rangeFrom(null, null), rangeFrom(0, null), rangeFrom(0, null)),
                Arguments.of( rangeFrom(null, 5), rangeFrom(4, null), rangeFrom(4, 5)),

                )
        }

        @JvmStatic
        fun getUnionTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of( EmptyRange(), EmptyRange(), EmptyRange()),
                Arguments.of( EmptyRange(), rangeFrom(0, 10), rangeFrom(0, 10)),
                Arguments.of( EmptyRange(), rangeFrom(null, null), rangeFrom(null, null)),
                Arguments.of( rangeFrom(0, 10), rangeFrom(10, 20), rangeFrom(0, 20)),
                Arguments.of( rangeFrom(0, 10), rangeFrom(5, 20), rangeFrom(0, 20)),
                Arguments.of( rangeFrom(0, 10), rangeFrom(2, 5), rangeFrom(0, 10)),
                Arguments.of( rangeFrom(0, 10), rangeFrom(-10, 5), rangeFrom(-10, 10)),
                Arguments.of( rangeFrom(null, null), rangeFrom(null, null), rangeFrom(null, null)),
                Arguments.of( rangeFrom(null, null), rangeFrom(null, 5), rangeFrom(null, null)),
                Arguments.of( rangeFrom(null, null), rangeFrom(0, 5), rangeFrom(null, null)),
                Arguments.of( rangeFrom(null, null), rangeFrom(0, null), rangeFrom(null, null)),
                Arguments.of( rangeFrom(null, 5), rangeFrom(4, null), rangeFrom(null, null)),
            )
        }

        @JvmStatic
        fun getOverlapsTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of( EmptyRange(), EmptyRange(), false),
                Arguments.of( EmptyRange(), rangeFrom(0, 10), false),
                Arguments.of( EmptyRange(), rangeFrom(null, null), false),
                Arguments.of( rangeFrom(0, 10), rangeFrom(10, 20), true),
                Arguments.of( rangeFrom(0, 10), rangeFrom(11, 20),false),
                Arguments.of( rangeFrom(0, 10), rangeFrom(5, 20), true),
                Arguments.of( rangeFrom(0, 10), rangeFrom(2, 5), true),
                Arguments.of( rangeFrom(0, 10), rangeFrom(-10, 5), true),
                Arguments.of( rangeFrom(null, null), rangeFrom(null, null), true),
                Arguments.of( rangeFrom(null, null), rangeFrom(null, 5), true),
                Arguments.of( rangeFrom(null, null), rangeFrom(0, 5), true),
                Arguments.of( rangeFrom(null, null), rangeFrom(0, null), true),
                Arguments.of( rangeFrom(null, 5), rangeFrom(4, null), true),
            )
        }

        @JvmStatic
        fun getDiffTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of( EmptyRange(), EmptyRange(), listOf(EmptyRange()), listOf(EmptyRange())),
                Arguments.of( EmptyRange(), rangeFrom(0, 10), listOf(EmptyRange()), listOf(rangeFrom(0, 10))),
                Arguments.of( EmptyRange(), rangeFrom(null, null), listOf(EmptyRange()), listOf(rangeFrom(null, null))),
                Arguments.of( rangeFrom(0, 10), rangeFrom(11, 20), listOf(rangeFrom(0, 10)), listOf(rangeFrom(11, 20))),
                Arguments.of( rangeFrom(0, 10), rangeFrom(10, 20), listOf(rangeFrom(0, 9)), listOf(rangeFrom(11, 20))),
                Arguments.of( rangeFrom(0, 10), rangeFrom(5, 20), listOf(rangeFrom(0, 4)), listOf(rangeFrom(11, 20))),
                Arguments.of( rangeFrom(0, 10), rangeFrom(5, 10), listOf(rangeFrom(0, 4)), listOf(EmptyRange())),
                Arguments.of( rangeFrom(0, 10), rangeFrom(2, 5), listOf(rangeFrom(0, 1), rangeFrom(6, 10)), listOf(EmptyRange())), //need noncontiguous range support
                Arguments.of( rangeFrom(0, 10), rangeFrom(-10, 5), listOf(rangeFrom(6, 10)), listOf(rangeFrom(-10, -1))),
                Arguments.of( rangeFrom(0, 10), rangeFrom(-10, 0), listOf(rangeFrom(1, 10)), listOf(rangeFrom(-10, -1))),
                Arguments.of( rangeFrom(null, null), rangeFrom(null, null), listOf(EmptyRange()), listOf(EmptyRange())),
                Arguments.of( rangeFrom(null, null), rangeFrom(null, 5), listOf(rangeFrom(6, null)), listOf(EmptyRange())),
                Arguments.of( rangeFrom(null, null), rangeFrom(0, 5), listOf(rangeFrom(null, -1), rangeFrom(6, null)), listOf(EmptyRange())), //need noncontiguous range support
                Arguments.of( rangeFrom(null, null), rangeFrom(0, null), listOf(rangeFrom(null, -1)), listOf(EmptyRange())),
                Arguments.of( rangeFrom(null, 5), rangeFrom(4, null), listOf(rangeFrom(null, 3)), listOf(rangeFrom(6, null))),
            )
        }

        @JvmStatic
        fun getSplitTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of( EmptyRange(), 5, listOf(EmptyRange())),
                Arguments.of( rangeFrom(null, null), 5, listOf(rangeFrom(null, 5), rangeFrom(6, null))),
                Arguments.of( rangeFrom(0, 10), 5, listOf(rangeFrom(0, 5), rangeFrom(6, 10))),
                Arguments.of( rangeFrom(0, 10), 10, listOf(rangeFrom(0, 10))),
                Arguments.of( rangeFrom(0, 10), 0, listOf(rangeFrom(0, 0), rangeFrom(1, 10))),
                Arguments.of( rangeFrom(0, 10), 15, listOf(rangeFrom(0, 10)))
            )
        }
    }


}