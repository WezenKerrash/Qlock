package com.wkerrash.qlock

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.wkerrash.qlock.databinding.ActivityMainBinding
import java.util.*
import kotlin.arrayOf as kotlinArrayOf


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var mainHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainHandler = Handler(Looper.getMainLooper())

        binding.invertColoursButton.setOnClickListener() { invertColours() }
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateNextTask)
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateNextTask)
    }

    private fun showActualTime() {
        val rightNow = Calendar.getInstance()
        val hourNow = rightNow.get(Calendar.HOUR_OF_DAY)
        val minuteNow = rightNow.get(Calendar.MINUTE)
        val secondNow = rightNow.get(Calendar.SECOND)

        // Add a leading zero for a better design
        if (secondNow < 10) {
            val secondNowCorrection = "0$secondNow"
            binding.seconds.text = secondNowCorrection
        } else {
            binding.seconds.text = secondNow.toString()
        }

        if (minuteNow < 10) {
            val minuteNowCorrection = "0$minuteNow"
            binding.minutes.text = minuteNowCorrection
        } else {
            binding.minutes.text = minuteNow.toString()
        }

        if (hourNow < 10) {
            val hourNowCorrection = "0$hourNow"
            binding.hours.text = hourNowCorrection
        } else {
            binding.hours.text = hourNow.toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showActualDate() {
        val dayName = kotlinArrayOf("Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado")
        //val monthName = kotlinArrayOf("de Enero de", "de Febrero de", "de Marzo de", "de Abril de", "de Mayo de", "de Junio de", "de Julio de", "de Agosto de", "de Septiembre de", "de Octubre de", "de Noviembre de", "de Diciembre de")

        val rightNow = Calendar.getInstance()
        val numberNow = rightNow.get(Calendar.DAY_OF_MONTH)
        val monthNow = rightNow.get(Calendar.MONTH) + 1
        val yearNow = rightNow.get(Calendar.YEAR)

        if (numberNow < 10) {
            val numberNowCorrection = "0$numberNow"
            binding.numberDayOfWeek.text = numberNowCorrection
        } else {
            binding.numberDayOfWeek.text = numberNow.toString()
        }

        if (monthNow < 10) {
            val monthNowCorrection = "0$monthNow"
            binding.month.text = "/$monthNowCorrection/"
        } else {
            binding.month.text = "/$monthNow/"
        }

        binding.year.text = yearNow.toString()

        // Use the number returned by rightNow function as index of the array dayName
        binding.dayOfWeek.text = dayName[rightNow.get(Calendar.DAY_OF_WEEK) - 1] + ","
    }

    private val updateNextTask = object : Runnable {
        override fun run() {
            showActualTime()
            showActualDate()
            mainHandler.postDelayed(this, 1000)

        }
    }

    private fun createHorizontalLayout() {
        binding.hours.textSize = 150F
        binding.minutes.textSize = 150F
        binding.seconds.textSize = 25F
        binding.center.textSize = 90F
        binding.dayOfWeek.textSize = 20F
        binding.numberDayOfWeek.textSize = 20F
        binding.month.textSize = 20F
        binding.year.textSize = 20F
    }

    private fun createVerticalLayout() {
        binding.hours.textSize = 90F
        binding.minutes.textSize = 90F
        binding.seconds.textSize = 15F
        binding.center.textSize = 70F
        binding.dayOfWeek.textSize = 15F
        binding.numberDayOfWeek.textSize = 15F
        binding.month.textSize = 15F
        binding.year.textSize = 15F
    }

    // Check if the device is rotated or not
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            createHorizontalLayout()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            createVerticalLayout()
        }
    }

    var x = 0  // Used to count how many times, the button to invert colours is pressed
    @SuppressLint("ResourceAsColor")
    private fun invertColours(){
        if (x % 2 == 0) {
            binding.generalLayout.setBackgroundResource(R.color.white)
            binding.seconds.setTextColor(Color.BLACK)
            binding.minutes.setTextColor(Color.BLACK)
            binding.hours.setTextColor(Color.BLACK)
            binding.center.setTextColor(Color.BLACK)
            binding.dayOfWeek.setTextColor(Color.BLACK)
            binding.numberDayOfWeek.setTextColor(Color.BLACK)
            binding.month.setTextColor(Color.BLACK)
            binding.year.setTextColor(Color.BLACK)
            x += 1
        } else {
            binding.generalLayout.setBackgroundResource(R.color.black)
            binding.seconds.setTextColor(Color.WHITE)
            binding.minutes.setTextColor(Color.WHITE)
            binding.hours.setTextColor(Color.WHITE)
            binding.center.setTextColor(Color.WHITE)
            binding.dayOfWeek.setTextColor(Color.WHITE)
            binding.numberDayOfWeek.setTextColor(Color.WHITE)
            binding.month.setTextColor(Color.WHITE)
            binding.year.setTextColor(Color.WHITE)
            x += 1
        }
    }
}