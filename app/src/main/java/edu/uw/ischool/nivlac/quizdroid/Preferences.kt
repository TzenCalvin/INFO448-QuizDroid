package edu.uw.ischool.nivlac.quizdroid

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class Preferences : AppCompatActivity() {
    val TAG = MainActivity::class.java.canonicalName
    lateinit var download : EditText
    lateinit var downloadInterval : EditText
    lateinit var enterButton : Button
    lateinit var backButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preferences)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        download = findViewById(R.id.URL)
        downloadInterval = findViewById(R.id.downloadInterval)
        enterButton = findViewById(R.id.enterButton)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        enterButton.setOnClickListener {
            fetch()
            Toast.makeText(this, "Updating questions from ${download.text}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetch() {
        val url = download.text.toString()
        Log.v(TAG, "Fetching $url")

        (application as QuizApp).executor.execute {
            val urlConnection = URL(url).openConnection() as HttpURLConnection
            val responseStream = ByteArrayOutputStream()
            try {
                // There's all sorts of better ways to read the response
                // from the HTTP request; this is just the simplest way.
                val incoming = BufferedInputStream(urlConnection.inputStream).bufferedReader()
                incoming.forEachLine {
                    responseStream.write(it.toByteArray())
                }
                // Create or open the file in internal storage
                val fileOutputStream: FileOutputStream = this.openFileOutput("questions.json", Context.MODE_PRIVATE)
                // Write the JSON string to the file
                fileOutputStream.write(responseStream.toString().toByteArray())
                fileOutputStream.close()
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(applicationContext, "Successful download!", Toast.LENGTH_SHORT).show()
                }
                Log.i(TAG, "JSON file saved successfully.")
            } catch (e: IOException) {
                e.printStackTrace()
                showError()
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(applicationContext, "Failed download... Try again.", Toast.LENGTH_SHORT).show()
                }
                Log.e(TAG, "I am failing.")
            } finally {
                Log.i(TAG, "I exited.")
                urlConnection.disconnect()
            }
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
}