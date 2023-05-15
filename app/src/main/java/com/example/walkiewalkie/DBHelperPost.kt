package com.example.walkiewalkie

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperPost(context: Context):SQLiteOpenHelper(context,"Postdetaildata",null,1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table Postdetaildata (postID TEXT primary key,userName TEXT,postData TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists Postdetaildata")
    }

    fun saveuserdata(postID:String,userName:String,postData:String): Boolean {

        if (postData ==null){
            return false
        }

        val p0 = this.writableDatabase
        val cv = ContentValues()

        cv.put("postID",postID)
        cv.put("userName",userName)
        cv.put("postData",postData)
        val result = p0.insert("Postdetaildata",null,cv)
        if (result==-1L){
            return false
        }
        return true
    }

    fun gettext(): Cursor? {
        val p0 = this.writableDatabase
        val cursor = p0.rawQuery("select * from Postdetaildata",null)
        return cursor
    }

    fun getNewestPostID(): Int {
        val db = readableDatabase
        val query = "SELECT MAX(postID) FROM Postdetaildata"
        val cursor = db.rawQuery(query, null)

        val newestPostID = if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            0
        }

        cursor.close()
        db.close()

        return newestPostID
    }

    fun clearTable() {
        val db = writableDatabase
        db.execSQL("DELETE FROM Postdetaildata")
        db.close()
    }
}