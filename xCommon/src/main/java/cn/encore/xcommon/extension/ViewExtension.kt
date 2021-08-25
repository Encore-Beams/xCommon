package cn.encore.xcommon.extension

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat

/**
 *
 * Author: encore
 * Date  : 8/10/21
 * Email : encorebeams@outlook.com
 */
fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


/**
 * View artificial attribute that sets compound left drawable resource
 */
var TextView.drawableLeftResource: Int
    get() = throw IllegalAccessException("Property drawableLeftResource only as setter")
    set(value) {
        drawableLeft = ContextCompat.getDrawable(context, value)
    }

/**
 * View artificial attribute that sets compound right drawable resource
 */
var TextView.drawableRightResource: Int
    get() = throw IllegalAccessException("Property drawableRightResource only as setter")
    set(value) {
        drawableRight = ContextCompat.getDrawable(context, value)
    }

/**
 * View artificial attribute that sets compound top drawable resource
 */
var TextView.drawableTopResource: Int
    get() = throw IllegalAccessException("Property drawableTopResource only as setter")
    set(value) {
        drawableTop = ContextCompat.getDrawable(context, value)
    }

/**
 * View artificial attribute that sets compound bottom drawable resource
 */
var TextView.drawableBottomResource: Int
    get() = throw IllegalAccessException("Property drawableBottomResource only as setter")
    set(value) {
        drawableBottom = ContextCompat.getDrawable(context, value)
    }

/**
 * View artificial attribute that sets compound left drawable
 */
var TextView.drawableLeft: Drawable?
    get() = throw IllegalAccessException("Property drawableLeft only as setter")
    set(value) {
        val drawables = compoundDrawables
        setCompoundDrawablesWithIntrinsicBounds(value, drawables[1], drawables[2], drawables[3])
    }

/**
 * View artificial attribute that sets compound right drawable
 */
var TextView.drawableRight: Drawable?
    get() = throw IllegalAccessException("Property drawableRight only as setter")
    set(value) {
        val drawables = compoundDrawables
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], value, drawables[3])
    }

/**
 * View artificial attribute that sets compound top drawable
 */
var TextView.drawableTop: Drawable?
    get() = throw IllegalAccessException("Property drawableTop only as setter")
    set(value) {
        val drawables = compoundDrawables
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], value, drawables[2], drawables[3])
    }

/**
 * View artificial attribute that sets compound bottom drawable
 */
var TextView.drawableBottom: Drawable?
    get() = throw IllegalAccessException("Property drawableBottom only as setter")
    set(value) {
        val drawables = compoundDrawables
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], value)
    }


const val CLICK_THROTTLE_DELAY = 200L

/**
 * Set a callback on a view that responds to a click events. It throttles clicks
 * so rapid burst of clicks does not emit multiple callback calls.
 *
 * @param throttleDelay - optional delay in milliseconds between clicks
 */
fun View.onThrottledClick(
    throttleDelay: Long = CLICK_THROTTLE_DELAY,
    onClick: (View) -> Unit
) {
    setOnClickListener {
        onClick(this)
        isClickable = false
        postDelayed({ isClickable = true }, throttleDelay)
    }
}