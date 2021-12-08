
class BingoNumber(val number: Int) {
    var marked: Boolean = false
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BingoNumber

        if (number != other.number) return false

        return true
    }

    override fun hashCode(): Int = number
    override fun toString(): String = number.toString()
}

class BingoLine(val numbers: List<BingoNumber>) {
    fun markNumber(number: Int) {
        val num = BingoNumber(number)
        numbers.filter { it == num }.map { it.marked = true }
    }

    fun isComplete(): Boolean =
            numbers.filter { it.marked }.size == numbers.size

    override fun toString() = numbers.toString()
}
class BingoBoard(numbers: List<List<Int>>) {
    var rows: List<BingoLine> = numbers.map { row ->
        BingoLine(row.map { BingoNumber(it) })
    }
    var columns: List<BingoLine>

    init {
        val cols = mutableListOf<BingoLine>()
        for (i in 0..4) {
            cols.add(BingoLine(numbers.map {
                it[i]
            }.map { BingoNumber(it) }))
        }
        columns = cols
    }

    fun markNumber(number: Int): Boolean {
        // Check rows
        rows.forEach { it.markNumber(number) }
        columns.forEach { it.markNumber(number) }

        return rows.any { it.isComplete() } || columns.any { it.isComplete() }
    }

    fun sumOfUnMatched(): Int =
            rows.map { row -> row.numbers.sumOf { if (!it.marked) it.number else 0 } }.sumOf { it }


    override fun toString(): String = rows.joinToString(separator = "\n") { it.toString() }
}

private fun makeBoards(numbers: List<String>): List<BingoBoard> {
    var numbersToProcess = numbers
    val ret = mutableListOf<BingoBoard>()

    while (numbersToProcess.isNotEmpty()) {
        ret.add(makeBoard(numbersToProcess.take(5)))
        numbersToProcess = numbersToProcess.drop(5)
    }

    return ret
}

private fun makeBoard(numbers: List<String>): BingoBoard {
    val board = numbers.map { line ->
        line.split(" ").filter { it.isNotBlank() }.map {
            it.toInt()
        }
    }

    return BingoBoard(board)
}

fun main() {

    fun part1(input: List<String>): Int {
        val bareLines = input.filter { it.isNotEmpty() }
        val cards = bareLines.drop(1)

        val boards = makeBoards(cards)

        var lastCalledNumber = 0
        val matchingBoard: Int = run loop@{
            input[0].split(",").forEach {
                val number = it.toInt()
                lastCalledNumber = number
                boards.forEachIndexed() { i, board ->
                    if (board.markNumber(number)) {
                        return@loop i
                    }
                }
            }
            -1
        }

        val winningBoard = boards[matchingBoard]
        val sumOfUnMarked = winningBoard.sumOfUnMatched()

        return lastCalledNumber * sumOfUnMarked
    }

    fun part2(input: List<String>): Int {
        val bareLines = input.filter { it.isNotEmpty() }
        val cards = bareLines.drop(1)

        val boards = makeBoards(cards).toMutableList()

        var lastCalledNumber = 0
        var lastBoard = BingoBoard(emptyList())
        run loop@{
            input[0].split(",").forEach {
                val number = it.toInt()
                lastCalledNumber = number
                val iterator = boards.iterator()

                while (iterator.hasNext()) {
                    lastBoard = iterator.next()

                    if (lastBoard.markNumber(number)) {
                        iterator.remove()
                    }
                }

                if (boards.isEmpty()) {
                    return@loop
                }
            }
        }
        return lastCalledNumber * lastBoard.sumOfUnMatched()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
