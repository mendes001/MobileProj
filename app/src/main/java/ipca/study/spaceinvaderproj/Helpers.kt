package ipca.study.spaceinvaderproj

import android.text.TextUtils
import android.util.Patterns

fun String.isPasswordValid() : Boolean{
    return this.length>=6
}

fun String.isEmailValid() : Boolean{
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}