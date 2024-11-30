package utils

import kotlin.Error

object Ranges {
    abstract class Rangeable: Comparable<Rangeable>
    data class RangeableValue(val t: Long): Rangeable() {
        override fun compareTo(other: Rangeable): Int {
            if (other is RangeableValue) return t.compareTo(other.t)
            if (other is PositiveInfinity) return -1
            return 1
        }

        override fun toString(): String {
            return t.toString()
        }
    }

    abstract class RangeableInfinity(open val positive: Boolean): Rangeable()
    data class NegativeInfinity(override val positive: Boolean = false): RangeableInfinity(false) {
        override fun compareTo(other: Rangeable): Int {
            if (other is NegativeInfinity) return 0
            return -1
        }
        override fun toString(): String {
            return "-Inf"
        }
    }

    data class PositiveInfinity(override val positive: Boolean = true): RangeableInfinity(true) {
        override fun compareTo(other: Rangeable): Int {
            if (other is PositiveInfinity) return 0
            return 1
        }

        override fun toString(): String {
            return "+Inf"
        }
    }

    abstract class IRange() {
        abstract fun contains(t: Rangeable): Boolean
        abstract fun contains(t: Long): Boolean
        abstract fun intersection(other: IRange): IRange
        abstract fun union(other: IRange): IRange
        abstract fun overlaps(other: IRange): Boolean
        //Returns everything in this that is not contained in other
        abstract fun difference(other: IRange): List<IRange>
        abstract fun split(pivot: Long): List<IRange>

    }
    data class Range(val start: Rangeable, val end: Rangeable): IRange() {
        override fun contains(t: Rangeable): Boolean {
            return t in start..end
        }
        override fun contains(t: Long): Boolean {
            return contains(RangeableValue(t))
        }

        override fun intersection(other: IRange): IRange {
            if (other !is Range) return other

            val start = if (this.contains(other.start)) {
                other.start
            } else if (other.contains(this.start)) {
                this.start
            } else {
                null
            }

            val end = if (this.contains(other.end)) {
                other.end
            } else if (other.contains(this.end)) {
                this.end
            } else {
                null
            }

            if (start == null || end == null) return EmptyRange()

            return Range(start, end)
        }

        override fun union(other: IRange): IRange {
            if (other !is Range) {
                return this
            }

            //Noncontiguous unions not supported
            if (!this.overlaps(other)) {
                throw Error("Noncontiguous unions not supported")
            }

            val minStart = minOf(this.start, other.start)
            val maxEnd = maxOf(this.end, other.end)
            return Range(minStart, maxEnd)
        }

        override fun overlaps(other: IRange): Boolean {
            if (other !is Range) return false
            return this.contains(other.start) || this.contains(other.end) || other.contains(this.start) || other.contains(this.end)
        }

        override fun difference(other: IRange): List<IRange> {
            if (other !is Range) return listOf(this)

            if (!this.overlaps(other)) return listOf(this)

            //this:   |---|
            //other: |-----|
            //result: empty
            //this:   |---|
            //other:  |---|
            //result: empty
            if (other.contains(this.start) && other.contains(this.end)) {
                return listOf(EmptyRange())
            }

            //this:  |---|
            //other:     |---|
            //result:|--|
            //this:  |---|
            //other:    |---|
            //result:|-|
            //this:  |---|
            //other:   |-|
            //result:||
            if (other.start is RangeableValue && this.contains(other.start) && other.end >= this.end) {
                return listOf(Range(this.start, RangeableValue(other.start.t - 1)))
            }


            //this:  |---|
            //other:   ||
            //result:||..|
            if ( other.start is RangeableValue && other.end is RangeableValue && this.contains(other.start) && this.contains(other.end)) {
                return listOf(
                    Range(this.start, RangeableValue(other.start.t - 1)),
                    Range(RangeableValue(other.end.t+1), this.end)
                )
            }


            //this:  |---|
            //other: |-|
            //result:   ||
            //this:  |---|
            //other:|-|
            //result:  |-|
            //this:   |---|
            //other:|-|
            //result:  |--|
            if (other.end is RangeableValue && this.contains(other.end) && other.start <= this.start) {
                return listOf(Range(RangeableValue(other.end.t+1), this.end))
            }

            throw Error("Diff not handled properly")
        }

        override fun split(pivot: Long): List<IRange> {
            if (!this.contains(pivot) || (this.end is RangeableValue && this.end.t == pivot)) return listOf(this)

            return listOf(
                Range(this.start, RangeableValue(pivot)),
                Range(RangeableValue(pivot+1), this.end)
            )
        }

        override fun toString(): String {
            return "(${start}..${end})"
        }
    }

    data class EmptyRange(private val isNull: Boolean = true): IRange() {
        override fun contains(t: Rangeable): Boolean {
            return false
        }

        override fun contains(t: Long): Boolean {
            return false
        }

        override fun intersection(other: IRange): IRange {
            return this
        }

        override fun union(other: IRange): IRange {
            return other
        }

        override fun overlaps(other: IRange): Boolean {
            return false
        }

        override fun difference(other: IRange): List<IRange> {
            return listOf(this)
        }

        override fun split(pivot: Long): List<IRange> {
            return listOf(this)
        }

        override fun toString(): String {
            return "()"
        }
    }
    fun rangeFrom(start: Long?, end: Long?): Range {
        val startRangeable = if (start == null) NegativeInfinity() else RangeableValue(start)
        val endRangeable = if (end == null) PositiveInfinity() else RangeableValue(end)
        return Range(startRangeable, endRangeable)
    }
}