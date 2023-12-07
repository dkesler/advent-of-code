package d5

import utils.*


fun main() {
    val blocks = readBlocks("/d5.txt")

    val seeds = blocks[0][0].split(":")[1].split(" ").filter{it != ""}.chunked(2)
            .map{ LongRange(it[0].toLong(), it[0].toLong()+it[1].toLong()-1) }


    val mappers = blocks.subList(1, blocks.size).map{ block -> block.subList(1, block.size).map{ toMapper(it)} }

    var sources = seeds.toList()

    for (stage in mappers) {
        val destinations = mutableListOf<LongRange>()
        var newSources = sources
        for (mapper in stage) {
            val unmappedSources = mutableListOf<LongRange>()
            for (src in newSources) {
                val mapped = mapper.rangeToDest(src)
                if (mapped.first != null) destinations.add(mapped.first!!)
                unmappedSources.addAll(mapped.second)
            }
            newSources = unmappedSources
        }
        sources = (destinations + newSources)
    }


    println(sources.minByOrNull{ it.first }?.first)
}

//too high: 93725110