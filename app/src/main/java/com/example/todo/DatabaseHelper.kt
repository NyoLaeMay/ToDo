package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_Name, null, DATEBASE_VERSION){
    companion object{
        private const val DATABASE_Name = "student.db"
        private const val DATEBASE_VERSION = 1
        private const val TABLE_NAME = "todo"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DES = "description"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DES TEXT, $COLUMN_DATE TEXT, $COLUMN_TIME TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertDATA(todo: TodoList){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,todo.title)
            put(COLUMN_DES,todo.description)
            put(COLUMN_DATE,todo.date)
            put(COLUMN_TIME,todo.time)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()

    }

    fun getAllData() : List<TodoList>{
        val noteList = mutableListOf<TodoList>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)
        while(cursor.moveToNext()){
            var id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            var title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            var description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DES))
            var date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            var time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            val todo = TodoList(id,title,description,date,time)
            noteList.add(todo)
        }
        cursor.close()
        db.close()
        return noteList
    }


    fun deleteDate(noteID : Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteID.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()

    }

    fun getNoteByID(noteID: Int): TodoList{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        var id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        var title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        var description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DES))
        var date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
        var time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
        val note = TodoList(id,title,description,date,time)
        db.close()
        return note
    }

    fun updateData(todo: TodoList){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,todo.title)
            put(COLUMN_DES,todo.description)
            put(COLUMN_DATE,todo.date)
            put(COLUMN_TIME,todo.time)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(todo.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }
}