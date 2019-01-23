package com.example.pathhunt.pathhuntkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.RadioButton
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
import kotlinx.android.synthetic.main.activity_question.view.*
import kotlinx.android.synthetic.main.row_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Thread.sleep
import java.net.URL
import java.util.*

class QuestionActivity : AppCompatActivity() {
    var allquestions: MutableList<Question> = mutableListOf()
    var answer: String? = ""
    var userAnswer: String? = ""
    var questionLocation: String? = ""
    var questionId: Int = 0
    var totalScore: Int = 0
    var scoreToGain: Int = 60
    var time: Int = 0
    var zeroScore : Boolean = false
    var rightAnswer : Boolean = false
    //Timer, mensen krijgen 60 seconden om vraag te beantwoorden
    //om de 5 seconden gaat er 5 score, van de totale score die ze kunnen verdienen, af
    var count: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onFinish() {
            Toast.makeText(this@QuestionActivity, "Time's up!", Toast.LENGTH_LONG).show()
            questionAnswered()
        }

        override fun onTick(p0: Long) {
            time = (p0 / 1000).toInt()
            txtTimeRemaining.text = time.toString()
            if(scoreToGain == 0){
                zeroScore = true
            }
            if ((time % 5) == 0 && zeroScore == false) {
                scoreToGain -= 5
            }
        }
    }
//To make sure they can't press back button
    override fun onBackPressed() {

    }

    //val Question1 = Question(answer = "Test123", content = "Wat is het antwoord", id = null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        questionLocation = prefs.nextLocation
        totalScore = prefs.teamScore
        rdbAnswer1.visibility = View.INVISIBLE
        rdbAnswer2.visibility = View.INVISIBLE
        rdbAnswer3.visibility = View.INVISIBLE
        btnCheck.visibility = View.INVISIBLE
        getInfo(questionLocation)
        setScore(totalScore)
        txtTimeRemaining.text = "60"

        btnCheck.setOnClickListener {
            if (rdgAnswers.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_LONG).show()
            } else {
                val userRadio: RadioButton = findViewById(rdgAnswers.checkedRadioButtonId)
                userAnswer = userRadio.text.toString()
            }
            if (userAnswer.equals(answer)) {
                rightAnswer = true
                totalScore += scoreToGain
            }
            else{
                rightAnswer = false
            }
            questionAnswered()
        }
    }
 /*   private fun nextQuestion(){
        if (questionId < allquestions.size - 1) {
            questionId++
        }
        txtQuestion.text = allquestions[questionId].content
        changeAnswers()
        answer = allquestions[questionId].answer
    }*/
    private fun setScore(newScore: Int) {
        txtScoreQuestion.text = "Score: " + newScore.toString()
    }

    private fun resetScore(){
        scoreToGain = 60
    }

    private fun questionAnswered(){
        count.cancel()
        setScore(totalScore)
        resetScore()
        prefs.numberOfQuestions++
        if(prefs.numberOfQuestions == 2){
            val intent1 = Intent (this, EndActivity::class.java)
            startActivity(intent1)
        }
        prefs.teamScore = totalScore
        prefs.nextLocationId ++
        getNextDestination(prefs.nextLocationId)
        Log.d("Geo", prefs.nextLocationId.toString())
        sleep(1000)
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
        //nextQuestion()
        //count.start()
    }

    private fun changeAnswers() {
        if (rdgAnswers.checkedRadioButtonId != -1) {
            rdgAnswers.clearCheck()
        }
        rdbAnswer1.text = allquestions[questionId].options[0]
        rdbAnswer2.text = allquestions[questionId].options[1]
        rdbAnswer3.text = allquestions[questionId].options[2]
    }

//code snippet: https://stackoverflow.com/questions/45685026/how-can-i-get-a-random-number-in-kotlin
    private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

    private fun getNextDestination(id: Int){
        var url = Api().urlLocations+"/" + id.toString()
        url.httpGet().responseObject(Locatie.SingleDeserializer()){request, response, result ->
            when (result){
                is Result.Success ->{
                    val (locations, err) = result
                    prefs.nextStreet = locations?.street
                    prefs.nextLocation = locations?.name
                    if(rightAnswer == false ){
                        prefs.nextExtraStreet = locations?.extraStreet
                    }

                    else{
                        prefs.nextExtraStreet = ""
                    }
                    //Log.d("nextstreet", prefs.nextStreet)
                }

                is Result.Failure -> {
                    prefs.nextStreet = "No street found"
                    prefs.nextLocation = "No next location"
                }
            }
        }
    }

    private fun getInfo(name: String?) {
         var url = Api().urlQuestions +"?location=" + name
        Log.d("url", url)
        url.httpGet().responseObject(Question.Deserializer()) { request, response, result ->
            when (result) {
                is Result.Success -> {
                    rdbAnswer1.visibility = View.VISIBLE
                    rdbAnswer2.visibility = View.VISIBLE
                    rdbAnswer3.visibility = View.VISIBLE
                    btnCheck.visibility = View.VISIBLE
                    val (questions, err) = result
                    questions?.forEach { question ->
                        //Log.d("Questions", "Content ${question.content}, antwoord is ${question.answer}")
                        allquestions?.add(question)
                    }
                    //Log.d("All Questions", allquestions.toString())
                    questionId = (0 until allquestions.size).random()
                    txtQuestion.text = allquestions[questionId].content
                    changeAnswers()
                    answer = allquestions[questionId].answer
                    count.start()
                }

                is Result.Failure -> {
                    txtQuestion.text = "Couldn't find question"
                }
            }
        }
        //httpPost voor question
//            Log.d("Question1", Gson().toJson(Question1))
//            url.httpPost()
//                .jsonBody(Gson().toJson(Question1))
//                .response {_, _, result ->
//                    Log.d("result", result.toString())
//                }

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
