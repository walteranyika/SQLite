package com.walter.sqliteexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputName : EditText = findViewById(R.id.inputName)
        val inputQuantity : EditText = findViewById(R.id.inputQuantity)
        val inputPrice : EditText = findViewById(R.id.inputPrice)
        val inputDesc: EditText = findViewById(R.id.inputDesc)

        val buttonSave :Button = findViewById(R.id.buttonSave)
        val buttonFetchAll :Button = findViewById(R.id.buttonFetchAll)
        val buttonFetchOne :Button = findViewById(R.id.buttonFetchOne)
        val buttonDelete :Button = findViewById(R.id.buttonDelete)
        val buttonSearch :Button = findViewById(R.id.buttonSearch)
        val buttonUpdate :Button = findViewById(R.id.buttonUpdate)

        val db = DBHelper(this, null)

        buttonSave.setOnClickListener {
           val name = inputName.text.toString().trim()
           val quantity = inputQuantity.text.toString().trim().toIntOrNull()
           val price = inputPrice.text.toString().trim() .toIntOrNull()
           val description = inputDesc.text.toString().trim()

           if (name.isNotEmpty() && quantity != null && price != null  && description.isNotEmpty()){
               db.createProduct(name, quantity, price, description)
               Toast.makeText(this, "Product $name saved", Toast.LENGTH_SHORT).show()
               inputName.text.clear()
               inputQuantity.text.clear()
               inputPrice.text.clear()
               inputDesc.text.clear()
           }else{
               Toast.makeText(this, "Fill in all the fields with valid values", Toast.LENGTH_SHORT).show()
           }
        }

        buttonFetchAll.setOnClickListener {
            val cursor = db.getProducts()
            cursor!!.moveToFirst()
            while (cursor.moveToNext()){
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val quantity = cursor.getInt(2)
                val price = cursor.getInt(3)
                val desc = cursor.getString(4)

                Log.d("PRODUCTS", "onCreate: $id : $name : $quantity : $price: $desc")
            }
        }

        buttonFetchOne.setOnClickListener {
            val cursor = db.getOneProduct(2)
            cursor!!.moveToFirst()
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val quantity = cursor.getInt(2)
            val price = cursor.getInt(3)
            val desc = cursor.getString(4)

            Log.d("PRODUCTS_ONE", "onCreate: $id : $name : $quantity : $price: $desc")
        }

        buttonDelete.setOnClickListener {
            db.deleteProduct(2)
        }

        buttonSearch.setOnClickListener {
            val cursor = db.searchProducts("ung")
            cursor!!.moveToFirst()
            while (cursor.moveToNext()){
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val quantity = cursor.getInt(2)
                val price = cursor.getInt(3)
                val desc = cursor.getString(4)

                Log.d("PRODUCTS_SEARCH", "onCreate: $id : $name : $quantity : $price: $desc")
            }
        }

        buttonUpdate.setOnClickListener {
            db.updateProduct(2, "New Updated Product", 300, 200, "Just been updated")
        }
    }
}