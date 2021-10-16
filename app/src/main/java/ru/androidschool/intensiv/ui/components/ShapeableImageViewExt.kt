package ru.androidschool.intensiv.ui.components

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class ShapeableImageViewExt @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : ShapeableImageView(context, attrs, style) {
    var src: String? = ""
        set(path: String?) {
            Picasso.get()
                .load(path)
                .into(this)
            field = path
        }
}
