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
        val ax1: Int = l1.end.x
        val ax2: Int = l1.start.x
        val ay1: Int = l1.end.y
        val ay2: Int = l1.start.y
        val bx1: Int = l2.end.x
        val bx2: Int = l2.start.x
        val by1: Int = l2.end.y
        val by2: Int = l2.start.y
        val v1: Int = (bx2 - bx1) * (ay1 - by1) - (by2 - by1) * (ax1 - bx1)
        val v2: Int = (bx2 - bx1) * (ay2 - by1) - (by2 - by1) * (ax2 - bx1)
        val v3: Int = (ax2 - ax1) * (by1 - ay1) - (ay2 - ay1) * (bx1 - ax1)
        val v4: Int = (ax2 - ax1) * (by2 - ay1) - (ay2 - ay1) * (bx2 - ax1)
        return ((v1 * v2 < 0) && (v3 * v4 < 0))
    }
}