package com.example.btvn_mycalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentNumber: String = ""
    private var operator: String? = null
    private var firstOperand: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonPlus, R.id.buttonMinus, R.id.buttonMultiply, R.id.buttonDivide,
            R.id.buttonEquals, R.id.buttonC, R.id.buttonCE, R.id.buttonBS, R.id.buttonDot, R.id.buttonPlusMinus
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onButtonClick(it as Button) }
        }
    }

    private fun onButtonClick(button: Button) {
        when (val buttonText = button.text.toString()) {
            in "0".."9", "." -> appendNumber(buttonText)
            "+", "-", "x", "/" -> setOperator(buttonText)
            "=" -> calculateResult()
            "C" -> clear()
            "CE" -> clearEntry()
            "BS" -> backspace()
            "+/-" -> toggleSign()
        }
    }

    private fun appendNumber(number: String) {
        if (number == "." && currentNumber.contains(".")) return
        currentNumber += number
        display.text = currentNumber
    }

    private fun setOperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstOperand = currentNumber.toDouble()
            currentNumber = ""
        }
        operator = op
    }

    private fun calculateResult() {
        if (operator != null && currentNumber.isNotEmpty()) {
            val secondOperand = currentNumber.toDouble()
            val result = when (operator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "x" -> firstOperand!! * secondOperand
                "/" -> firstOperand!! / secondOperand
                else -> return
            }
            display.text = result.toString()
            currentNumber = result.toString()
            operator = null
            firstOperand = null
        }
    }

    private fun clear() {
        currentNumber = ""
        operator = null
        firstOperand = null
        display.text = "0"
    }

    private fun clearEntry() {
        currentNumber = ""
        display.text = "0"
    }

    private fun backspace() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            display.text = if (currentNumber.isEmpty()) "0" else currentNumber
        }
    }

    private fun toggleSign() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = if (currentNumber.startsWith("-")) {
                currentNumber.drop(1)
            } else {
                "-$currentNumber"
            }
            display.text = currentNumber
        }
    }
}