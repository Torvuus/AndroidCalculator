package com.example.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private  var calculator=Calculator()
    lateinit var expressionTxt:TextView
    lateinit var resultTxt:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //var calculator=
        expressionTxt=findViewById<TextView>(R.id.textViewExpression)
        resultTxt=findViewById<TextView>(R.id.textViewResult)
    }
    fun buttonClicked(view: View){
        var buttonId=findViewById<TextView>(view?.id)
        var buttonTxt=buttonId?.text.toString()


        when(buttonTxt){
            "CLR"->calculator.clear()
            "Delete"->calculator.delete()
            "="->{
                calculator.calculate()
                resultTxt.text=calculator.getResult()
            }
            else->calculator.insertAny(buttonTxt.single())
        }
        expressionTxt.text=calculator.getExpression()
    }
}
