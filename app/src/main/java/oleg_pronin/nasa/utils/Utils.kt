package oleg_pronin.nasa.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.createSnackbarAndShow(
    text: String,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, length).show()
}

fun View.createSnackbarAndShow(
    resourcesId: Int,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, resources.getText(resourcesId) , length).show()
}