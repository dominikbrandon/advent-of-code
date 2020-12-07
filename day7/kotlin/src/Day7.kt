import java.io.File

object Day7 {

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File(javaClass.getResource("day7.txt").toURI())

        val bags = file.readBags()
        val outermostBags = getBagsWhichCanContain("shiny gold", bags)
        println(outermostBags.toSet().size)

        println(howManyInside(1, "shiny gold", bags).values.sum())
    }

    private fun getBagsWhichCanContain(specificBag: String, allBags: Map<String, String>): List<String> {
        val bagsWhichCanContain = allBags.mapNotNull {
            if (it.value.contains(specificBag)) it.key else null
        }
        val aggregate = bagsWhichCanContain.toMutableList()
        bagsWhichCanContain.flatMapTo(aggregate) {
            getBagsWhichCanContain(it, allBags)
        }
        return aggregate
    }

    private fun howManyInside(quantity: Int, specificBag: String, allBags: Map<String, String>): Map<String, Int> {
        val regex = Regex("(\\d+) (.+) bag.*")
        val insideBags = allBags[specificBag]!!
            .split(",")
            .map { it.trim() }
            .mapNotNull { regex.matchEntire(it)?.destructured }
            .associate { it.let { match -> Pair(match.component2(), match.component1().toInt() * quantity) } }
        val aggregate = insideBags.toMutableMap()
        insideBags.map {
            howManyInside(it.value, it.key, allBags)
        }.forEach { map ->
            map.forEach {
                aggregate.merge(it.key, it.value) { a, b -> a + b }
            }
        }
        return aggregate
    }

    private fun File.readBags(): Map<String, String> {
        val regex = Regex("(.+) bags contain (.+)")
        return readText()
            .trim()
            .split("\n")
            .associate { row ->
                regex.matchEntire(row)!!.destructured.let {
                    Pair(it.component1(), it.component2())
                }
            }
    }
}
