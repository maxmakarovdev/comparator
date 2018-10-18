package com.maximmakarov.comparator.core.extensions

import android.content.Context
import android.content.DialogInterface
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.maximmakarov.comparator.R


fun Context.dpToPx(dp: Int) = Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

fun Context.getColorCompat(@ColorRes colorResId: Int) = ContextCompat.getColor(this, colorResId)

fun Context.getDrawableCompat(@DrawableRes drawableResId: Int) = ContextCompat.getDrawable(this, drawableResId)


fun Context.inputDialog(@StringRes title: Int, text: String, @StringRes hint: Int,
                        @StringRes posTitle: Int, posAction: (dialog: DialogInterface, text: String) -> Unit,
                        @StringRes negTitle: Int, negAction: (dialog: DialogInterface) -> Unit) {

    val view = View.inflate(this, R.layout.dialog_edit_text, null)
    view.findViewById<TextInputLayout>(R.id.textLayout).apply {
        setHint(getString(hint))
    }
    val edit = view.findViewById<TextInputEditText>(R.id.text).apply {
        setText(text)
        setSelection(text.length)
    }

    AlertDialog.Builder(this)
            .setTitle(title)
            .setView(view)
            .setPositiveButton(posTitle) { dialog, _ -> posAction(dialog, edit.text.toString()) }
            .setNegativeButton(negTitle) { dialog, _ -> negAction(dialog) }
            .setCancelable(false)
            .show()
}