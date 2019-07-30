package msk.pobazar.forceline.utils

import msk.pobazar.forceline.entities.GameLevel
import msk.pobazar.forceline.entities.Point
import java.util.*

class GeneratorLevel {

    fun generateLevel(): GameLevel {
        val size = 10
        val countPoint = 10
        val transition: Array<Int> = Array(countPoint) { 0 }
        val field: Array<Point> = Array(countPoint) { Point(-1, 0F, 0F, false) }
        for (i in 1..countPoint) {
            field[i - 1] = Point(i - 1, Random().nextFloat() * 1000 + 100, Random().nextFloat() * 1000 + 100, false)
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