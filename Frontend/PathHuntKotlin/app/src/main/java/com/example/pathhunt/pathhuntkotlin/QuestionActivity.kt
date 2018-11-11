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
    var question : Question? = null
    var answer : String? = null
    var userAnswer : String? = null
    var QuestionId : Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        getInfo(QuestionId)
//        doAsync {
//            val result = URL("http://192.168.1.62:45455/api/questions/1").readText()
//            question = Klaxon()
//                .parse<Question>(result)
//            //Log.d("log1", result)
//            uiThread {
//                if (question != null) {
//                    answer = question?.answer.toString();
//                    txtQuestion.text = question?.content.toString()
//                }
//            }
//        }

        btnCheck.setOnClickListener {
            userAnswer = etxtAnswer.text.toString()
            if(userAnswer.equals(answer)){
                Toast.makeText(this, "Good answer", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show()
            }
        }

        btnPrev.setOnClickListener {
            if(QuestionId > 1){
                QuestionId--
            }
            getInfo(QuestionId)
        }

        btnNext.setOnClickListener {
            QuestionId++
            getInfo(QuestionId)
        }
    }

    fun getInfo(id: Int){
        doAsync {
            val result = URL("http://192.168.1.62:45455/api/questions/$id").readText()
            question = Klaxon()
                .parse<Question>(result)
            //Log.d("log1", result)
            uiThread {
                if (question != null) {
                    answer = question?.answer.toString();
                    txtQuestion.text = question?.content.toString()
                }
            }
        }
    }
}
