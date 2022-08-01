package com.walter.sqliteexample

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.text.StringSearch

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                QUANTITY_COLUMN + " INTEGER," +
                PRICE_COLUMN + " INTEGER," +
                DESCRIPTION_COLUMN + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // This method is for adding data in our database
    fun createProduct(name : String, quantity : Int, price: Int, description:String ){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(NAME_COl, name)
        values.put(QUANTITY_COLUMN, quantity)
        values.put(PRICE_COLUMN, price)
        values.put(DESCRIPTION_COLUMN, description)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getProducts(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun updateProduct(id: Int, name: String, quantity: Int, price: Int, description: String){
      val contentValues = ContentValues()
      contentValues.put(NAME_COl, name)
      contentValues.put(QUANTITY_COLUMN, quantity)
      contentValues.put(PRICE_COLUMN, price)
      contentValues.put(DESCRIPTION_COLUMN, description)

      val db  = this.writableDatabase
      db.update(TABLE_NAME, contentValues, "$ID_COL=?", arrayOf(id.toString()))
      db.close()
    }

    fun deleteProduct(id : Int){
        val db = this.writableDatabase
        val  selectionArgs = arrayOf(id.toString())
        db.delete(TABLE_NAME, "$ID_COL = ?", selectionArgs)
        db.close()
    }

    fun getOneProduct(id : Int) : Cursor ?{
        val db = this.readableDatabase
        val selectionArgs = arrayOf(id.toString())
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $ID_COL=?", selectionArgs)
    }

    fun searchProducts(search: String):  Cursor?{
        val sql = "SELECT * FROM $TABLE_NAME WHERE $NAME_COl LIKE '%$search%'"
        val db = this.readableDatabase
        return  db.rawQuery(sql, null)
    }
    //CRUD

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "SHOP_DB"

        // below is the variable for database version
        private val DATABASE_VERSION = 2

        // below is the variable for table name
        val TABLE_NAME = "products"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val NAME_COl = "name"

        // below is the variable for age column
        val QUANTITY_COLUMN = "quantity"

        val PRICE_COLUMN = "price"

        val DESCRIPTION_COLUMN = "description"
    }
}