package com.example.walkiewalkie.stepActivity

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.walkiewalkie.R


class RecordsCalenderItemView(
    mContext: Context,//date and time
    private val weekStr: String,
    private val dateStr: String,
    val position: Int,
    private var curItemDate: String
) : RelativeLayout(mContext) {

    private var itemLl: LinearLayout? = null
    private var lineView: View? = null
    private var weekTv: TextView? = null
    private var dateRl: RelativeLayout? = null
    private var dateTv: TextView? = null


    private var itemClick: OnCalenderItemClick? = null

    interface OnCalenderItemClick {
        fun onCalenderItemClick()
    }

    fun setOnCalenderItemClick(itemClick: OnCalenderItemClick) {
        this.itemClick = itemClick
    }

    init {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.records_calender_item_view, this)
        itemLl = itemView.findViewById(R.id.records_calender_item_ll)
        weekTv = itemView.findViewById(R.id.records_calender_item_week_tv)
        lineView = itemView.findViewById(R.id.calendar_item_line_view)
        dateRl = itemView.findViewById(R.id.records_calender_item_date_rl)
        dateTv = itemView.findViewById(R.id.records_calender_item_date_tv)

        weekTv!!.textSize = 15f
        lineView!!.visibility = View.GONE

        weekTv!!.text = weekStr
        dateTv!!.text = dateStr

        itemView.layoutParams = LayoutParams(
            ScreenUtil.getScreenWidth(mContext) / 7,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        itemView.setOnClickListener { itemClick!!.onCalenderItemClick() }

    }

    fun setChecked(checkedFlag: Boolean) {
        if (checkedFlag) {
            //selected date
            weekTv!!.setTextColor(resources.getColor(R.color.main_text_color))
            dateTv!!.setTextColor(resources.getColor(R.color.white))
            dateRl!!.setBackgroundResource(R.mipmap.ic_blackk_round_bg)
        } else {
            //not selected date
            weekTv!!.setTextColor(resources.getColor(R.color.gray_default_dark))
            dateTv!!.setTextColor(resources.getColor(R.color.gray_default_dark))
            dateRl!!.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}
