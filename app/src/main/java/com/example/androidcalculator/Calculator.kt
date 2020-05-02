package com.example.androidcalculator

class Calculator (){
    private val numbers= mutableListOf<Double>()
    private val signs= mutableListOf<Char>()
    private val hierarchy= listOf('/','*','+','-')
    private val signsTest= listOf('/','*','+','-','.')
    private val numbersTest= listOf('1','2','3','4','5','6','7','8','9','0')
    var previousSign = ' '
    var currentSign = ' '
    var number=""
    var isOneDot=false

    fun clear(){
        numbers.clear()
        signs.clear()
        previousSign=' '
        currentSign=' '
        number=""
    }

    fun insertNumber(num:Double)=numbers.add(num)
    fun insertSign(sign:Char)=signs.add(sign)
    fun insertAny(any:Char){
        previousSign=currentSign
        if (isFirstSign()){

        }
    }
    fun isFirstSign():Boolean{
        if(previousSign== ' ')
            return true
        return false
    }

    fun simpleOperation(sign:Char,a:Double,b:Double){
        when(sign){
            '+'->a+b
            '-'->a-b
            '*'->a*b
            '/'->if(b!=0.0) a/b else 0.0
        }
    }
    fun calculate(){

    }
    fun delete() {

    }
    fun getResult():String{


        return " "
    }
    fun getExpression():String{


        return " "
    }
}