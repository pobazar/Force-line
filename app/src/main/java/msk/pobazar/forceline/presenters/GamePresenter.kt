package msk.pobazar.forceline.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import msk.pobazar.forceline.entities.GameLevel
import msk.pobazar.forceline.utils.GeneratorLevel
import msk.pobazar.forceline.views.GameView

@InjectViewState
class GamePresenter : MvpPresenter<GameView>() {
    private lateinit var view: GameView
    private var gameLevel: GameLevel = GeneratorLevel().generateLevel()

    override fun attachView(view: GameView) {
        super.attachView(view)
        this.view = view
    }

    fun start() {
        view.setField(gameLevel.field, gameLevel.lines)
    }
}