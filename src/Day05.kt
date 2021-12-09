import kotlin.reflect.KProperty1

data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point) {
    val isStraight = start.x == end.x || start.y == end.y
    private val isHorizontal = start.y == end.y

    val max: Point = Point(maxOf(start.x, end.x), maxOf(start.y, end.y))

    private fun getStraightRange(prop: KProperty1<Point, Int>): IntProgression {
        val start = prop.get(start)
        val end = prop.get(end)
        return if (start < end) {
            start..end
        } else {
            start downTo end
        }
    }

    fun getPoints(): List<Point> {
        // check if horizontal of vertical
        return if (isStraight) {
            if (isHorizontal) {
                val prop = Point::x
                getStraightRange(prop).map { x ->
                    Point(x, start.y)
                }
            } else {
                val prop = Point::y
                getStraightRange(prop).map { y ->
                    Point(start.x, y)
                }
            }
        } else {
            // diagonal line
            val inc = if (start.y < end.y) 1 else -1
            val range = if (start.x < end.x) {
                start.x..end.x
            } else {
                start.x downTo end.x
            }
            diagonalLinePoints(range, inc)
        }
    }

    private fun diagonalLinePoints(range: IntProgression, inc: Int): List<Point> {
        var y = start.y
        return range.map { x ->
            Point(x, y).also {
                y += inc
            }
        }
    }
}

data class Grid(val width: Int, val height: Int) {
    private val points = List(height + 1) { IntArray(width + 1) { 0 } }

    fun addLine(line: Line) {
        line.getPoints().forEach { point ->
            points[point.y][point.x] += 1
        }
    }

    override fun toString(): String {
        val output = StringBuilder()
        points.forEach { line ->
            line.forEach { pointCount ->
                output.append(" $pointCount ")
            }
            output.trim()
            output.append("\n")
        }
        return output.toString()
    }

    fun dangerAreas(): Int {
        // Find the number of points that the value is >= 2
        var dangerAreas = 0
        points.forEach { line ->
            line.forEach { pointCount ->
                if (pointCount >= 2) {
                    dangerAreas++
                }
            }
        }

        return dangerAreas
    }
}

fun main() {
    fun toLines(input: List<String>): List<Line> {
        return input.map { line ->
            val (start, end) = line.split("->").map { i ->
                val (x, y) = i.trim().split(",")
                Point(x.toInt(), y.toInt())
            }
            Line(start, end)
        }
    }

    fun part1(input: List<String>): Int {
        val lineList = toLines(input)

        val straightLines = lineList.filter { it.isStraight }

        val maxX = lineList.maxOf { it.max.x }
        val maxY = lineList.maxOf { it.max.y }

        val grid = Grid(maxX, maxY)
        straightLines.forEach {
            grid.addLine(it)
        }

        return grid.dangerAreas()
    }

    fun part2(input: List<String>): Int {
        val lineList = toLines(input)

        val maxX = lineList.maxOf { it.max.x }
        val maxY = lineList.maxOf { it.max.y }

        val grid = Grid(maxX, maxY)
        lineList.forEach {
            grid.addLine(it)
        }

        return grid.dangerAreas()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
