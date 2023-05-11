package com.example.walkiewalkie.stepActivity

import android.content.Context

class ScreenUtil {
    companion object {
        fun getScreenWidth(mContext: Context): Int {

            val displayMetrics = mContext.resources.displayMetrics
            val widthPixels = displayMetrics.widthPixels
            val heightPixels = displayMetrics.heightPixels
            val density = displayMetrics.density

            return widthPixels
        }
    }
}