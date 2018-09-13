package com.example.sasha.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input: TextView = findViewById(R.id.txtExpr)
        fun updateInputView(expression: CalculatorExpression) {
            input.text = expression.toString()
        }


        val result: TextView = findViewById(R.id.txtResult)
        fun updateResultView(expression: CalculatorExpression) {
            result.text = expression.calculate().toString()
        }

        val expression = CalculatorExpression()

        fun addNumbersHandlers() {
            val numberButtons = (0..9).map {
                findViewById<Button>(resources.getIdentifier(
                        "btn${it}", "id", packageName)
                )
            }

            numberButtons.forEachIndexed { i, btn ->
                btn.setOnClickListener {
                    expression.append(i)
                    updateInputView(expression)
                }
            }
        }
        addNumbersHandlers()

        fun addOperatorsHandlers() {
            val binaryOperators = mapOf<Button, Char>(
                    Pair(findViewById(R.id.btnAdd), Operators.PLUS),
                    Pair(findViewById(R.id.btnSubstract), Operators.MINUS),
                    Pair(findViewById(R.id.btnDivide), Operators.DIVIDE),
                    Pair(findViewById(R.id.btnMultiply), Operators.MULTIPLY),
                    Pair(findViewById(R.id.btnPow), Operators.EXPONENTIATE)
            )

            binaryOperators.map {
                val (btn, operator) = it
                btn.setOnClickListener {
                    expression.append(operator, overrideFirstZero = false)
                    updateInputView(expression)
                }
            }
        }
        addOperatorsHandlers()

        fun addConfirmHandler() {
            val confirm = findViewById<Button>(R.id.btnConfirm)
            confirm.setOnClickListener {
                try {
                    updateResultView(expression)
                } catch (e: InvalidExpressionError) {

                    Toast
                            .makeText(this, "Please check your expression", Toast.LENGTH_LONG)
                            .show()

                }
            }
        }
        addConfirmHandler()

        fun addEraseHandler() {
            val erase = findViewById<Button>(R.id.btnErase)
            erase.setOnClickListener {
                expression.removeLast()
                updateInputView(expression)
            }
            erase.setOnLongClickListener {
                expression.clear()
                updateInputView(expression)
                updateResultView(expression)
                return@setOnLongClickListener true
            }
        }
        addEraseHandler()

        fun addDotHandler() {
            val DOT = '.'
            val dot = findViewById<Button>(R.id.btnDot)
            dot.setOnClickListener {
                expression.append(DOT, overrideFirstZero = false)
                updateInputView(expression)
            }
        }
        addDotHandler()

        fun addParenthesisHandler() {
            val mapping = mapOf<Button, Char>(
                    Pair(findViewById(R.id.btnLeftPar), Parenthesis.OPENING),
                    Pair(findViewById(R.id.btnRightPar), Parenthesis.CLOSING)
            )
            mapping.map {
                val (btn, c) = it
                btn.setOnClickListener {
                    expression.append(c)
                    updateInputView(expression)
                }
            }
        }
        addParenthesisHandler()

        fun addUnaryFuncsHandlers() {
            val unaryFuncs = mapOf<Button, String>(
                    Pair(findViewById(R.id.btnSqrt), UnaryFunctions.SQRT),
                    Pair(findViewById(R.id.btnLog), UnaryFunctions.LOG),
                    Pair(findViewById(R.id.btnSin), UnaryFunctions.SIN),
                    Pair(findViewById(R.id.btnCos), UnaryFunctions.COS)
            )
            unaryFuncs.map {
                val (btn, func) = it
                btn.setOnClickListener {
                    expression.append("${func}(", overrideFirstZero = true)
                    updateInputView(expression)
                }
            }
        }
        addUnaryFuncsHandlers()

    }
}
