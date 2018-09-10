package com.example.sasha.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.time.Duration

import kotlin.math.sqrt
import kotlin.math.log

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var expression = Expression()

        val input = findViewById<TextView>(R.id.txtExpr)
        val result = findViewById<TextView>(R.id.txtResult)

        fun addNumbersHandlers() {
            val numberButtons = (0..9).map {
                findViewById<Button>(resources.getIdentifier("btn$it", "id", packageName))
            }

            numberButtons.forEachIndexed { i, btn ->
                btn.setOnClickListener {
                    if (expression.editingState == Expression.EditingState.GOT_RESULTS) {
                        expression = Expression()
                    }
                    expression.append(i)
                    updateInputView(input, expression)
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
                    when (expression.editingState) {
                        Expression.EditingState.GOT_RESULTS -> {
                            expression.expression = expression.evaluate().toString()
                        }
                        Expression.EditingState.RIGHT_OPERAND -> {
                            updateResultView(result, expression)
                            expression.expression = expression.evaluate().toString()
                        }

                    }
                    expression.append(operator)
                    updateInputView(input, expression)
                    expression.editingState = Expression.EditingState.RIGHT_OPERAND
                }
            }
        }
        addOperatorsHandlers()

        fun addConfirmHandler() {

            val confirm = findViewById<Button>(R.id.btnConfirm)
            confirm.setOnClickListener {
                try {
                    updateResultView(result, expression)
                    expression.editingState = Expression.EditingState.GOT_RESULTS
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
                if (expression.editingState == Expression.EditingState.GOT_RESULTS)
                    expression = Expression()
                expression.eraseLast()
                if (!expression.containsOperator)
                // User has erased the operator from the expression in the input field
                    expression.editingState = Expression.EditingState.LEFT_OPERAND
                if (expression.expression.isEmpty())
                // User has erased the only character from the input
                    expression.expression = "0"
                updateInputView(input, expression)
            }
        }
        addEraseHandler()

        fun addDotHandler() {
            val dot = findViewById<Button>(R.id.btnDot)
            dot.setOnClickListener {
                expression.append('.')
                updateInputView(input, expression)
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

                    val evaluated = expression.evaluate().toDouble()
                    expression.expression = func(evaluated).toString()
                    updateInputView(input, expression)
                    result.text = expression.expression
                    expression.editingState = Expression.EditingState.GOT_RESULTS
                }
            }
        }
        addUnaryFuncsHandlers()

    }

    private fun updateInputView(inputView: TextView, expression: Expression) {
        inputView.text = expression.toString()
    }

    private fun updateResultView(resultView: TextView, expression: Expression) {
        resultView.text = expression.evaluate().toString()
    }


}
