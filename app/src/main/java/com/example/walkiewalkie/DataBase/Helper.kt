package com.example.walkiewalkie.DataBase

//import android.service.autofill.UserData
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.preference.PreferenceManager
import android.util.Log

class Helper(context : Context): SQLiteOpenHelper(context, "Userdata", null, 1) {
    private val appContext: Context = context.applicationContext
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Userdata (username TEXT primary key, phone TEXT, email TEXT, password TEXT, weight TEXT, height TEXT, bmi REAL)")
        db?.execSQL("create table Postdetaildata (postID TEXT primary key,userName TEXT,postData TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Userdata")
        db?.execSQL("drop table if exists Postdetaildata")
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
        val sharedPreferences = appContext.getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()

        return true
    }



    fun saveBMI(weight: String, height: String, bmi: Float) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("weight", weight)
        values.put("height", height)
        values.put("bmi", bmi)

        db.insert("Userdata", null, values)
        db.close()
    }

    fun getLatestBMIForUser(username: String): Float {
        val db = readableDatabase

        val cursor: Cursor? = db.query(
            "Userdata",
            arrayOf("bmi"),
            "username = ?",
            arrayOf(username),
            null,
            null,
            "username DESC",
            "1"
        )
        var latestBMI: Float = 0f

        if (cursor != null && cursor.moveToFirst()) {
            val bmiIndex = cursor.getColumnIndex("bmi")
            if (!cursor.isNull(bmiIndex)) {
                latestBMI = cursor.getFloat(bmiIndex)
            }
            cursor.close()
        }

        db.close()
        return latestBMI
    }

    fun updateBMI(username: String,weight: String, height: String, newBMI: Float) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("weight", weight)
        values.put("height", height)
        values.put("bmi", newBMI)

        val whereClause = "username = ?"
        val whereArgs = arrayOf(username)

        db.update("Userdata", values, whereClause, whereArgs)
        db.close()
    }

    fun getUserDetails(username: String): UserDetails? {
        val db = readableDatabase
        val projection = arrayOf("username", "phone", "email", "password", "weight", "height", "bmi")
        val selection = "username = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query("Userdata", projection, selection, selectionArgs, null, null, null)
        var userDetails: UserDetails? = null

        if (cursor != null && cursor.moveToFirst()) {
            val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            val weight = cursor.getString(cursor.getColumnIndexOrThrow("weight"))
            val height = cursor.getString(cursor.getColumnIndexOrThrow("height"))
            val bmi = cursor.getFloat(cursor.getColumnIndexOrThrow("bmi"))

            userDetails = UserDetails(username, phone, email, password, weight, height, bmi)
        }

        cursor?.close()
        db.close()

        return userDetails
    }

    //postDataBase

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

    fun updatePostData(postID: String, newPostData: String): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put("postData", newPostData)

        val whereClause = "postID = ?"
        val whereArgs = arrayOf(postID)

        val rowsAffected = db.update("Postdetaildata", values, whereClause, whereArgs)
        db.close()

        return rowsAffected > 0
    }

    fun deletePostData(postID: String): Boolean {
        val db = writableDatabase

        val whereClause = "postID = ?"
        val whereArgs = arrayOf(postID)

        val rowsAffected = db.delete("Postdetaildata", whereClause, whereArgs)
        db.close()

        return rowsAffected > 0
    }

    data class UserDetails(
        val username: String,
        val phone: String,
        val email: String,
        val password: String,
        val weight: String?,
        val height: String?,
        val bmi: Float?
    )

    fun updateUserProfile (username: String, updatedName: String, updatedPhone: String, updatedEmail: String, updatedPassword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("username", updatedName)
        values.put("phone",updatedPhone)
        values.put("email", updatedEmail)
        values.put("password",updatedPassword)

        val whereClause = "username = ?"
        val whereArgs = arrayOf(username)

        val rowAffected = db.update("Userdata", values, whereClause, whereArgs)
        db.close()

        return rowAffected != 0

    }


}