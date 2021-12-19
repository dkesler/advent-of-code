package d16
import utils.*

fun main() {
    val g =  readList("/d16/d16p1")
    val inBits = toBits(g[0])
    val packets = toPackets(inBits)
    println(packets.map{it.versionSum()}.sum())
    println(parsePacket(inBits).first.calculate())
}

data class Packet(
    val version: Int,
    val literalValue: Long,
    val typeId: Int,
    val subPackets: List<Packet>
) {
    fun versionSum(): Int {
        return subPackets.map{it.versionSum()}.sum() + version
    }

    fun calculate(): Long {
        return when(typeId) {
            0 -> subPackets.map{it.calculate()}.sum()
            1 -> subPackets.map{it.calculate()}.fold(1L, {a, i -> a*i})
            2 -> subPackets.map{it.calculate()}.minOrNull()!!
            3 -> subPackets.map{it.calculate()}.maxOrNull()!!
            4 -> literalValue
            5 -> if (subPackets[0].calculate() > subPackets[1].calculate()) 1L else 0L
            6 -> if (subPackets[0].calculate() < subPackets[1].calculate()) 1L else 0L
            7 -> if (subPackets[0].calculate() == subPackets[1].calculate()) 1L else 0L
            else -> 0
        }
    }
}

fun toPackets(bits: List<Char>): List<Packet> {
    val packets = mutableListOf<Packet>()
    var b = bits
    while(b.size > 3) {
        val res = parsePacket(b)
        b = res.second
        packets.add(res.first)
    }

    return packets
}

private fun parsePacket(
    bits: List<Char>
): Pair<Packet, List<Char>> {
    val version = bits.subList(0, 3)
    val typeId = bits.subList(3, 6)
    if (strify(typeId) == "100") {
        return parseLiteral(version, listOf(), bits.subList(6, bits.size))
    } else {
        return parseOp(version, typeId, bits.subList(6, bits.size))
    }
}

fun parseOp(version: List<Char>, op: List<Char>, bits: List<Char>): Pair<Packet, List<Char>> {
    if (bits[0] == '0') {
        return parseOp0(version, op, bits.subList(1, bits.size))
    } else {
        return parseOp1(version, op, bits.subList(1, bits.size))
    }

}

fun parseOp1(version: List<Char>, op: List<Char>, bits: List<Char>): Pair<Packet, List<Char>> {
    val numSubpackets = longify(bits.subList(0, 11))
    val res = (1..numSubpackets).fold(Pair(listOf<Packet>(), bits.subList(11, bits.size)), { p, i ->
        val r = parsePacket(p.second)
        Pair(p.first + r.first, r.second)
    })
    return Pair(
        Packet(
            intify(version),
            0,
            intify(op),
            res.first

        ),
        res.second
    )
}

fun parseOp0(version: List<Char>, op: List<Char>, bits: List<Char>): Pair<Packet, List<Char>> {
    val subpacketBits = intify(bits.subList(0, 15))
    val subpackets = toPackets(bits.subList(15, 15+subpacketBits))
    return Pair(
        Packet(
            intify(version),
            0,
            intify(op),
            subpackets
        ),
        bits.subList(15+subpacketBits, bits.size)
    )
}

fun parseLiteral(version: List<Char>, literalSoFar: List<Char>, bits: List<Char>): Pair<Packet, List<Char>> {
    val newLiterals = literalSoFar + bits.subList(1, 5)

    if (bits[0] == '0') {
        return Pair(
            Packet(intify(version), longify(newLiterals), 4, listOf()),
            bits.subList(5, bits.size)
        )
    }

    return parseLiteral(
        version,
        newLiterals,
        bits.subList(5, bits.size)
    )
}

private fun longify(newLiterals: List<Char>) = strify(newLiterals).toLong(2)
private fun intify(newLiterals: List<Char>) = strify(newLiterals).toInt(2)

private fun strify(version: List<Char>) = String(version.toCharArray())

fun toBits(line: String): List<Char> {
    val bitmap = mapOf(
        Pair('0', "0000"),
        Pair('1', "0001"),
        Pair('2', "0010"),
        Pair('3', "0011"),
        Pair('4', "0100"),
        Pair('5', "0101"),
        Pair('6', "0110"),
        Pair('7', "0111"),
        Pair('8', "1000"),
        Pair('9', "1001"),
        Pair('A', "1010"),
        Pair('B', "1011"),
        Pair('C', "1100"),
        Pair('D', "1101"),
        Pair('E', "1110"),
        Pair('F', "1111")
    )

    return line.map{ bitmap[it]!!}.map{it.toList()}.flatten()
}
