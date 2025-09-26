package com.hfad.catchat

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {
    private lateinit var timerText: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var timerStatus: TextView
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private val startTimeInMillis: Long = 25 * 60 * 1000 // 25 minutes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Set up the marquee text view
        val marqueeTextView = view.findViewById<TextView>(R.id.marqueeTextView)
        marqueeTextView.isSelected = true

        // Set up the YouTube WebView
        val youtubeWebView = view.findViewById<WebView>(R.id.youtubeWebView)
        val webSettings: WebSettings = youtubeWebView.settings
        webSettings.javaScriptEnabled = true
        youtubeWebView.webViewClient = WebViewClient()
        youtubeWebView.loadUrl("https://www.youtube.com/embed/pIsS9c0qpA0?autoplay=1&rel=0")

        // Set up the Pomodoro Timer
        timerText = view.findViewById(R.id.timerText)
        startButton = view.findViewById(R.id.startButton)
        resetButton = view.findViewById(R.id.resetButton)
        timerStatus = view.findViewById(R.id.timerStatus)

        startButton.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        resetButton.setOnClickListener {
            resetTimer()
        }

        updateCountDownText(startTimeInMillis)
        return view
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(startTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateCountDownText(millisUntilFinished)
            }

            override fun onFinish() {
                isTimerRunning = false
                updateButtons()
                timerStatus.text = "Time to take a break!"
                Toast.makeText(context, "Pomodoro session complete!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        isTimerRunning = true
        updateButtons()
        timerStatus.text = "Focus on your task"
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
        updateButtons()
        timerStatus.text = "Timer paused"
    }

    private fun resetTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
        updateCountDownText(startTimeInMillis)
        updateButtons()
        timerStatus.text = "Focus on your task"
    }

    private fun updateCountDownText(millisUntilFinished: Long) {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        timerText.text = timeFormatted
    }

    private fun updateButtons() {
        if (isTimerRunning) {
            startButton.text = "Pause"
            resetButton.visibility = View.INVISIBLE
        } else {
            startButton.text = "Start"
            resetButton.visibility = View.VISIBLE
        }
    }
}
