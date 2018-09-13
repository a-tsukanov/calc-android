package com.example.sasha.myapplication

import org.mariuszgromada.math.mxparser.*

class CalculatorExpression() : Expression() {

    init {
        expressionString = "0"
    }

    fun append(c: Char): Unit {
        expressionString =
                if (expressionString == "0")
                    "${c}"
                else
                    "${expressionString}${c}"
    }

    fun append(n: Int): Unit {
        append(n.toString().single())
    }

    fun removeLast(): Unit {
        expressionString =
                if (expressionString.length == 1)
                    "0"
                else
                    expressionString.slice(0 until expressionString.length - 1)
    }

    override fun calculate(): Double {
        val operatorsCorrected = Expression(expressionString)
        var toCorrect = operatorsCorrected.expressionString
        toCorrect = toCorrect.replace('÷', '/')
        toCorrect = toCorrect.replace('x', '*')
        operatorsCorrected.expressionString = toCorrect
        return operatorsCorrected.calculate()
    }

    override fun toString(): String = expressionString
}