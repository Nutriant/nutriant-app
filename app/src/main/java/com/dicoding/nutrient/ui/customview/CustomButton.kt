package com.dicoding.nutrient.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.nutrient.R

class CustomButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var enabledBackground: Drawable =
        ContextCompat.getDrawable(context, R.drawable.bg_button_enable) as Drawable
    private var disabledBackground: Drawable =
        ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (isEnabled) enabledBackground else disabledBackground
    }

    fun setButtonEnableRegister(
        etNameResult: CharSequence?,
        etEmailResult: CharSequence?,
        etPasswordResult: CharSequence?
    ) {
        isEnabled = ((etNameResult != null && etNameResult.toString().isNotEmpty())) &&
                ((etEmailResult != null && etEmailResult.toString().isNotEmpty())) &&
                ((etPasswordResult != null && etPasswordResult.toString().isNotEmpty()))
    }

    fun setButtonEnableLogin(
        etEmailResult: CharSequence?,
        etPasswordResult: CharSequence?
    ) {
        isEnabled =
            ((etEmailResult != null && etEmailResult.toString().isNotEmpty())) &&
                    ((etPasswordResult != null && etPasswordResult.toString().isNotEmpty()))
    }

}