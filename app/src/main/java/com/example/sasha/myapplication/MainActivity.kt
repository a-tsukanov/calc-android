package com.example.sasha.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import kotlin.math.sqrt
import kotlin.math.log

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
                        "btn$it", "id", packageName)
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
                    Pair(findViewById(R.id.btnAdd), '+'),
                    Pair(findViewById(R.id.btnSubstract), '-'),
                    Pair(findViewById(R.id.btnDivide), '÷'),
                    Pair(findViewById(R.id.btnMultiply), 'x'),
                    Pair(findViewById(R.id.btnPow), '^')
            )

            binaryOperators.map {
                val (btn, operator) = it
                btn.setOnClickListener {
                    expression.append(operator)
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
                } catch (e: ArithmeticException) {

                    Toast
                            .makeText(this, "Cannot divide by zero", Toast.LENGTH_LONG)
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
        }
        addEraseHandler()

        fun addDotHandler() {
            val DOT = '.'
            val dot = findViewById<Button>(R.id.btnDot)
            dot.setOnClickListener {
                expression.append(DOT)
            }
        }
        addDotHandler()

        fun addUnaryFuncsHandlers() {
            val unaryFuncs = mapOf<Button, (Double) -> Double>(
                    Pair(findViewById(R.id.btnSqrt), { i -> sqrt(i) }),
                    Pair(findViewById(R.id.btnLog), { i -> log(i, kotlin.math.E) })
            )
            unaryFuncs.map {
                val (btn, func) = it
                btn.setOnClickListener {
                    throw NotImplementedError()

                }
            }
        }
        addUnaryFuncsHandlers()

    }
}
