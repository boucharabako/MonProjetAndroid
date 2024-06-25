package com.bouchara.projet

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Userdata.db", null, 3) {
    /**companion object {
    const val DATABASE_NAME = "Userdata.db"
    const val DATABASE_VERSION = 1
    }**/

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table contacts (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, contact TEXT, created_at DATETIME DEFAULT CURRENT_TIMESTAMP)")
        db?.execSQL(
            "CREATE TABLE messages (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "contact_phone TEXT," +
                    "contact_name TEXT," +
                    "message TEXT, created_at DATETIME DEFAULT CURRENT_TIMESTAMP)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists contacts")
        db?.execSQL("DROP TABLE IF EXISTS messages")
        onCreate(db)
    }

    fun saveuserdata (name: String, contact: String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("contact", contact)
        val result = db.insert("contacts", null, cv)
        return result != -1L
    }
    fun updateuserdata (name:String, contact: String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("contact", contact)
        val cursor: Cursor = db.rawQuery("select * from contacts where name = ?", arrayOf(name))
        if(cursor.count>0){
            val result = db.update("contacts", cv, "name=?", arrayOf(name))
            return result != -1
        }else{
            return false
        }
    }

    fun deleteuserdata (name:String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        val cursor: Cursor = db.rawQuery("select * from contacts where name = ?", arrayOf(name))
        if(cursor.count>0){
            val result = db.delete("contacts","name=?", arrayOf(name))
            return result != -1
        }else{
            return false
        }
    }

    fun deleteuserMessagedata (message:String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        val cursor: Cursor = db.rawQuery("select * from messages where message = ?", arrayOf(message))
        if(cursor.count>0){
            val result = db.delete("messages","message=?", arrayOf(message))
            return result != -1
        }else{
            return false
        }
    }

    fun gettext(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("select * from contacts ORDER BY created_at DESC", null)
    }

    fun gettextMessage(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("select * from messages ORDER BY created_at DESC", null)
    }
}