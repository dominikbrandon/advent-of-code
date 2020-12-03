import java.io.File

object Day3 {

    @JvmStatic
    fun main(args: Array<String>) {
        val moves = listOf(
            Move(1, 1),
            Move(3, 1),
            Move(5, 1),
            Move(7, 1),
            Move(1, 2),
        )
        val file = File(javaClass.getResource("day3.txt").toURI())
        val multiplication = moves.fold(1L, { multiplicationAcc, move ->
            file.useLines { lines ->
                multiplicationAcc * move.countTrees(lines)
            }
        })
        println(multiplication)
    }

    data class Move(
        private val horizontal: Int,
        private val vertical: Int
    ) {
        fun countTrees(lines: Sequence<String>) =
            lines.filterIndexed { lineIndex, _ ->
                lineIndex.rem(vertical) == 0
            }.filterIndexed { lineIndex, line ->
                val horizontalIndex = (lineIndex * horizontal).rem(line.length)
                val content = line[horizontalIndex]
                Cell(content).isTree()
            }.count()
    }

    data class Cell(
        private val content: Char
    ) {
        fun isTree() = content == '#'
    }
}
