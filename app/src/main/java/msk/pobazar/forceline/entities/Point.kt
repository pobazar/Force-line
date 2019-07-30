package msk.pobazar.forceline.entities

/**
 * Класс точки
 * @param x - значение по горизонтали
 * @param y - значение по вертикали
 */
class Point(val id: Int, var x: Float, var y: Float, var checked: Boolean) {

    /**
     * Сравнение двух объектов
     */
    fun equal(point2: Point): Boolean {
        return !((y != point2.y) || (x != point2.x) || (id != point2.id) || (checked != point2.checked))
    }
}