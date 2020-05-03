package com.example.androidcalculator

class Calculator (){
    private val numbers= mutableListOf<Double>()
    private val operators= mutableListOf<Char>()
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
    var calculatedProperly=true

    fun clear(){
        numbers.clear()
        operators.clear()
        previousSign=' '
        currentSign=' '
        number=""
        isOneDot=false
        lastAdded="none"
        calculatedProperly=true
    }

    fun insertNumber(num:Double)=numbers.add(num)
    fun insertSign(sign:Char)=operators.add(sign)
    fun insertNumberAndSetSign(num: Double,sign: Char){
        insertNumber(num)
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
                    }
                }
                in hierarchy->{
                    if(checkSignWithLists(any, numbersTest)){
                        currentSign=any
                        number+=currentSign
                        insertSign(previousSign)
                    }
                }
                '.'->{
                    if(checkSignWithLists(any,hierarchy)){
                        insertNumberAndSetSign(number.toDouble(),any)
                    }
                }
                in numbersTest->{
                    if(checkSignWithLists(any, numbersTest)){
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

    fun checkSignWithLists(sign: Char,list: List<Char>):Boolean{
        return sign in list
    }

    fun simpleOperation(sign:Char,a:Double,b:Double):Double{
        return when (sign) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b != 0.0) a / b else 0.0 // to do errors
            else -> 0.0
        }
    }
    fun calculate(){
        if(numbers.lastIndex!=-1 && operators.lastIndex!=-1){
            if(number.lastIndex==operators.lastIndex){
                operators.removeAt(operators.lastIndex)
            }
            for(operator in hierarchy){
                while(operator in operators){
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
            if(currentSign in numbersTest){
                insertNumber(number.toDouble())
            }
        }
    }
    fun validation(){
        calculatedProperly = numbers.size>-1
    }

    fun getResult():String{
        insertSignsFromTmpVar()
        calculate()
        clear()
        validation()
        return if(calculatedProperly) {
            numbers.toString()
        } else
            "Error"
    }

    fun getExpression():String{
        var increasingIndex=0
        if (numbers.lastIndex>0){
            while (increasingIndex!=numbers.lastIndex) {
                expressionTxt += numbers.elementAt(increasingIndex).toString()
                if (increasingIndex <= operators.lastIndex)
                    expressionTxt += operators.elementAt(increasingIndex).toString()
                increasingIndex++
        }
        }
        return expressionTxt
    }
}