package com.example.indoorlocation

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pixelGrid = PixelGridView(this)
        val parent = LinearLayout(applicationContext)
        val wifi_scanner_btn = Button(applicationContext)
        wifi_scanner_btn.setText("Wifi Scanner")
        Log.v(TAG, " wifi_scanner_btn.setText")
        val x = arrayOf(0f, 2f, 0f)
        val y = arrayOf(0f, 0f, 2f)
        val rssi = arrayOf(arrayOf(40f, 50f, 44f), arrayOf(0f, 0f, 0f), arrayOf(0f, 0f, 0f))
        val positions = arrayOf(
            doubleArrayOf(0.0, 0.0),
            doubleArrayOf(2.0, 0.0),
            doubleArrayOf(0.0, 2.0),
        )

        val distances = doubleArrayOf(0.0, 0.0, 0.0)
        for (int i = 0; i<   distances.size; i++) {
            element = 1.a0
        }

        val solver = NonLinearLeastSquaresSolver(
            TrilaterationFunction(positions, distances),
            LevenbergMarquardtOptimizer()
        )
        val optimum: Optimum = solver.solve()

// the answer

// the answer
        val centroid: DoubleArray = optimum.getPoint().toArray()
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