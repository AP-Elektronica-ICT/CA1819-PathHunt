package com.example.pathhunt.pathhuntkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
//import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class QuestionActivity : AppCompatActivity() {
    var allquestions: MutableList<Question> = mutableListOf()
    var answer: String? = null
    var userAnswer: String? = null
    var QuestionId: Int = 0
    val url = "http://192.168.137.1:45455/api/questions"
    val Question1 = Question(answer = "Test123", content = "Wat is het antwoord", id = null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        try
        {
            getInfo(QuestionId)
        }
        catch(e : Exception)
        {
            Toast.makeText(this, "Can't connect to the server", Toast.LENGTH_LONG).show()
        }

        btnCheck.setOnClickListener {
            userAnswer = etxtAnswer.text.toString()
            if (userAnswer.equals(answer)) {
                Toast.makeText(this, "Good answer", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show()
            }
        }

        btnPrev.setOnClickListener {
            if (QuestionId >= 1) {
                QuestionId--
            }
            //getInfo(QuestionId)
            txtQuestion.text = allquestions[QuestionId].content
            answer = allquestions[QuestionId].answer
        }

        btnNext.setOnClickListener {
            //getInfo(QuestionId)
            if (QuestionId < allquestions.size - 1) {
                QuestionId++
            }
            txtQuestion.text = allquestions[QuestionId].content
            answer = allquestions[QuestionId].answer
        }
    }

    fun getInfo(id: Int) {
        //var data: Question? = null
        url.httpGet().responseObject(Question.Deserializer()) { request, response, result ->
            val (questions, err) = result
            questions?.forEach { question ->
                Log.d("Questions", "Content ${question.content}, antwoord is ${question.answer}")
                allquestions?.add(question)
            }
            Log.d("All Questions", allquestions.toString())
            txtQuestion.text = allquestions[QuestionId].content
            answer = allquestions[QuestionId].answer
        }
        //httpPost voor question
//            Log.d("Question1", Gson().toJson(Question1))
//            url.httpPost()
//                .jsonBody(Gson().toJson(Question1))
//                .response {_, _, result ->
//                    Log.d("result", result.toString())
//                }


//        "http://192.168.137.1:45455/api/questions/1".httpGet().responseString(){request, response, result ->
//            when(result){
//                is Result.Success ->{
//                    data = Klaxon().parse<Question>(result.get())
//                    Log.d("LogResult", result.get())
//                    answer = data?.answer.toString()
//                    txtQuestion.text = data?.content.toString();
//                }
//
//                is Result.Failure ->{
//                    txtQuestion.text = "Couldn't find question"
//                }
//            }

        //       }
//        doAsync {
//            val result = URL("http://192.168.137.1:45455/api/questions/$id").readText()
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
    }
}
