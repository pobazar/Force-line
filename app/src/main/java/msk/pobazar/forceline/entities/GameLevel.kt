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

    val lines: Array<Line> = Array(countPoint) { Line(Point(-1, 0F, 0F, false), Point(-1, 0F, 0F, false)) }
    var checkedId: Int = -1

    init {
        initLines()
    }

    /**
     * Проверяет решена ли игра
     */
    fun checkWin(): Boolean {
        for (line in lines)
            if (!line.status)
                return false
        return true
    }

    /**
     * меняет местами две точки на поле
     */
    fun swapPoint(id1: Int, id2: Int) {
        lateinit var p1: Point
        lateinit var p2: Point
        for (p in field) {
            if (p.id == id1)
                p1 = p
        }
        for (p in field) {
            if (p.id == id2)
                p2 = p
        }

        for (p in field) {
            if (p.id == p1.id) {
                p.x = p2.x
                p.y = p2.y
                break
            }
        }
        for (p in field) {
            if (p.id == p2.id) {
                p.x = p1.x
                p.y = p1.y
                break
            }
        }
        calculateLines()
    }

    /**
     * Заполняем массив lines
     */
    private fun initLines() {
        for (tr in 1..countPoint)
            lines[tr - 1] = Line(field[tr - 1], field[transitions[tr - 1]])
        calculateLines()
    }

    /**
     * Пересчитывает массив линий
     */
    fun calculateLines() {
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
        val ax1: Float = l1.end.x
        val ax2: Float = l1.start.x
        val ay1: Float = l1.end.y
        val ay2: Float = l1.start.y
        val bx1: Float = l2.end.x
        val bx2: Float = l2.start.x
        val by1: Float = l2.end.y
        val by2: Float = l2.start.y
        val v1: Float = (bx2 - bx1) * (ay1 - by1) - (by2 - by1) * (ax1 - bx1)
        val v2: Float = (bx2 - bx1) * (ay2 - by1) - (by2 - by1) * (ax2 - bx1)
        val v3: Float = (ax2 - ax1) * (by1 - ay1) - (ay2 - ay1) * (bx1 - ax1)
        val v4: Float = (ax2 - ax1) * (by2 - ay1) - (ay2 - ay1) * (bx2 - ax1)
        return ((v1 * v2 < 0) && (v3 * v4 < 0))
    }
}