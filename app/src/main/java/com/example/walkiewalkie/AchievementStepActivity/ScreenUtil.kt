package com.example.walkiewalkie.AchievementStepActivity

import android.content.Context

class ScreenUtil {
    companion object {
        fun getScreenWidth(mContext: Context): Int {

            val displayMetrics = mContext.resources.displayMetrics
            val widthPixels = displayMetrics.widthPixels
            return widthPixels
        }
    }
}


// val heightPixels = displayMetrics.heightPixels
// val density = displayMetrics.density