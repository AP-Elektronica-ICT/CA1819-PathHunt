package com.example.pathhunt.pathhuntkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_question.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class QuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        doAsync {
            val result = URL("http://192.168.1.62:45455/api/questions/1").readText()
            val questions : Question? = Klaxon()
                .parse<Question>(result)

            //Log.d("log1", result)
            uiThread {
                if (questions != null) {
                    txtQuestion.text = questions?.content.toString()
                }
            }
        }


//        var question = txtQuestion.text.toString()
//        btnCheck.setOnClickListener {
//            var answer = etxtAnswer.text.toString()
//            if(answer.equals(question)){
//                Toast.makeText(this, "Good answer", Toast.LENGTH_LONG).show()
//            }
//            else{
//                Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}
