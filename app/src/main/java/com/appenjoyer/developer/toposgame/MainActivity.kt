package com.appenjoyer.developer.toposgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDelegate
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var topoImages = ArrayList<ImageView>()
    var score : Int = 0
    var handler : Handler = Handler()
    var runnable : Runnable = Runnable {  }
    var gameStarted : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpTopoImages()
        btnStart.setOnClickListener ({ _ -> startGame() })




    }


    //show and hide topos
    fun showAndHideTopos(){
        runnable = object : Runnable{
            override fun run() {
                hideTopos()
                val random = Random()
                val index = random.nextInt(9-0)
                topoImages[index].visibility= VISIBLE
                handler.postDelayed(runnable, 500)
            }

        }
        handler.post(runnable)

    }
    fun setUpTopoImages(){
        //other way could be
        topoImages = arrayListOf(topo1,topo2,topo3,topo4,topo5,topo6,topo7,topo8,topo9)
        /*var topo = ImageView(this)
        for (i in 1..9) {
            topo.setImageResource(this.resources.getIdentifier("topo${i}","drawable",this.packageName))
            topoImages.add(topo)
        }*/
    }

    fun setUpCountDownTimer(){
        object : CountDownTimer(10000,1000){
            override fun onFinish() {
                tvTimeText.text="Time's over"
                gameStarted = false
                handler.removeCallbacks(runnable)
                btnStart.isEnabled = true
                hideTopos()
                showAlertGameFinished()
            }

            override fun onTick(p0: Long) {
                tvTimeText.text= "Time: ${p0 / 1000}"

            }

        }.start()
    }

    fun showAlertGameFinished(){
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Game finished!")
        alert.setCancelable(false)
        alert.setMessage("Congratulations you made ${score} points! \nDo you want to start again?")
        alert.setPositiveButton("Yes"){
            dialogInterface, _ ->  startGame()
        }
        alert.setNegativeButton("No"){
            dialogInterface, _ ->  Toast.makeText(applicationContext,"Bye bye",Toast.LENGTH_SHORT).show()
            finish()
        }
        alert.show()

    }

    fun hideTopos(){
        for(topo in topoImages){
            topo.visibility = INVISIBLE
        }
    }
    fun increaseScore(view: View){
        if(!gameStarted) return
        score++
        tvScoreText.text="Score: ${score}"
    }


    fun startGame(){
        gameStarted = true
        btnStart.isEnabled = false
        score = 0
        showAndHideTopos()
        setUpCountDownTimer()
    }


}
