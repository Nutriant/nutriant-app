package com.dicoding.nutrient.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.nutrient.R
import com.google.android.material.textfield.TextInputLayout

class CustomEditTextPassword : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG_BEFORE_TEXT_CHANGE, s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString().trim()
                error = if (text.isNotEmpty() && text.length < 8) {
                    context.getString(R.string.eight_char_password)
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val textInputLayout = parent.parent as TextInputLayout
                if (error.isNullOrBlank()) {
                    textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                } else {
                    textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
                }
                Log.d(TAG_AFTER_TEXT_CHANGE, s.toString())
            }
        })
    }

    companion object {
        private const val TAG_BEFORE_TEXT_CHANGE = "BeforeTextChangedCustomEditText"
        private const val TAG_AFTER_TEXT_CHANGE = "AfterTextChangedCustomEditText"
    }
}