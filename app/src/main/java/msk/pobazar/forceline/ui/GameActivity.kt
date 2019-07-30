package msk.pobazar.forceline.ui

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import msk.pobazar.forceline.R
import msk.pobazar.forceline.entities.Line
import msk.pobazar.forceline.views.GameView
import android.view.SurfaceHolder
import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceView


class GameActivity : MvpAppCompatActivity(), GameView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    class DrawView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

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

        class DrawThread(private val surfaceHolder: SurfaceHolder) : Thread() {

            var running = false

            override fun run() {
                var canvas: Canvas?
                while (running) {
                    canvas = null
                    try {
                        canvas = surfaceHolder.lockCanvas(null)
                        if (canvas == null)
                            continue
                        canvas.drawColor(Color.GREEN)
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas)
                        }
                    }
                }
            }
        }
    }

    override fun setField(field: Array<Point>, lines: Array<Line>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCheckedPoint(point: Point, checked: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
