package com.example.walkiewalkie.AchievementStepActivity

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {

    companion object {
        private val dateFormat = SimpleDateFormat("yyyyMMdd")
        private val mCalendar = Calendar.getInstance()
        private val weekStrings = arrayOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat")
        private val rWeekStrings = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

        fun changeFormatDate(date: String): String? {
            val dFormat = SimpleDateFormat("yyyy-MM-dd")
            var curDate: String? = null
            try {
                val dt = dateFormat.parse(date)
                curDate = dFormat.format(dt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return curDate
        }

        /**
         * Check the day is -7 or not
         */
        fun isDateOutDate(date: String): Boolean {
            try {
                if ((Date().time - dateFormat.parse(date).time) > 7 * 24 * 60 * 60 * 1000) return true

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return false
        }

        /**
         * current time
         */
        private fun getCurTime(): String {
            val dFormat = SimpleDateFormat("HH:mm")
            return "Today " + dFormat.format(System.currentTimeMillis())
        }

        /**
         * record week
         */
        fun getWeekStr(dateStr: String): String {

            val todayStr = dateFormat.format(mCalendar.time)

            if (todayStr == dateStr) {
                return getCurTime()
            }

            val preCalendar = Calendar.getInstance()
            preCalendar.add(Calendar.DATE, -1)
            val yesterdayStr = dateFormat.format(preCalendar.time)
            if (yesterdayStr == dateStr) {
                return "Yesterday"
            }

            var w = 0
            try {
                val date = dateFormat.parse(dateStr)
                val calendar = Calendar.getInstance()
                calendar.time = date
                w = calendar.get(Calendar.DAY_OF_WEEK) - 1
                if (w < 0) {
                    w = 0
                }

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return rWeekStrings[w]
        }


        /**
         * get the date of the month
         */
        public fun getCurrentDay(): Int {
            return mCalendar.get(Calendar.DATE)
        }

        /**
         * current date
         */
        fun getCurrentDate(): String {
            return dateFormat.format(mCalendar.getTime())
        }


        /**
         * datelist
         */
        fun dateListToDayList(dateList: List<String>): List<Int> {
            val calendar = Calendar.getInstance()
            val dayList: MutableList<Int> = ArrayList()
            for (date in dateList) {
                try {
                    calendar.setTime(dateFormat.parse(date))
                    val day = calendar.get(Calendar.DATE)
                    dayList.add(day)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

            }
            return dayList
        }


        /**
         * count the last week of today
         */
        fun getBeforeDateListByNow(): List<String> {
            val weekList: MutableList<String> = ArrayList()

            for (i in -6..0) {
                //consume monday is first day of the week
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, i)
                val date = dateFormat.format(calendar.time)
                weekList.add(date)
            }
            return weekList
        }

        /**
         * the week of today
         */
        fun getCurWeekDay(curDate: String): String {
            var w = 0
            try {
                val date = dateFormat.parse(curDate)
                val calendar = Calendar.getInstance()
                calendar.time = date
                w = calendar.get(Calendar.DAY_OF_WEEK) - 1
                if (w < 0) {
                    w = 0
                }

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return weekStrings[w]
        }
    }

}