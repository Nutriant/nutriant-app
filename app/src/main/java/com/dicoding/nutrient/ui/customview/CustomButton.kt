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

    fun setButtonEnableAssestment(
        etHeightResult: CharSequence?,
        etWeightResult: CharSequence?
    ) {
        isEnabled = (etWeightResult != null && etWeightResult.toString().isNotEmpty()) &&
                (etHeightResult != null && etHeightResult.toString().isNotEmpty())
    }

    fun setButtonEnableChangePassword(
        etPasswordResult: CharSequence?,
        etPasswordResultConfirmation: CharSequence?
    ) {
        isEnabled = (etPasswordResult != null && etPasswordResult.toString().isNotEmpty()) &&
                (etPasswordResultConfirmation != null && etPasswordResultConfirmation.toString()
                    .isNotEmpty())
    }

    fun setButtonEnableFillFnBManual(
        etFoodNameResult: CharSequence?,
        etPortionResult: CharSequence?,
        etServingGramResult: CharSequence?,
        etProteinResult: CharSequence?,
        etFatResult: CharSequence?,
        etCarbohydrateResult: CharSequence?
    ) {
        isEnabled = etPortionResult != null && etPortionResult.toString().isNotEmpty() &&
                etServingGramResult != null && etServingGramResult.toString().isNotEmpty() &&
                (etFoodNameResult != null && etFoodNameResult.toString().isNotEmpty()) &&
                (etProteinResult != null && etProteinResult.toString().isNotEmpty()) &&
                (etFatResult != null && etFatResult.toString().isNotEmpty()) &&
                (etCarbohydrateResult != null && etCarbohydrateResult.toString().isNotEmpty())
    }

    fun setButtonInformationLog(
        etFoodNameResult: CharSequence?,
        etProteinResult: CharSequence?,
        etFatResult: CharSequence?,
        etCarbohydrateResult: CharSequence?
    ) {
        isEnabled =
            (etFoodNameResult != null && etFoodNameResult.toString().isNotEmpty()) &&
                    (etProteinResult != null && etProteinResult.toString().isNotEmpty()) &&
                    (etFatResult != null && etFatResult.toString().isNotEmpty()) &&
                    (etCarbohydrateResult != null && etCarbohydrateResult.toString().isNotEmpty())
    }

    fun setButtonPersonalData(
        etNameResult: CharSequence?,
        etDOBResult: CharSequence?,
        etGenderResult: CharSequence?,
        etHeightResult: CharSequence?,
        etWeightResult: CharSequence?
    ) {
        isEnabled = ((etNameResult != null && etNameResult.toString().isNotEmpty())) &&
                ((etDOBResult != null && etDOBResult.toString().isNotEmpty())) &&
                ((etGenderResult != null && etGenderResult.toString().isNotEmpty())) &&
                ((etHeightResult != null && etHeightResult.toString().isNotEmpty())) &&
                ((etWeightResult != null && etWeightResult.toString().isNotEmpty()))
    }


}