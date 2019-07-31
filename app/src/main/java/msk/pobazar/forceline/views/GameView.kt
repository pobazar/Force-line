package msk.pobazar.forceline.views

import com.arellomobile.mvp.MvpView
import msk.pobazar.forceline.entities.Line
import msk.pobazar.forceline.entities.Point

/**
 * View для активити игры
 */
interface GameView : MvpView {
    /**
     * Отображает поле на экране
     * @param lines - массив линий между точками
     * @param field - массив точек на поле
     * @param checkedId - id выбраной точки (-1, если ни одна не выбрана)
     */
    fun setGame(field: Array<Point>, lines: Array<Line>, checkedId: Int)

}