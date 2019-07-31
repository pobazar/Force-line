package msk.pobazar.forceline.utils

import msk.pobazar.forceline.entities.GameLevel
import msk.pobazar.forceline.entities.Point
import java.util.*

class GeneratorLevel {

    fun generateLevel(): GameLevel {
        val size = 10
        val countPoint = 10
        val transition: Array<Int> = arrayOf(1, 2, 3, 0, 5, 6, 4, 8, 9, 7)
        val field: Array<Point> = Array(countPoint) { i ->
            Point(i - 1, Random().nextFloat() * 1000 + 100, Random().nextFloat() * 1000 + 100, false)
        }

        return GameLevel(size, field, countPoint, transition)
    }

}