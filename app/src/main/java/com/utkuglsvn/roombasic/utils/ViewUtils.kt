package com.utkuglsvn.roombasic.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.utkuglsvn.roombasic.R

//Edit Text
fun EditText.textToInt(): Int = this.text.toString().toInt()

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

//toast
fun Context?.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//Action bar
fun Activity.isShowActionBar(isShow: Boolean) {
    if (!isShow)
        (this as AppCompatActivity?)?.supportActionBar?.hide()
    else
        (this as AppCompatActivity?)?.supportActionBar?.show()
}

//input check
fun inputCheck(name: Editable, quantity: Editable) = name.isNotEmpty() && quantity.isNotEmpty()

//dialog
fun Context?.deleteUserDialog(positiveButtonClick: DialogInterface.OnClickListener) {
    val builder = AlertDialog.Builder(this)
    with(builder)
    {
        setTitle(this@deleteUserDialog?.getString(R.string.list_item_remove))
        setPositiveButton(
            this@deleteUserDialog?.getString(R.string.yes),
            positiveButtonClick
        )
        setNegativeButton(this@deleteUserDialog?.getString(R.string.no), negativeButtonClick)
        show()
    }
}


private val negativeButtonClick = { dialog: DialogInterface, which: Int ->
    dialog.cancel()
}