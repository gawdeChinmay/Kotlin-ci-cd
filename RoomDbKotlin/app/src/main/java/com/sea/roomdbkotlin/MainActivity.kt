package com.sea.roomdbkotlin

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.sea.roomdbkotlin.databinding.ActivityMainBinding
import com.sea.roomdbkotlin.room_db.AppDataBase
import com.sea.roomdbkotlin.room_db.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



         db = Room.databaseBuilder(
            applicationContext,
            AppDataBase:: class.java, "database-name"
        ).build()

        val userDao = db.userDao()

        binding.saveData.setOnClickListener {
            val firstNameEntered = binding.firstName.text.toString()
            val lastNameEntered = binding.lastName.text.toString()
//
            lifecycleScope.launch(Dispatchers.IO) {
                userDao.insertAll(User(firstName = firstNameEntered, lastName = lastNameEntered))
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Data Saved", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.getData.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
            val users: List<User> = userDao.getAll()
            val userFirstnameList = mutableListOf<String>()

            users.forEach { item ->
                userFirstnameList.add(item.firstName.toString())
            }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_list_item_1, // Predefined layout for displaying a single item
                        userFirstnameList
                    )
                    binding.listView.adapter = adapter
                }
         }
        }


    }

    override fun onStop() {
        super.onStop()
        println("on stop called")
    }


    override fun onDestroy() {
        super.onDestroy()
        println("destroy called")
        Log.d("MainActivity", "onDestroy called")
        Log.d("MainActivity", "onDestroy called")
        Log.d("MainActivity", "onDestroy called")
        Log.d("MainActivity", "onDestroy called")
        Log.d("MainActivity", "onDestroy called")
        db.clearAllTables()
    }

}