package com.example.timer

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var counter = 0
    var counter_min = 0
    var counter_sec = 0
    var counter_hr = 0

    var counter_secs: String = ""
    var counter_mins: String = ""
    var counter_hrs: String = ""

    var hourOfDay_r = 0
    var minute_r = 0

    var duration : Long = 0

    var check_start = true

    private lateinit var timer: CountDownTimer
    private lateinit var countTime : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Timer Project"

        countTime = findViewById(R.id.timelive)

        startTime.setOnClickListener {
            startTimer(duration)
//            timer.cancel()
        }

        stopTime.setOnClickListener {
            stopTimer()
        }

        timepicker_button.setOnClickListener {
            openTimePicker()
        }


    }

    private fun createTimer(duration : Long) {
        timer = object : CountDownTimer(duration, 1000) { //1000

            override fun onTick(millisUntilFinished: Long) {
                counter_sec = counter % 60
                counter_min = (counter / 60) % 60
                counter_hr = (counter / 60 / 60) % 24

                counter_secs = timeDigit(counter_sec)
                counter_mins = timeDigit(counter_min)
                counter_hrs = timeDigit(counter_hr)

                countTime.text = "${counter_hrs}:${counter_mins}:${counter_secs}"
                counter++
            }

            override fun onFinish() {
//                countTime.text = "Finished"
                counter = 0
                check_start = true
            }
        }
    }

    private fun startTimer(duration: Long) {
        if (check_start == true) {
            createTimer(duration)
            timer.start()
            check_start = false
        }
    }

    private fun stopTimer() {
        timer.cancel()
        counter = 0

        counter_sec = counter % 60
        counter_min = (counter / 60) % 60
        counter_hr = (counter / 60 / 60) % 24

        counter_secs = timeDigit(counter_sec)
        counter_mins = timeDigit(counter_min)
        counter_hrs = timeDigit(counter_hr)

//            countTime.text = counter.toString()
        countTime.text = "${counter_hrs}:${counter_mins}:${counter_secs}"
        check_start = true
    }

    private fun openTimePicker() {
        if (check_start == true) {
            val calendar = Calendar.getInstance()
            val timeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                hourOfDay_r = hourOfDay
                minute_r = minute
                duration = ((hourOfDay_r * 60 * 60 * 1000) + (minute_r * 60 * 1000)).toLong()

                val hrs_s = timeDigit(hourOfDay_r)
                val min_s = timeDigit(minute_r)

                time_textView.text = "$hrs_s : $min_s"
            }
            val timeDialog = TimePickerDialog(
                this, timeListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
            )
            timeDialog.show()
        }
    }


    private fun timeDigit(num: Int): String {
        var text = ""
        if (num < 10) {
            text = "0${num.toString()}"
        } else {
            text = num.toString()
        }

        return text

    }


}