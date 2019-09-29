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
    private var gameLevel: GameLevel = GeneratorLevel().generateLevel()

    override fun attachView(view: GameView) {
        super.attachView(view)
        viewState.setFirstGame(gameLevel.field, gameLevel.lines)
    }

    fun onTouch(x: Float, y: Float, radius: Float) {
        for (p in gameLevel.field) {
            if (((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y)) <= radius * radius) {
                if (gameLevel.checkedId == -1) {
                    p.checked = true
                    gameLevel.checkedId = p.id
                    viewState.setGame(gameLevel.field, gameLevel.lines)
                } else {
                    if (gameLevel.checkedId != p.id) {
                        gameLevel.swapPoint(gameLevel.checkedId, p.id)
                        gameLevel.offChecked()
                        viewState.setGame(gameLevel.field, gameLevel.lines)
                        if (gameLevel.checkWin()) {//Вин
                        }
                    }
                }
                return
            }
        }
        if (gameLevel.checkedId != -1) {
            gameLevel.offChecked()
            viewState.setGame(gameLevel.field, gameLevel.lines)
        }
    }
}