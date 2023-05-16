package com.example.walkiewalkie.AchievementStepActivity

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.walkiewalkie.DataBase.DatabaseHelper

class DBhelper(private val context: Context) : SQLiteOpenHelper(context, "StepDB", null, 1) {
    private val CREATE_BANNER = ("create table step ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "curDate TEXT, "
            + "totalSteps TEXT)")

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_BANNER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun addNewData(stepEntity: StepEntity) {
        val stepDb = readableDatabase
        val values = ContentValues()
        values.put("curDate", stepEntity.curDate)
        values.put("totalSteps", stepEntity.steps)
        stepDb.insert("step", null, values)
        stepDb.close()
    }

    fun getCurDataByDate(curDate: String): StepEntity? {
        val stepDb = readableDatabase
        var stepEntity: StepEntity? = null
        val cursor = stepDb.query("step", null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val date = cursor.getString(cursor.getColumnIndexOrThrow("curDate"))
            if (curDate == date) {
                val steps = cursor.getString(cursor.getColumnIndexOrThrow("totalSteps"))
                stepEntity = StepEntity(date, steps)
                break
            }
        }
        stepDb.close()
        cursor.close()
        return stepEntity
    }

    fun getAllDatas(): List<StepEntity> {
        val dataList: MutableList<StepEntity> = ArrayList()
        val stepDb = readableDatabase
        val cursor = stepDb.rawQuery("select * from step", null)

        val curDateIndex = cursor.getColumnIndex("curDate")
        val totalStepsIndex = cursor.getColumnIndex("totalSteps")

        while (cursor.moveToNext()) {
            val curDate = if (curDateIndex != -1) cursor.getString(curDateIndex) else ""
            val totalSteps = if (totalStepsIndex != -1) cursor.getString(totalStepsIndex) else ""
            val entity = StepEntity(curDate, totalSteps)
            dataList.add(entity)

            if (totalSteps.toInt() > 10000) {
                val databaseHelper = DatabaseHelper(context)
                databaseHelper.updateTotalCoins(databaseHelper.getTotalCoins() + 5)
            }
            else if(totalSteps.toInt()>20000){
                val databaseHelper = DatabaseHelper(context)
                databaseHelper.updateTotalCoins(databaseHelper.getTotalCoins() + 10)
            }
            else if (totalSteps.toInt()>50000){
                val databaseHelper = DatabaseHelper(context)
                databaseHelper.updateTotalCoins(databaseHelper.getTotalCoins() + 60)
            }
        }
        // Close the database and cursor
        stepDb.close()
        cursor.close()
        return dataList
    }

    fun updateCurData(stepEntity: StepEntity) {
        val stepDb = readableDatabase

        val values = ContentValues()
        values.put("curDate", stepEntity.curDate)
        values.put("totalSteps", stepEntity.steps)
        stepDb.update("step", values, "curDate=?", arrayOf(stepEntity.curDate))
        stepDb.close()
    }

    fun deleteCurData(curDate: String) {
        val stepDb = readableDatabase

        if (stepDb.isOpen)
            stepDb.delete("step", "curDate", arrayOf(curDate))
        stepDb.close()
    }
}
