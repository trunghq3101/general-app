package com.trunghoang.generalapp

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import java.text.NumberFormat

@BindingAdapter("textInt")
fun EditText.bindInt(newInt: MutableLiveData<Int>) {
    newInt.value?.let {
        if (text.toString() != it.toString()) {
            setText(it.toString())
        }
    }
}

@BindingAdapter("textIntAttrChanged")
fun EditText.setOnTextIntChangeListener(listener: InverseBindingListener) {
    addTextChangedListener( object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.onChange()
            setSelection(text.length)
        }
    })
}

@InverseBindingAdapter(attribute = "textInt", event = "textIntAttrChanged")
fun EditText.getInt(): Int? = if (!text.isNullOrBlank()) {
    NumberFormat.getInstance().parse(text.toString()).toInt()
} else {
    null
}

