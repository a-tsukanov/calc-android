package com.example.sasha.myapplication

import org.mariuszgromada.math.mxparser.*

class InvalidExpressionError: Error()

class CalculatorExpression : Expression() {

    init {
        expressionString = ZERO_STRING
    }

    val PARSEABLE_DIVIDE = '/'
    val PARSEABLE_MULTIPLY = '*'

    fun append(c: Char, overrideFirstZero: Boolean = true): Unit {
        expressionString =
                if (expressionString == ZERO_STRING && overrideFirstZero)
                    "${c}"
                else
                    "${expressionString}${c}"
    }

    fun append(n: Int, overrideFirstZero: Boolean = true): Unit {
        append(n.toString().single(), overrideFirstZero)
    }

    fun append(s: String, overrideFirstZero: Boolean): Unit {
        s.iterator().forEach { append(it, overrideFirstZero) }
    }

    fun removeLast(): Unit {
        var numCharsToRemove = 1
        for (f in UnaryFunctions.all) {
            if (expressionString.endsWith("${f}${Parenthesis.OPENING}")) numCharsToRemove = f.length + 1
        }

        expressionString =
                if (expressionString.length == numCharsToRemove)
                    "0"
                else
                    expressionString.slice(
                            0 until expressionString.length - numCharsToRemove
                    )
    }

    fun clear(): Unit {
        expressionString = ZERO_STRING
    }

    override fun calculate(): Double {
        val operatorsCorrected = Expression(expressionString)
        var toCorrect = operatorsCorrected.expressionString
        toCorrect = toCorrect.replace(Operators.DIVIDE, PARSEABLE_DIVIDE)
        toCorrect = toCorrect.replace(Operators.MULTIPLY, PARSEABLE_MULTIPLY)
        operatorsCorrected.expressionString = toCorrect

        val result = operatorsCorrected.calculate()
        if (result.isNaN())
            throw InvalidExpressionError()
        else
            return result

    }

    override fun toString(): String = expressionString
}