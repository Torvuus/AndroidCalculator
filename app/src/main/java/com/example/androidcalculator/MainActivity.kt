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
    var afterResult=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expressionTxt=findViewById<TextView>(R.id.textViewExpression)
        resultTxt=findViewById<TextView>(R.id.textViewResult)
    }
    fun buttonClicked(view: View){
        var buttonId=findViewById<TextView>(view.id)
        var buttonTxt=buttonId?.text.toString()


        when(buttonTxt){
            "CLR"->{
                clr()
            }
            "Delete"->{
                if(afterResult)
                    clr()
                calculator.delete()
            }
            "="->{
                if(!afterResult) {
                    expressionTxt.text = calculator.getExpression()
                    resultTxt.text = calculator.getResult()
                    afterResult = true
                }
            }
            else->{
                if(afterResult)
                    clr()
                calculator.insertAny(buttonTxt.single())
                afterResult=false
            }
        }
        if(!afterResult)
            expressionTxt.text=calculator.getExpression()


         //expressionTxt.text=calculator.numbers.toString()
        //expressionTxt.text=calculator.number
        //resultTxt.text=calculator.operators.toString()
    }
    fun clr(){
        calculator.clear()
        resultTxt.text="="
        expressionTxt.text=""
        afterResult=false
    }
}
