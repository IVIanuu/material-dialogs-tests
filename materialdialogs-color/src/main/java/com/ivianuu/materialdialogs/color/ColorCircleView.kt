/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.materialdialogs.color

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.Paint
import android.graphics.Paint.Style.FILL
import android.graphics.Paint.Style.STROKE
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat.getDrawable
import com.ivianuu.materialdialogs.util.MDUtil.dimenPx


internal class ColorCircleView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val strokePaint = Paint()
    private val fillPaint = Paint()

    private val borderWidth = dimenPx(R.dimen.color_circle_view_border)

    private var transparentGrid: Drawable? = null

    init {
        setWillNotDraw(false)
        strokePaint.style = STROKE
        strokePaint.isAntiAlias = true
        strokePaint.color = Color.BLACK
        strokePaint.strokeWidth = borderWidth.toFloat()
        fillPaint.style = FILL
        fillPaint.isAntiAlias = true
        fillPaint.color = Color.DKGRAY
    }

    var color: Int = Color.BLACK
        set(value) {
            field = value
            fillPaint.color = value
            invalidate()
        }
    var border: Int = Color.DKGRAY
        set(value) {
            field = value
            strokePaint.color = value
            invalidate()
        }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) = super.onMeasure(widthMeasureSpec, widthMeasureSpec)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (color == TRANSPARENT) {
            if (transparentGrid == null) {
                transparentGrid = getDrawable(context, R.drawable.transparentgrid)
            }
            transparentGrid?.setBounds(0, 0, measuredWidth, measuredHeight)
            transparentGrid?.draw(canvas)
        } else {
            canvas.drawCircle(
                measuredWidth / 2f,
                measuredHeight / 2f,
                (measuredWidth / 2f) - borderWidth,
                fillPaint
            )
        }
        canvas.drawCircle(
            measuredWidth / 2f,
            measuredHeight / 2f,
            (measuredWidth / 2f) - borderWidth,
            strokePaint
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        transparentGrid = null
    }
}
