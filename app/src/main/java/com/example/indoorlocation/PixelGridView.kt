package com.example.indoorlocation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PixelGridView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private var numColumns = 0
    private var numRows = 0
    private var cellWidth = 0
    private var cellHeight = 0
    private val blackPaint: Paint = Paint()
    private lateinit var cellChecked: Array<BooleanArray>

    constructor(context: Context?) : this(context, null) {}

    init {
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE)
    }

    fun setNumColumns(numColumns: Int) {
        this.numColumns = numColumns
        calculateDimensions()
    }

    fun getNumColumns(): Int {
        return numColumns
    }

    fun setNumRows(numRows: Int) {
        this.numRows = numRows
        calculateDimensions()
    }

    fun getNumRows(): Int {
        return numRows
    }

    fun changeCell(column: Int, row: Int) {
        cellChecked[column][row] = !cellChecked[column][row]
        invalidate()
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateDimensions()
    }

    private fun calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return
        }
        cellWidth = getWidth() / numColumns
        cellHeight = getHeight() / numRows
        cellChecked = Array(numColumns) { BooleanArray(numRows) }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        if (numColumns == 0 || numRows == 0) {
            return
        }
        val width: Int = getWidth()
        val height: Int = getHeight()
        for (i in 0 until numColumns) {
            for (j in 0 until numRows) {
                if (cellChecked[i][j]) {
                    canvas.drawRect(
                        (i * cellWidth).toFloat(), (j * cellHeight).toFloat(),
                        ((i + 1) * cellWidth).toFloat(), ((j + 1) * cellHeight).toFloat(),
                        blackPaint
                    )
                }
            }
        }
        for (i in 1 until numColumns) {
            canvas.drawLine((i * cellWidth).toFloat(), 0F,
                (i * cellWidth).toFloat(), height.toFloat(), blackPaint)
        }
        for (i in 1 until numRows) {
            canvas.drawLine(0F, (i * cellHeight).toFloat(),
                width.toFloat(), (i * cellHeight).toFloat(), blackPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.getAction() === MotionEvent.ACTION_DOWN) {
            val column = Math.round(event.getX() / cellWidth)
            val row = Math.round(event.getY() / cellHeight)
            cellChecked[column][row] = !cellChecked[column][row]
            invalidate()
        }
        return true
    }
}