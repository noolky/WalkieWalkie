package com.example.walkiewalkie.stepActivity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBhelper(mContext : Context) : SQLiteOpenHelper(mContext, "Assignment.db", null,1) {
    private val DB_NAME = "Assignment.db"
    private val DB_VERSION = 1

    private val CREATE_BANNER = ("create table step ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "curDate TEXT, "
            + "totalSteps TEXT)")


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_BANNER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }



}