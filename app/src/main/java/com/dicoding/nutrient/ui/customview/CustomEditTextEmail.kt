package com.dicoding.nutrient.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.nutrient.R

class CustomEditTextEmail : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG_BEFORE_TEXT_CHANGE, s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString().trim()
                error = if (text.isNotEmpty() && !isValidEmail(text)) {
                    context.getString(R.string.email_format_not_valid)
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG_AFTER_TEXT_CHANGE, s.toString())
            }
        })
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    companion object {
        private const val TAG_BEFORE_TEXT_CHANGE = "BeforeTextChangedCustomEditText"
        private const val TAG_AFTER_TEXT_CHANGE = "AfterTextChangedCustomEditText"
    }
}