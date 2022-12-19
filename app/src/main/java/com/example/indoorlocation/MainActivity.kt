package com.example.indoorlocation

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver
import com.lemmingapex.trilateration.TrilaterationFunction
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer
import java.lang.Math.log
import java.lang.Math.pow
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var pixelGrid = PixelGridView(this)
        var parent = LinearLayout(applicationContext)
        var wifi_scanner_btn = Button(applicationContext)
        wifi_scanner_btn.setText("Wifi Scanner")

        var rssi = arrayOf(arrayOf(57.0, 50.0, 44.0), arrayOf(0, 0, 0), arrayOf(0, 0, 0))
        var positions = arrayOf(
            doubleArrayOf(0.0, 0.0),
            doubleArrayOf(2.0, 0.0),
            doubleArrayOf(0.0, 2.0),
        )


        var distances = (rssi.get(0).map {
           5 * pow(10.0, (it as Double - 21.0 - 20.0 * log(10.0)) / 35.0)
        }).toDoubleArray()

        var solver = NonLinearLeastSquaresSolver(
            TrilaterationFunction(positions, distances),
            LevenbergMarquardtOptimizer()
        )
        var optimum = solver.solve()

// the answer

// the answer
        var centroid = optimum.point.toArray()





        wifi_scanner_btn.setOnClickListener {
            // Do something in response to button click
            val intent = Intent(this, WifiScanner::class.java)
            startActivity(intent)
            Log.v(TAG, "onClick")
        }

        parent.setOrientation(LinearLayout.VERTICAL)
        parent.addView(wifi_scanner_btn)
        parent.addView(pixelGrid)

        pixelGrid.setNumColumns(20)
        pixelGrid.setNumRows(20)
        setContentView(parent)
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Log.e("NIlu_TAG","Hello World")
                pixelGrid.changeCell(3, 4)
            }
        },2000,2000)
    }

}