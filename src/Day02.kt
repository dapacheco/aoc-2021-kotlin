fun main() {
    fun part1(input: List<String>): Int {
        var horizontalPosition = 0
        var depth = 0

        input.forEach { cmd ->
            val (dir, magnitude) = cmd.split(" ")

            when (dir) {
                "forward" -> {
                    horizontalPosition += magnitude.toInt()
                }
                "up" -> {
                    depth -= magnitude.toInt()
                }
                "down" -> {
                    depth += magnitude.toInt()
                }
            }
        }

        return horizontalPosition * depth
    }

    fun part2(input: List<String>): Int {
        var horizontalPosition = 0
        var depth = 0
        var aim = 0

        input.forEach { cmd ->
            val (dir, magnitude) = cmd.split(" ")

            when (dir) {
                "forward" -> {
                    horizontalPosition += magnitude.toInt()
                    depth += aim * magnitude.toInt()
                }
                "up" -> {
                    aim -= magnitude.toInt()
                }
                "down" -> {
                    aim += magnitude.toInt()
                }
            }
        }
        return horizontalPosition * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
