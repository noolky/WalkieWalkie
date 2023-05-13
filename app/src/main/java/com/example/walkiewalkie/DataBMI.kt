package com.example.walkiewalkie

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBMI(context: Context) : SQLiteOpenHelper(context, "bmiTable.db", null, 1) {
    private val tableNameBMI="bmiTable"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $tableNameBMI (id INTEGER PRIMARY KEY AUTOINCREMENT, weight TEXT, height TEXT, bmi REAL)"
        )
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $tableNameBMI")
        onCreate(db)
    }


    // save BMI
    fun saveBMI(weight: String, height: String, bmi: Float) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("weight", weight)
        values.put("height", height)
        values.put("bmi", bmi)

        db.insert(tableNameBMI, null, values)
        db.close()
    }

    //get Lastest BMI
    fun getLastestBMI(): Float {
        val db = readableDatabase

        val cursor: Cursor? = db.query(
            tableNameBMI,
            arrayOf("bmi"),
            null,
            null,
            null,
            null,
            "id DESC",
            "1"
        )
        var lastestBMI: Float? = null

        if (cursor != null && cursor.moveToFirst()) {
            val bmiIndex = cursor.getColumnIndex("bmi")
            if (!cursor.isNull(bmiIndex)) {
                lastestBMI = cursor.getFloat(bmiIndex)
            }
            cursor.close()
        }

        db.close()
        return lastestBMI ?:0f
    }

}