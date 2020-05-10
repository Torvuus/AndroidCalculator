package com.example.androidcalculator

class Calculator (){
    //Lists that keeps numbers and operators that will be calculated
    var numbers= mutableListOf<String>()
    var operators= mutableListOf<Char>()

    //Lists that keeps appropiate signs that helps us
    // find out what type of sign has been inserted by user
    private val hierarchy= listOf('/','*','+','-')
    private val numbersWithZeroTest= listOf('1','2','3','4','5','6','7','8','9','0')
    private val numbersTest= listOf('1','2','3','4','5','6','7','8','9')

    //Variables used with adding, to display expression, to receive result
    var expressionTxt=""
    var lastAdded="none"
    var outcome=""
    var calculatedProperly=true

    fun clear(){
        numbers.clear()
        operators.clear()
        lastAdded="none"
        validation()
        getResult()
    }

    fun insertNumber(num:String)=numbers.add(num)
    fun insertSign(sign:Char)=operators.add(sign)

    fun insertAny(sign: Char){
        checkLastAdded()
        when(lastAdded){
            "none" -> {
                if(sign=='.')insertNumber("0.")
                if(sign in numbersWithZeroTest)insertNumber(sign.toString())
            }
            "operator"->{
                if(sign in hierarchy){
                    operators[operators.lastIndex]=sign
                }else if(sign in numbersWithZeroTest){
                    insertNumber(sign.toString())
                }else if(sign=='.')insertNumber("0.")
            }
            "number"->{
                if(sign in numbersWithZeroTest || sign=='.'){
                    numbers[numbers.lastIndex]=checkNumber(numbers[numbers.lastIndex],sign)
//                    var numberAssist=numbers[numbers.lastIndex]+sign
//                    numbers[numbers.lastIndex]+=numberAssist
                }else if(sign in hierarchy){
                    insertSign(sign)
                }
            }
        }
    }

    fun checkNumber(number:String,sign:Char):String{
        if(sign=='.'){
            if(!number.contains(sign,true) && number.length>=1)
                return number+sign
        }else{
            return if(number.length==1 && number[0]=='0'){
                number+'.'+sign
            }else{
                number+sign
            }
        }
        return number
    }

    fun simpleOperation(sign:Char,a:Double,b:Double):Double{
        return when (sign) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b != 0.0) a / b else {
                calculatedProperly=false
                return 0.0
            } // to do errors
            else -> 0.0
        }
    }

    fun calculate(){
        if(numbers.lastIndex!=-1 && operators.lastIndex!=-1){

            for(operator in hierarchy){
                while(operator in operators && numbers.lastIndex!=0){
                    var tmpIndex=operators.indexOf(operator)
                    numbers[tmpIndex]=simpleOperation(operator,numbers[tmpIndex].toDouble(),numbers[tmpIndex+1].toDouble()).toString()
                    operators.removeAt(tmpIndex)
                    numbers.removeAt(tmpIndex+1)
                }
            }
        }
    }

    fun delete() {
        if (numbers.lastIndex!=-1) {
            checkLastAdded()
            when (lastAdded) {
                "number" -> numbers.removeAt(numbers.lastIndex)
                "operator" -> operators.removeAt(numbers.lastIndex)
            }
            checkLastAdded()
        }
        getExpression()
    }


    fun checkLastAdded(){
        lastAdded = if(operators.lastIndex==numbers.lastIndex && operators.lastIndex!=-1){
            "operator"
        }else if (operators.lastIndex!=numbers.lastIndex && numbers.lastIndex!=-1){
            "number"
        }else "none"
    }

    fun validation(){
        if(calculatedProperly)
            calculatedProperly = numbers.size>-1
    }
    fun canCalculate():Boolean{
        return numbers.size>operators.size
    }

    fun getResult():String{
        calculatedProperly=true
        outcome=""
        if(!canCalculate())
            if(numbers.size>0)operators.removeAt(operators.lastIndex)
            calculate()

        if(!numbers.none())
            outcome=numbers[0].toString()

       return if(calculatedProperly) {
           "=$outcome"
        } else
            "Error"
    }

    fun getExpression():String{
        var increasingIndex=0
        expressionTxt=""
        if (numbers.lastIndex>=0){
            while (increasingIndex<=numbers.lastIndex) {
                expressionTxt += numbers.elementAt(increasingIndex).toString()
                if (increasingIndex <= operators.lastIndex)
                    expressionTxt += operators.elementAt(increasingIndex).toString()
                increasingIndex++
        }
        }
        return expressionTxt
    }

    //test
    fun testCalculateFuncWithOnlyPlus(){
        clear()
        for ( i in 0..10 )insertNumber(i.toString())
        for (i in 0..9)insertSign('+')
        calculate()
    }
}
