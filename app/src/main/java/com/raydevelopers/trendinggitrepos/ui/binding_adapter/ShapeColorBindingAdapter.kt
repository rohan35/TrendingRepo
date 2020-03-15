package com.raydevelopers.trendinggitrepos.ui.binding_adapter

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.raydevelopers.trendinggitrepos.R
import androidx.core.content.ContextCompat
import android.graphics.drawable.GradientDrawable



@BindingAdapter("shapeColor")
fun loadShape(textView: TextView, color: String) {
    val mDrawable = ContextCompat.getDrawable(textView.context, R.drawable.language_image_bg)
    val  shape =   mDrawable as (GradientDrawable)
    shape.setColor(Color.parseColor(color))
    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.language_image_bg,0,0,0)

}