package com.example.kacperopyrchal.petinder

import android.graphics.PorterDuff
import android.widget.Button

fun Button.adjustColor(id: Int) {
    val color = resources.getColor(id, null)
    background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
}