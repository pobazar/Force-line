package msk.pobazar.forceline.utils

import msk.pobazar.forceline.entities.GameLevel
import msk.pobazar.forceline.entities.Point

class GeneratorLevel {

    fun generateLevel(): GameLevel {
        val size = 10
        val countPoint = 10
        lateinit var transition: Array<Int>
        lateinit var field: Array<Point>
        for (i in 1..countPoint) {
            field[i - 1] = Point(i - 1, (10 * (i - 1)).toFloat(), (10 * (i - 1)).toFloat(), false)
        }
        transition[0] = 1
        transition[1] = 2
        transition[2] = 3
        transition[3] = 0
        transition[4] = 5
        transition[5] = 6
        transition[6] = 4
        transition[7] = 8
        transition[8] = 9
        transition[9] = 7

        return GameLevel(size, field, countPoint, transition)
    }
}