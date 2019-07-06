package msk.pobazar.forceline.entities

/**
 * Класс игрового уровня
 * @param size - размер поля
 * @param field - массив с расположением точек на поле
 * @param countPoint - количество точек
 * @param transitions - массив линий между точками
 * @param lines - массив класса Line для всех точек
 */
class GameLevel(val size: Int, var field: Array<Point>, val countPoint: Int, val transitions: Array<Int>) {

    lateinit var lines: Array<Line>

    init {
        initLines()
    }

    /**
     * Проверяет решена ли игра
     */
    fun checkWin(): Boolean {
        for (line in lines) {
            if (!line.status)
                return false
        }
        return true
    }

    /**
     * Заполняем массив lines
     */
    fun initLines() {
        for (tr in 1..countPoint) {
            val line = Line(field[tr], field[transitions[tr]])
            lines[tr] = line
        }
        calculateLines()
    }

    /**
     * Пересчитывает массив линий
     */
    private fun calculateLines() {
        for (line1 in lines) {
            line1.status = true
            for (line2 in lines) {
                if (!(line1.equal(line2))) {
                    if (checkLines(line1, line2)) {
                        line1.status = false
                        break
                    }
                }
            }
        }
    }

    /**
     * Проверяет пересекаются ли две линии
     * @param l1 - первая линия
     * @param l2 - вторая линия
     */
    private fun checkLines(l1: Line, l2: Line): Boolean {
        val xEnd1: Int
        val xStart1: Int
        val yEnd1: Int
        val yStart1: Int
        val xEnd2: Int
        val xStart2: Int
        val yEnd2: Int
        val yStart2: Int
        val x: Boolean
        val y: Boolean
        if (l1.start.x > l1.end.x) {
            xStart1 = l1.start.x
            xEnd1 = l1.end.x
        } else {
            xStart1 = l1.end.x
            xEnd1 = l1.start.x
        }
        if (l1.start.y > l1.end.y) {
            yStart1 = l1.start.y
            yEnd1 = l1.end.y
        } else {
            yStart1 = l1.end.y
            yEnd1 = l1.start.y
        }
        if (l2.start.x > l2.end.x) {
            xStart2 = l2.start.x
            xEnd2 = l2.end.x
        } else {
            xStart2 = l2.end.x
            xEnd2 = l2.start.x
        }
        if (l2.start.y > l2.end.y) {
            yStart2 = l2.start.y
            yEnd2 = l2.end.y
        } else {
            yStart2 = l2.end.y
            yEnd2 = l2.start.y
        }
        if (!(((xStart2 >= xEnd1) && (xStart2 <= xStart1)) || ((xEnd2 >= xEnd1) && (xEnd2 <= xStart1)))) {
            return false
        }
        if (!(((yStart2 >= yEnd1) && (yStart2 <= yStart1)) || ((yEnd2 >= yEnd1) && (yEnd2 <= yStart1)))) {
            return false
        }
        return true
    }
}