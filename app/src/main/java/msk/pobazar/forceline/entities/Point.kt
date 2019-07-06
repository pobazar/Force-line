package msk.pobazar.forceline.entities

/**
 * Класс точки
 * @param x - значение по горизонтали
 * @param y - значение по вертикали
 */
class Point(var y: Int, var x: Int) {

    /**
     * Сравнение двух объектов
     */
    fun equal(point2: Point): Boolean {
        return !((y != point2.y) || (x != point2.x))
    }
}