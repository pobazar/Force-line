package msk.pobazar.forceline.entities

/**
 * Класс линия между двумя точками
 * @param start - стартовая точка
 * @param end - конечная точка
 * @param status - освобождена линия?
 */
class Line(val start: Point, val end: Point, var status: Boolean = false) {

    /**
     * Сравнение двух объектов
     */
    fun equal(line2: Line): Boolean {
        return !((start != line2.start) || (end != line2.end))
    }
}