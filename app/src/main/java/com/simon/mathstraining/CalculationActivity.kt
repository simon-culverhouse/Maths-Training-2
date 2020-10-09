package com.simon.mathstraining

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculation.*
import java.util.logging.Logger.global
import kotlin.concurrent.thread

class CalculationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation)
        val answer : IntArray = intArrayOf(0, 0)
        var count = 0
        var answersum = 0
        var answerinput = 50
        var answer1 = 0
        var answer2 = 0
        var x = 1
        var attempts = 0

        // Custom Toast Function
        fun Toast.createToast(context: Context, message:String, gravity:Int, duration:Int){
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            /*first parameter is the layout you made
            second parameter is the root view in that xml
             */
            val layout = inflater.inflate(R.layout.answer_toast, (context as Activity).findViewById<ViewGroup>(R.id.custom_toast_container))

            layout.findViewById<TextView>(R.id.text).text = message
            setGravity(gravity, 0, -150)
            setDuration(Toast.LENGTH_SHORT);
            setView(layout);
            show()
        }
        // Choose random numbers and apply to buttons
        fun chooseRand(string: String) {
            x=1
            while (x>0) {
                var randomChoice = (1..10).random()
                var randomChoice2 = (1..10).random()
                x = if (randomChoice <= randomChoice2){
                    1
                } else 0


                when (string) {
                    "X" -> {
                        answersum = (randomChoice * randomChoice2)
                    }
                    "-" -> {
                        answersum = (randomChoice - randomChoice2)
                    }
                    "+" -> {
                        answersum = (randomChoice + randomChoice2)
                    }
                    "/" -> {
                        randomChoice *= randomChoice2
                        answersum = (randomChoice / randomChoice2)
                    }
                }
                answer1 = randomChoice
                answer2 = randomChoice2
                button_first.text = randomChoice.toString()
                button_second.text = randomChoice2.toString()

            }
            x=1
       }

        // Output from numberpad
        fun outputAnswer(){
            when {
                (answer[1] >= 0) and (answer[0] > 0) -> {
                    button_answer.text = ( answer[0].toString()  + answer[1].toString())
                }
                (answer[0] < 1) and (answer[1] >= 0) -> {
                    button_answer.text = answer[1].toString()
                }
                answer[1] < 0 -> {
                    button_answer.text = null
                }
            }
            answerinput = ((answer[0] * 10) + answer[1])
            println(answerinput)
        }
        // Input from numberpad
        fun inputAnswer(int: Int) {
            when {
                count > 1 -> {
                    return
                }
                // Input number
                count == 0 -> {
                    answer[1] = int
                }
            }
            if (count == 1) {
                answer[0] = answer[1]
                answer[1] = int
            }

            outputAnswer()
            count += 1
        }
        // Delete button action
        fun deleteInput(){
            button_answer.text = null
            count = 0
            answer[0] = 0
            answer[1] = 0
            outputAnswer()
            if (count > 0 ){count -= 1}
        }
        // Check answer and toast
        fun checkAnswer(message: String) {

                if (answersum == answerinput) {

                    val toast: Toast = Toast(this)
                    toast.createToast(this, "Correct Answer\n $answer1 $message $answer2  = $answerinput", Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT)


                    } else {
                    val toast: Toast = Toast(this)
                    toast.createToast(this, "Wrong Answer\n $answer1 $message $answer2  = $answersum", Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT)
                }
                attempts ++
                if (attempts > 5){
                        this.finish()
                    }
            deleteInput()
            chooseRand(message)
        }

        // Set up screen
        fun multiply(string: String) {
            val textView = findViewById<TextView>(R.id.textView).apply {
                text = "Multiplication"
            }
            button_function.text = string
            outputAnswer()
            button_answer.text = null
            chooseRand(string)
        }

        fun subtract(string: String) {
            findViewById<TextView>(R.id.textView).apply {
              text = "Subtraction"
            }
            button_function.text = string
            outputAnswer()
            button_answer.text = null
            chooseRand(string)
            println("this many$attempts")
        }
        fun addition(string: String) {
            val textView = findViewById<TextView>(R.id.textView).apply {
                text = "Addition"
            }
            button_function.text = string
            outputAnswer()
            button_answer.text = null
            chooseRand(string)
        }
        fun division(string: String) {
            val textView = findViewById<TextView>(R.id.textView).apply {
                text = "Division"
            }
            button_function.text = string
            outputAnswer()
            button_answer.text = null
            chooseRand(string)
        }

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        when (message) {

            "X" -> multiply(message)

            "-" -> subtract(message)

            "+" -> addition(message)

            "/" -> division(message)
        }


        // Listeners for numberpad, next and enter keys
        button_num0.setOnClickListener {
            inputAnswer(0)
        }
        button_num1.setOnClickListener {
            inputAnswer(1)
        }
        button_num2.setOnClickListener {
            inputAnswer(2)
        }
        button_num3.setOnClickListener {
            inputAnswer(3)
        }
        button_num4.setOnClickListener {
            inputAnswer(4)
        }
        button_num5.setOnClickListener {
            inputAnswer(5)
        }
        button_num6.setOnClickListener {
            inputAnswer(6)
        }
        button_num7.setOnClickListener {
            inputAnswer(7)
        }
        button_num8.setOnClickListener {
            inputAnswer(8)
        }
        button_num9.setOnClickListener {
            inputAnswer(9)
        }
        button_del.setOnClickListener {
            deleteInput()
        }
        button_enter.setOnClickListener {
            checkAnswer(message)
        }
    }
}
