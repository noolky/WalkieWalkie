package com.example.walkiewalkie.stepActivity


import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.*
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.walkiewalkie.R
import java.text.DecimalFormat
import java.util.*


class MainActivity : BaseActivity(), Handler.Callback {
    private var calenderView: BeforeOrAfterCalendarView? = null
    private var curSelDate: String = ""
    private val df = DecimalFormat("#.##")
    private val stepEntityList: MutableList<StepEntity> = ArrayList()
    private var stepData: DBstep? = null
    private var isBind = false
    private val mGetReplyMessenger = Messenger(Handler(this))
    private var messenger: Messenger? = null
    private var timerTask: TimerTask? = null
    private var timer: Timer? = null
    private var movementRecordsCalenderLL: LinearLayout? = null
    private lateinit var isSupportTv: TextView
    private lateinit var movementTotalStepsTv:TextView
    private lateinit var movementTotalKmTv: TextView
    private lateinit var movementTotalKmTimeTv: TextView
    private lateinit var movementTotalStepsTimeTv: TextView




    override fun getLayoutId(): Int {
        return R.layout.activity_step
    }

    override fun initData() {
        movementTotalStepsTv = findViewById(R.id.movement_total_steps_tv)
        movementRecordsCalenderLL = findViewById(R.id.movement_records_calender_ll)
        movementTotalStepsTv = findViewById(R.id.movement_total_steps_tv)
        isSupportTv = findViewById(R.id.is_support_tv)
        movementTotalKmTv = findViewById(R.id.movement_total_km_tv)
        movementTotalKmTimeTv = findViewById(R.id.movement_total_km_time_tv)
        movementTotalStepsTimeTv = findViewById(R.id.movement_total_steps_time_tv)

        curSelDate = TimeUtil.getCurrentDate()
        calenderView = BeforeOrAfterCalendarView(this)
        movementRecordsCalenderLL?.addView(calenderView)
        requestPermission()
    }

    override fun initListener() {
        calenderView?.setOnBoaCalenderClickListener(object :
            BeforeOrAfterCalendarView.BoaCalenderClickListener {
            override fun onClickToRefresh(position: Int, curDate: String) {
                curSelDate = curDate
                //get data by date
                setDatas()
            }
        })
    }

    private fun requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    1
                )
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACTIVITY_RECOGNITION
                    )
                ) {
                    //Pop up asking permission
                    Toast.makeText(this, "Please allow the permission in order to use the apps.", Toast.LENGTH_SHORT).show()
                }
            } else {
                startStepService()
            }
        } else {
            startStepService()
        }
    }
    private fun startStepService() {
        /**
         * To check device support or not
         */
        if (StepCountCheckUtil.isSupportStepCountSensor(this)) {
            getRecordList()
            isSupportTv.visibility = View.GONE
            setDatas()
            setupService()
        } else {
            movementTotalStepsTv.text = "0"
            movementTotalKmTv.text="0"
            isSupportTv.visibility = View.VISIBLE
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startStepService()
        } else {
            Toast.makeText(this, "Please allow the permission in order to use the app.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupService() {
        val intent = Intent(this, StepService::class.java)
        isBind = bindService(intent, conn, BIND_AUTO_CREATE)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) startForegroundService(intent)
        else startService(intent)
    }

    private val conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            timerTask = object : TimerTask() {
                override fun run() {
                    try {
                        messenger = Messenger(service)
                        val msg = Message.obtain(null, ConstantData.MSG_FROM_CLIENT)
                        msg.replyTo = mGetReplyMessenger
                        messenger!!.send(msg)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
            }
            timer = Timer()
            timer!!.schedule(timerTask, 0, 500)
        }

        override fun onServiceDisconnected(name: ComponentName) {
        }
    }

    private fun setDatas() {
        val stepEntity = stepData?.getCurDataByDate(curSelDate)

        if (stepEntity != null) {
            val steps = stepEntity.steps?.let { Integer.parseInt(it) }
            //get step
            movementTotalStepsTv.text = steps.toString()
            //get step in km
            movementTotalKmTv.text = steps?.let { countTotalKM(it) }
        } else {
            movementTotalStepsTv.text = "0"
            movementTotalKmTv.text = "0"
        }
        //set time
        val time = TimeUtil.getWeekStr(curSelDate)
        movementTotalKmTimeTv.text = time
        movementTotalStepsTimeTv.text = time
    }

    /**
     *calculation in km
     */
    private fun countTotalKM(steps: Int): String {
        val totalMeters = steps * 0.7
        //keep in 2 decimal
        return df.format(totalMeters / 1000)
    }

    /**
     * get step record
     */
    private fun getRecordList() {
        stepData = DBstep(this)
        stepEntityList.clear()
        stepEntityList.addAll(stepData!!.getAllDatas())
        if (stepEntityList.size > 7) {
            //after 7 days, the previous record will be deleted
            for (entity in stepEntityList) {
                if (TimeUtil.isDateOutDate(entity.curDate!!)) {
                    stepData?.deleteCurData(entity.curDate!!)
                }
            }
        }
    }

    override fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            //get data from server
            ConstantData.MSG_FROM_SERVER ->
                //update date if the date is today
                if (curSelDate == TimeUtil.getCurrentDate()) {
                    //record steps
                    val steps = msg.data.getInt("steps")
                    //set step
                    movementTotalStepsTv.text = steps.toString()
                    //count total step in KM
                    movementTotalKmTv.text = countTotalKM(steps)
                }
        }
        return false
    }

    //avoid memory leaks so we need onDestroy the isBind
    override fun onDestroy() {
        super.onDestroy()
        if (isBind) this.unbindService(conn)
    }
}
