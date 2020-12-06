import java.io.File

object Day6 {

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File(javaClass.getResource("day6.txt").toURI())
        val groups = file.readGroups()

        // part1
        val sum = groups.sumBy { it.countAnyContains() }
        println(sum)

        // part2
        val sum2 = groups.sumBy { it.countEachContains() }
        println(sum2)
    }

    private fun File.readGroups(): List<Group> {
        return readText()
            .split("\n\n")
            .map { rawGroup ->
                val answers = rawGroup
                    .trim()
                    .split("\n")
                    .map { singlePerson ->
                        val chars = singlePerson
                            .filter { !it.isWhitespace() }
                            .toSet()
                        Answer(chars)
                    }
                Group(answers)
            }
    }

    data class Group(
        val answers: List<Answer>
    ) {

        fun countEachContains(): Int {
            val shortestAnswer = answers.minByOrNull { it.chars.size }!!
            return countContains(shortestAnswer.chars) { acc, other -> acc.intersect(other) }
        }

        fun countAnyContains(): Int {
            return countContains(setOf()) { acc, other -> acc.union(other) }
        }

        private fun countContains(initial: Set<Char>, policy: (acc: Set<Char>, other: Set<Char>) -> Set<Char>): Int {
            return answers.map { it.chars }.fold(initial, policy).size
        }
    }

    data class Answer(
        val chars: Set<Char>
    )
}
