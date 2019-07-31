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
import android.util.Log
import android.view.MotionEvent
import com.arellomobile.mvp.presenter.InjectPresenter
import msk.pobazar.forceline.presenters.GamePresenter


class GameActivity : MvpAppCompatActivity(), GameView {
    @InjectPresenter
    lateinit var gamePresenter: GamePresenter

    val radius = 50F
    val strokeWidth = 5F

    lateinit var field: Array<Point>
    lateinit var lines: Array<Line>
    var paintPointBlue: Paint = Paint()
    var paintPointRed: Paint = Paint()
    var paintLineBlue: Paint = Paint()
    var paintLineRed: Paint = Paint()
    var x = 0F
    var y = 0F
    var checkedId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        paintPointBlue.color = Color.BLUE
        paintPointRed.color = Color.RED
        paintLineBlue.color = Color.BLUE
        paintLineBlue.strokeWidth = strokeWidth
        paintLineRed.color = Color.RED
        paintLineRed.strokeWidth = strokeWidth
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        x = event.x
        y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("touch", "Нажатие вниз $x,$y")
                for (p in field) {
                    if (((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y)) <= radius * radius) {
                        Log.d("touch", "Нажатие на точку")
                        p.checked = true
                        if ((checkedId == -1) || (checkedId == p.id))
                            checkedId = p.id
                        else {
                           swapPoint(checkedId, p.id)
                        }
                        break
                    }
                }
                if (checkedId == -1)
                    field[checkedId].checked = false
            }
        }
        return super.onTouchEvent(event)
    }

    fun swapPoint(id1: Int, id2: Int){

    }

    /**
     * Рисование игры
     */
    fun drawGame(canvas: Canvas) {
        drawLines(canvas)
        drawPoints(canvas)
    }

    /**
     * Рисование точек
     */
    fun drawPoints(canvas: Canvas) {
        for (p in field) {
            if (p.checked)
                canvas.drawCircle(p.x, p.y, radius, paintPointRed)
            else
                canvas.drawCircle(p.x, p.y, radius, paintPointBlue)
        }
    }

    /**
     * Рисование линий
     */
    fun drawLines(canvas: Canvas) {
        for (l in lines) {
            if (l.status)
                canvas.drawLine(l.end.x, l.end.y, l.start.x, l.start.y, paintLineBlue)
            else
                canvas.drawLine(l.end.x, l.end.y, l.start.x, l.start.y, paintLineRed)
        }
    }

    /**
     * Отображает поле на экране
     */
    override fun setField(field: Array<Point>, lines: Array<Line>) {
        this.field = field
        this.lines = lines
        setContentView(DrawView(this))
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
