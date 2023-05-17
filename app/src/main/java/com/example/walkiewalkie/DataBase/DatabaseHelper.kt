package com.example.walkiewalkie.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DatabaseHelper class to manage database creation and version management
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "CoinDatabase", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Coins (id INTEGER PRIMARY KEY, total INTEGER, last_view_timestamp INTEGER DEFAULT 0, vChecker1 INTEGER, vChecker2 INTEGER, vChecker3 INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // not needed for this example
    }

    // Method to insert or update total coins in database
    fun updateTotalCoins(totalCoins: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", 1)
        contentValues.put("total", totalCoins)
        db.replace("Coins", null, contentValues)
        db.close()
    }

    // Method to retrieve total coins from database
    fun getTotalCoins(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT total FROM Coins WHERE id=1", null)
        var totalCoins = 0
        if (cursor.moveToFirst()) {
            totalCoins = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return totalCoins
    }

    // Method to update last view timestamp in database
    fun updateLastViewTimestamp(timestamp: Long) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("last_view_timestamp", timestamp)

        val whereClause = "id = ?"
        val whereArgs = arrayOf("1")
        db.update("Coins", contentValues, whereClause, whereArgs)

        db.close()
    }

    fun getLastViewTimestamp(): Long {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT last_view_timestamp FROM Coins WHERE id=1", null)
        var lastViewTimestamp = 0L
        if (cursor.moveToFirst()) {
            lastViewTimestamp = cursor.getLong(0)
        }
        cursor.close()
        db.close()
        return lastViewTimestamp
    }

    fun updateVChecker1(value: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("vChecker1", value)

        val whereClause = "id = ?"
        val whereArgs = arrayOf("1")
        db.update("Coins", contentValues, whereClause, whereArgs)
        db.close()
    }

    fun updateVChecker2(value: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("vChecker2", value)

        val whereClause = "id = ?"
        val whereArgs = arrayOf("1")
        db.update("Coins", contentValues, whereClause, whereArgs)
        db.close()
    }

    fun updateVChecker3(value: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("vChecker3", value)
        val whereClause = "id = ?"
        val whereArgs = arrayOf("1")
        db.update("Coins", contentValues, whereClause, whereArgs)
        db.close()
    }

    fun getVChecker1(): Int {
        // Retrieve the value of VChecker1 from the database
        val db = this.readableDatabase
        val query = "SELECT vChecker1 FROM Coins WHERE id=1" // Use the correct table name here
        val cursor = db.rawQuery(query, null)

        var vChecker1 = 0
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("vChecker1")
            if (columnIndex != -1) {
                vChecker1 = cursor.getInt(columnIndex)
            }
        }

        cursor.close()
        db.close()

        return vChecker1
    }

    fun getVChecker2(): Int {
        // Retrieve the value of VChecker2 from the database
        val db = this.readableDatabase
        val query = "SELECT vChecker2 FROM Coins WHERE id=1" // Use the correct table name here
        val cursor = db.rawQuery(query, null)

        var vChecker2 = 0

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("vChecker2")
            if (columnIndex != -1) {
                vChecker2 = cursor.getInt(columnIndex)
            }
        }

        cursor.close()
        db.close()

        return vChecker2
    }

    fun getVChecker3(): Int {
        // Retrieve the value of VChecker3 from the database
        val db = this.readableDatabase
        val query = "SELECT vChecker3 FROM Coins WHERE id=1" // Use the correct table name here
        val cursor = db.rawQuery(query, null)

        var vChecker3 = 0

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("vChecker3")
            if (columnIndex != -1) {
                vChecker3 = cursor.getInt(columnIndex)
            }
        }

        cursor.close()
        db.close()

        return vChecker3
    }

}
