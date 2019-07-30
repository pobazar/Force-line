package msk.pobazar.forceline.ui

import android.content.Context
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import msk.pobazar.forceline.entities.Line
import msk.pobazar.forceline.views.GameView
import android.view.SurfaceHolder
import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceView
import msk.pobazar.forceline.entities.Point
import android.graphics.Paint
import msk.pobazar.forceline.entities.GameLevel
import msk.pobazar.forceline.presenters.GamePresenter
import msk.pobazar.forceline.utils.GeneratorLevel


class GameActivity : MvpAppCompatActivity(), GameView {

//    private val gamePresenter: GamePresenter = GamePresenter()
    private var gameLevel: GameLevel = GeneratorLevel().generateLevel()

    lateinit var field: Array<Point>
    lateinit var lines: Array<Line>
    var paint: Paint = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
        paint.color = Color.BLUE
        paint.strokeWidth = 10F
        field=gameLevel.field
        lines=gameLevel.lines
    }

    fun drawGame(canvas: Canvas) {
        //canvas.drawColor(Color.GREEN)
        for (p in field) {
            canvas.drawCircle(p.x, p.y, 50F, Paint())
        }
    }

    /**
     * Отображает поле на экране
     */
    override fun setField(field: Array<Point>, lines: Array<Line>) {
        this.field = field
        this.lines = lines
    }

    /**
     * Изменяет выделенность точки
     */
    override fun setCheckedPoint(point: Point, checked: Boolean) {

    }

    /**
     * Класс для отрисовки канвы
     */
    inner class DrawView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

        private var drawThread: DrawThread? = null

        init {
            holder.addCallback(this)
        }

        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
            drawThread = DrawThread(getHolder())
            drawThread?.running = true
            drawThread?.start()
        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            var retry = true
            drawThread?.running = false
            while (retry) {
                try {
                    drawThread?.join()
                    retry = false
                } catch (e: InterruptedException) {
                }
            }
        }

        inner class DrawThread(private val surfaceHolder: SurfaceHolder) : Thread() {

            var running = false

            override fun run() {
                var canvas: Canvas?
                while (running) {
                    canvas = null
                    try {
                        canvas = surfaceHolder.lockCanvas(null)
                        if (canvas == null)
                            continue
                        drawGame(canvas)
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas)
                        }
                    }
                }
            }
        }
    }
}
