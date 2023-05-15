package com.example.walkiewalkie

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
//import android.service.autofill.UserData
import android.util.Log

class Helper(context : Context): SQLiteOpenHelper(context, "Userdata", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Userdata (username TEXT primary key, phone TEXT, email TEXT, password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Userdata")
//        db?.execSQL("ALTER TABLE Userdata RENAME COLUMN password TO email")
//        db?.execSQL("ALTER TABLE Userdata RENAME COLUMN phone TO password")
//        db?.execSQL("ALTER TABLE Userdata RENAME COLUMN email TO phone")
        onCreate(db)
    }

    fun insertData(username: String, phone: String, email: String, password: String): Boolean {
        val db = this.writableDatabase
        Log.d("Helper", "Inserting data - username: $username, phone: $phone, email: $email, password: $password")
        val values = ContentValues().apply {
            put("username", username)
            put("password", phone) //Password should be stored in password column
            put("phone", email) //Phone should be stored in phone column
            put("email", password) //Email should be stored in email column
        }
        val result = db.insertOrThrow("Userdata", null, values)
        Log.i("chill", result.toString())
        db.close()
        return result != -1L
    }

    fun checkUserPass(username: String, password: String): Boolean{
        val p0 = this.writableDatabase
        val query = "select * from Userdata where username= '$username' and password= '$password'"
//        val query = "select * from Userdata where username= '$username' and password= '$password'"
        Log.i("Helper.kt", "cursor count $query")
        val cursor = p0.rawQuery(query, null)
        Log.i("Helper.kt", "cursor count ${cursor.count}")
        if(cursor.count<=0){
            cursor.close()
            return false
        }
        return true
    }

//    fun getUserData(userId: Long): UserData? {
//        val db = this.readableDatabase
//        val projection = arrayOf("name", "phone", "email", "password")
//        val selection = "id = ?"
//        val selectionArgs = arrayOf(userId.toString())
//        val cursor = db.query("Userdata", projection, selection, selectionArgs, null, null, null)
//        var userData: UserData? = null
//        if (cursor.moveToFirst()) {
//            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
//            val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
//            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
//            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
//            userData = UserData(name, phone, email, password)
//        }
//        cursor.close()
//        db.close()
//        return userData
//    }
//    data class UserData(val name: String, val phone: String, val email: String, val password: String)
}