package com.unifest.android.core.common.utils

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

// https://developer.android.com/develop/ui/compose/quick-guides/content/auto-format-phone-number?hl=ko
class PhoneNumberVisualTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        val text = toString()
        val trimmed = if (text.length >= 11) text.substring(0..10) else text
        val out = StringBuilder()

        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 2 || i == 6) out.append('-')
        }

        replace(0, length, out.toString())
    }
}
