package msk.pobazar.forceline.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import msk.pobazar.forceline.entities.GameLevel
import msk.pobazar.forceline.ui.GameActivity
import msk.pobazar.forceline.utils.GeneratorLevel
import msk.pobazar.forceline.views.GameView

@InjectViewState
class GamePresenter : MvpPresenter<GameView>() {
    private lateinit var view: GameView
    private var gameLevel: GameLevel = GeneratorLevel().generateLevel()

    override fun attachView(view: GameView) {
        super.attachView(view)
        this.view = view
        view.setGame(gameLevel.field, gameLevel.lines, gameLevel.checkedId)
    }

    fun onTouch(x: Float, y: Float, radius: Float) {
        for (p in gameLevel.field) {
            if (((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y)) <= radius * radius) {
                if ((gameLevel.checkedId == -1) || (gameLevel.checkedId == p.id)) {
                    p.checked = true
                    gameLevel.checkedId = p.id
                } else {
                    p.checked = false
                    gameLevel.checkedId = -1
                    gameLevel.swapPoint(gameLevel.checkedId, p.id)
                }
                view.setGame(gameLevel.field, gameLevel.lines, gameLevel.checkedId)
                return
            }
        }
        if (gameLevel.checkedId != -1) {
            for (p in gameLevel.field) {
                if (p.id == gameLevel.checkedId) {
                    p.checked = false
                    gameLevel.checkedId = -1
                    view.setGame(gameLevel.field, gameLevel.lines, gameLevel.checkedId)
                    return
                }
            }
        }
    }
}