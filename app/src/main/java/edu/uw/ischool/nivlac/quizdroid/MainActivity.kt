package edu.uw.ischool.nivlac.quizdroid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.canonicalName
    private var topics = QuizApp()
    private var subjects = topics.topicsList
    var finishedInitial = false

    lateinit var lstSubjects : ListView
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (isAirplaneModeOn(this)) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Airplane mode on!")
            builder.setMessage("This app will not work without internet. Want to turn off airplane mode?")
            builder.setPositiveButton("Sure!") { _, _ ->
                val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
                startActivity(intent)
            }
            builder.setNegativeButton("Nah, get me out.") { dialog, _ ->
                dialog.dismiss()
                finish()
            }

            val dialog = builder.create()

            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
        } else if (!isNetworkAvailable(this)) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No internet!")
            builder.setMessage("This app will not work without internet. Try again later!")
            builder.setNegativeButton("Bye") { dialog, _ ->
                dialog.dismiss()
                finish()
            }

            val dialog = builder.create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
        }

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        //check for first start up
        if (sharedPreferences.getBoolean("isFirstLaunch", true)) {
            Toast.makeText(this, "Getting questions from http://tednewardsandbox.site44.com/questions.json", Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {
                val isDownloaded = initialRead("http://tednewardsandbox.site44.com/questions.json", "questions.json")

                if (isDownloaded) {
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isFirstLaunch", false)
                    editor.apply()
                    Toast.makeText(applicationContext, "Successful download!", Toast.LENGTH_SHORT).show()
                    setUp()
                } else {
                    Toast.makeText(applicationContext, "Failed download... Try again.", Toast.LENGTH_SHORT).show()
                    showError()
                    Log.e("MainActivity", "Download failed")
                }
            }
        } else {
            setUp()
        }
    }

    fun showError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Uh oh!")
        builder.setMessage("Something went wrong! Would you like to retry the download?")

        builder.setPositiveButton("Retry") { dialog, _ ->
            dialog.dismiss()
            recreate()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
    }

    suspend fun initialRead(initialUrl : String, fileName: String): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val url = URL(initialUrl)
                val urlConnection = url.openConnection() as HttpURLConnection
                val inputStream = BufferedInputStream(urlConnection.inputStream)

                val fileOutputStream: FileOutputStream = openFileOutput(fileName, MODE_PRIVATE)
                inputStream.copyTo(fileOutputStream)

                fileOutputStream.close()
                inputStream.close()
                urlConnection.disconnect()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun setUp() {
        finishedInitial = true
        topics = QuizApp()
        subjects = topics.topicsList

        lstSubjects = findViewById(R.id.subjectList)
        val adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, subjects.map {
                it.title + " - " + it.desc
            })
        lstSubjects.adapter = adapter

        lstSubjects.setOnItemClickListener { parent, view, position, id ->
            val selected = subjects[position]
            val intent = Intent(this, Overview::class.java)
            intent.putExtra("topic", selected)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        invalidateOptionsMenu()
    }

    fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i(TAG, "Menu has been set!")
        menuInflater.inflate(R.menu.preferences, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.openPreferences -> {
                val intent = Intent(this, Preferences::class.java)
                startActivity(intent)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}