package msk.pobazar.forceline.views

import android.graphics.Point
import com.arellomobile.mvp.MvpView
import msk.pobazar.forceline.entities.Line

/**
 * View для активити игры
 */
interface GameView : MvpView {
    /**
     * Отображает поле на экране
     * @param lines - массив линий между точками
     * @param field - массив точек на поле
     */
    fun setField(field: Array<Point>, lines: Array<Line>)

    /**
     * Изменяет выделенность точки
     * @param point - точка
     * @param checked - статус точки
     */
    fun setCheckedPoint(point: Point, checked: Boolean)
}