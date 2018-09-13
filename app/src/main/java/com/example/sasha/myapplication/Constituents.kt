package com.example.sasha.myapplication

class Operators {
    companion object {
        val PLUS = '+'
        val MINUS = '-'
        val MULTIPLY = 'x'
        val DIVIDE = '÷'
        val EXPONENTIATE = '^'

        val all = listOf(PLUS, MINUS, MULTIPLY, DIVIDE, EXPONENTIATE)
    }
}

class Parenthesis {
    companion object {
        val OPENING = '('
        val CLOSING = ')'
    }
}

class UnaryFunctions {
    companion object {
        val SIN = "sin"
        val COS = "cos"
        val LOG = "ln"
        val SQRT = "sqrt"

        val all = listOf(SIN, COS, LOG, SQRT)
    }
}

const val ZERO_STRING = "0"