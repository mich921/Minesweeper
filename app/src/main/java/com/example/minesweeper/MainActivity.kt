package com.example.minesweeper

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var level = "easy"
    var highScore = "-//-"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        easy.setOnClickListener {
            level="easy"
        }
        medium.setOnClickListener{
            level="medium"
        }
        hard.setOnClickListener{
            level="hard"
        }
        startButton.setOnClickListener{
            startGame(level)
        }

    }

    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("Alert!")
        builder.setMessage("Are you sure you want to exit the app?")
        builder.setCancelable(false)

        // Создание ImageView и установка изображения
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.stay)
        imageView.adjustViewBounds = true
        imageView.maxHeight = 250  // Установите максимальную высоту по желанию
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE

        // Добавление ImageView в AlertDialog
        builder.setView(imageView)

        builder.setPositiveButton("Yes"
        ){ dialog, which ->
            val exitAppIntent = Intent(Intent.ACTION_MAIN)
            exitAppIntent.addCategory(Intent.CATEGORY_HOME)
            exitAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(exitAppIntent)
            finish()
            super.onBackPressed()
        }

        builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
            }
        })

        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()

        val intent = intent
        if(intent.getStringExtra("lastTime") != null || intent.getStringExtra("highScore") != null ) {
            lastGameTime.text = " " + intent.getStringExtra("lastTime")
            bestTime.text = " " + intent.getStringExtra("highScore")
            highScore = bestTime.text as String
        }else{
            lastGameTime.text = " not played"
            bestTime.text = " not played"
        }
    }

    private fun startGame(level: String){

            val intent = Intent(this, BoardActivity::class.java).apply {
                putExtra("selectedLevel",level)
                putExtra("flag",1)
            }
            startActivity(intent)

    }

}
