package com.dicoding.nutrient.ui.customview

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

class CustomTextInputLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private var previousEndIconMode = END_ICON_NONE

    override fun setError(errorText: CharSequence?) {
        previousEndIconMode = endIconMode
        super.setError(errorText)
        if (errorText.isNullOrEmpty()) {
            endIconMode = previousEndIconMode
        }
    }
}