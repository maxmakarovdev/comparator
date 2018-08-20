package com.maximmakarov.comparator.core.ext

import android.content.Context
import android.content.DialogInterface
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.maximmakarov.comparator.R


fun Context.dpToPx(dp: Int) = Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

fun Context.getColorCompat(@ColorRes colorResId: Int) = ContextCompat.getColor(this, colorResId)

fun Context.getDrawableCompat(@DrawableRes drawableResId: Int) = ContextCompat.getDrawable(this, drawableResId)


fun Context.inputDialog(title: Int, text: String, hint: Int,
                        posTitle: Int, posAction: (dialog: DialogInterface, text: String) -> Unit,
                        negTitle: Int, negAction: (dialog: DialogInterface) -> Unit) {

    val view = View.inflate(this, R.layout.dialog_edit_text, null)
    val edit = view.findViewById<EditText>(R.id.text).apply {
        setHint(hint)
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