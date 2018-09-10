package com.example.sasha.myapplication

import java.lang.Math.pow

class Expression {
    enum class EditingState {
        LEFT_OPERAND,
        RIGHT_OPERAND,
        GOT_RESULTS,
    }
    var editingState = EditingState.LEFT_OPERAND
    var expression = ""
    private val operators = listOf('÷', 'x', '-', '+', '^')

    fun evaluate(): Number {
        for(operator in operators) {
            if(!expression.contains(operator)) continue

            val (leftOperand, rightOperand) = expression.split(operator)
            val a = leftOperand.toDouble()
            val b = rightOperand.toDouble()
            return when(operator) {
                '÷' -> a / b
                'x' -> a * b
                '-' -> a - b
                '+' -> a + b
                '^' -> pow(a, b)
                else -> throw Error("no such operator: ${operator}")

            }
        }
        return expression.toDouble()
    }

    fun append(n: Int) {
        expression = if(expression == "0")
            "${n}"
        else
            "${expression}${n}"
    }

    fun append(c: Char) {
        expression = "${expression}${c}"
    }

    fun eraseLast() {
        expression = expression.slice(0 until (expression.length - 1))
    }

    val containsOperator: Boolean
        get() = operators.any {expression.contains(it)}

    override fun toString() = expression

}