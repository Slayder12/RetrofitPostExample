package com.example.retrofitpost

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitpost.utils.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    //https://jsonplaceholder.typicode.com/
    private lateinit var idTV: TextView
    private lateinit var userIdTV: TextView
    private lateinit var titleTV: TextView
    private lateinit var bodyTV: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        postRequest()

    }

    private fun initUI() {
        idTV = findViewById(R.id.idTV)
        userIdTV = findViewById(R.id.userIdTV)
        titleTV = findViewById(R.id.titleTV)
        bodyTV = findViewById(R.id.bodyTV)
        progressBar = findViewById(R.id.progressBar)
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun postRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                //Вариант 1
                //val user = User("new body", null,"new title", 15)
                //RetrofitInstance.api.createPost(user)

                //Вариант 2
                RetrofitInstance.api.createUrlPost(18,"new title","new body" )

            } catch (e: IOException) {
                Toast.makeText(applicationContext, "http error${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "app error${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "${response.code()}", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                    idTV.visibility = View.VISIBLE
                    userIdTV.visibility = View.VISIBLE
                    titleTV.visibility = View.VISIBLE
                    bodyTV.visibility = View.VISIBLE

                    val data = response.body()
                    idTV.text = data!!.id.toString()
                    userIdTV.text = data.userId.toString()
                    titleTV.text = data.title
                    bodyTV.text = data.body

                }
            }
        }
    }
}