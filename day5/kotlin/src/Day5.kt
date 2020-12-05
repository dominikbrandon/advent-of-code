import java.io.File

object Day5 {

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File(javaClass.getResource("day5.txt").toURI())
        val boardingPasses = file.useLines { lines ->
            lines.map { BoardingPass(it) }.toList()
        }
        val seats = boardingPasses.map { it.seat }
        val highestId = seats.maxByOrNull { it.id }!!.id
        println(highestId)

        val allSeats = (0..127).flatMap { row -> (0..7).map { col -> Seat(row, col) } }
        val emptySeats = allSeats.subtract(seats)
        emptySeats.forEach { println(it) }
    }

    data class Seat(
        val row: Int,
        val col: Int
    ) {
        val id = row * 8 + col
    }

    data class BoardingPass(
        private val code: String
    ) {
        val seat = Seat(row(), col())

        private fun row(): Int = code
            .substring(0 until 7)
            .map {
                when (it) {
                    'F' -> '0'
                    'B' -> '1'
                    else -> throw AssertionError("Could not decode row: $it")
                }
            }
            .joinToString(separator = "")
            .let { Integer.valueOf(it, 2) }

        private fun col(): Int = code
            .substring(7)
            .map {
                when (it) {
                    'L' -> '0'
                    'R' -> '1'
                    else -> throw AssertionError("Could not decode column: $it")
                }
            }
            .joinToString(separator = "")
            .let { Integer.valueOf(it, 2) }
    }
}
