package com.example.walkiewalkie.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DatabaseHelper class to manage database creation and version management
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "CoinDatabase", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Coins (id INTEGER PRIMARY KEY, total INTEGER)")
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
}

