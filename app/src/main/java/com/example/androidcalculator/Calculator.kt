package com.example.androidcalculator

class Calculator (){
    val numbers= mutableListOf<Double>()
    val operators= mutableListOf<Char>()
    private val hierarchy= listOf('/','*','+','-')
    //private val operatorsTest= listOf('/','*','+','-','.')
    private val numbersWithZeroTest= listOf('1','2','3','4','5','6','7','8','9','0')
    private val numbersTest= listOf('1','2','3','4','5','6','7','8','9')
    var previousSign = ' '
    var currentSign = ' '
    var expressionTxt=""
    var number=""
    var isOneDot=false
    var lastAdded="none"
    var outcome=""
    var calculatedProperly=true

    fun clear(){
        numbers.clear()
        operators.clear()
        previousSign=' '
        currentSign=' '
        number=""
        isOneDot=false
        lastAdded="none"
        validation()
    }

    fun insertNumber(num:Double)=numbers.add(num)
    fun insertSign(sign:Char)=operators.add(sign)
    fun insertNumberAndSetSign(num: Double,sign: Char){
        insertNumber(num)
        isOneDot=false
        number=""
        currentSign=sign
    }

    fun insertAny(any:Char){
        previousSign=currentSign
        if (isFirstSign()){
            if(checkSignWithLists(any,numbersWithZeroTest)){
                number+=any
                currentSign=any
            }
        }else{
            when(previousSign){
                '0'->{
                    if(checkSignWithLists(any,hierarchy)){
                        insertNumberAndSetSign(number.toDouble(),any)
                    }else if(any=='.' && !isOneDot){
                        isOneDot=true
                        number+=any
                        currentSign=any
                    }else if(number.length>1 && number!="0" && checkSignWithLists(any,numbersWithZeroTest)){
                        currentSign=any
                        number+=any
                    }
                }
                in hierarchy->{
                    if(checkSignWithLists(any, numbersWithZeroTest)){
                        currentSign=any
                        number+=currentSign
                        insertSign(previousSign)
                    }
                }
                '.'->{
                    if(checkSignWithLists(any,hierarchy)){
                        insertNumberAndSetSign(number.toDouble(),any)
                    }else if (checkSignWithLists(any,numbersWithZeroTest)){
                        currentSign=any
                        number+=any
                    }
                }
                in numbersTest->{
                    if(checkSignWithLists(any, numbersWithZeroTest)){
                        currentSign=any
                        number+=currentSign
                    }else if(checkSignWithLists(any, hierarchy)){
                        insertNumberAndSetSign(number.toDouble(),any)
                    }else if (any=='.'){
                        if(!isOneDot){
                            currentSign=any
                            number+=any
                            isOneDot=true
                        }
                    }
                }
            }
        }
    }

    fun isFirstSign():Boolean{
        if(previousSign== ' ' && currentSign==' ')
            return true
        return false
    }

    fun checkSignWithLists(sign: Char,list: List<Char>):Boolean= sign in list

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
                    numbers[tmpIndex]=simpleOperation(operator,numbers[tmpIndex],numbers[tmpIndex+1])
                    operators.removeAt(tmpIndex)
                    numbers.removeAt(tmpIndex+1)
                }
            }
        }
    }

    fun delete() {
        number=""
        expressionTxt=""
        when(lastAdded){
            "number"->numbers.removeAt(numbers.lastIndex)
            "operator"->operators.removeAt(numbers.lastIndex)
        }
        checkLastAdded()
    }


    fun checkLastAdded(){
        lastAdded = if(operators.lastIndex==numbers.lastIndex && operators.lastIndex!=-1){
            "operator"
        }else if (operators.lastIndex!=numbers.lastIndex && operators.lastIndex!=-1){
            "number"
        }else "none"
    }

    fun insertSignsFromTmpVar(){
        if(!isFirstSign()){
            if(currentSign in numbersWithZeroTest){
                insertNumber(number.toDouble())
            }
        }
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
        insertSignsFromTmpVar()
        if(canCalculate())
            calculate()
        outcome=numbers[0].toString()
        clear()

       return if(calculatedProperly) {
            outcome
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
        for ( i in 0..10 )insertNumber(i.toDouble())
        for (i in 0..9)insertSign('+')
        calculate()
    }
}