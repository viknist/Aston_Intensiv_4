package ru.viknist.aston_dz_4

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val clock = findViewById<ClockCustomView>(R.id.analogClockView).apply {
            hourHandColor = getColor(R.color.red)
            minuteHandColor = getColor(R.color.pink)
            secondHandColor = getColor(R.color.green)

            hourHandWidth = 15f
            minuteHandWidth = 10f
            secondHandWidth = 5f
        }
    }
}