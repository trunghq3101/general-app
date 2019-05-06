package com.trunghoang.generalapp

import android.content.Context
import android.graphics.Canvas
import android.os.Debug
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        Debug.startMethodTracing("Recycler")
        super.onMeasure(widthSpec, heightSpec)
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        Debug.stopMethodTracing()
    }
}