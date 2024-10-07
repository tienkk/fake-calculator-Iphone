package com.example.calculator

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.TextView
import java.text.DecimalFormat
import java.text.NumberFormat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var tvResult: TextView? = null
    private var tvAC: TextView? = null
    private var tvPlusMinus: TextView? = null
    private var tvPercent: TextView? = null
    private var tvPlus: TextView? = null
    private var tvMinus: TextView? = null
    private var tvTimes: TextView? = null
    private var tvDiv: TextView? = null
    private var tvEqualSign: TextView? = null
    private var tvDot: TextView? = null
    private var tv0: TextView? = null
    private var tv1: TextView? = null
    private var tv2: TextView? = null
    private var tv3: TextView? = null
    private var tv4: TextView? = null
    private var tv5: TextView? = null
    private var tv6: TextView? = null
    private var tv7: TextView? = null
    private var tv8: TextView? = null
    private var tv9: TextView? = null

    private var concatChar = ""
    private var list: MutableList<String> = mutableListOf()
    private var checkEventDecimal = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        tvResult = findViewById(R.id.tvResult)
        tvAC = findViewById(R.id.tvAC)
        tvPlusMinus = findViewById(R.id.tvPlusMinus)
        tvPercent = findViewById(R.id.tvPercent)
        tvPlus = findViewById(R.id.tvPlus)
        tvMinus = findViewById(R.id.tvMinus)
        tvTimes = findViewById(R.id.tvTimes)
        tvDiv = findViewById(R.id.tvDiv)
        tvEqualSign = findViewById(R.id.tvEqualSign)
        tvDot = findViewById(R.id.tvDot)
        tv0 = findViewById(R.id.tv0)
        tv1 = findViewById(R.id.tv1)
        tv2 = findViewById(R.id.tv2)
        tv3 = findViewById(R.id.tv3)
        tv4 = findViewById(R.id.tv4)
        tv5 = findViewById(R.id.tv5)
        tv6 = findViewById(R.id.tv6)
        tv7 = findViewById(R.id.tv7)
        tv8 = findViewById(R.id.tv8)
        tv9 = findViewById(R.id.tv9)
        funcKeys()
        handleNumber()
        handleCalculate()
        handleDot()
    }

    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables", "SetTextI18n")
    private fun funcKeys() {
        val tops = arrayOf(tvAC, tvPlusMinus, tvPercent)
        tops.forEach { top ->
            top?.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        top.background = getDrawable(R.drawable.bg_white_radius)
                        when (top) {
                            tvAC -> {
                                tvResult?.gravity = Gravity.END or Gravity.BOTTOM
//                                tvResult?.textDirection = View.TEXT_DIRECTION_LTR
                                tvResult?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 145f)
                                tvResult?.text = "0"
                                concatChar = ""
                                list.clear()
                                if (concatChar.isNotEmpty()) tvAC?.text = "C" else tvAC?.text = "AC"

                                checkEventDecimal = false
                            }

                            tvPlusMinus -> {
                                try {
                                    concatChar = concatChar.toDouble().unaryMinus().toString()
                                    tvResult?.text = concatChar
                                } catch (e: Exception) {
                                    tvResult?.text = "error Minus"
                                    autoResizeText(tvResult?.text.toString())
                                }
                            }

                            tvPercent -> {
                                try {
                                    concatChar = (concatChar.toDouble() / 100).toString()
                                    tvResult?.text = concatChar
                                } catch (e: Exception) {
                                    tvResult?.text = "error Percent"
                                    autoResizeText(tvResult?.text.toString())
                                }
                            }
                        }

                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        top.background = getDrawable(R.drawable.bg_top_tv)
                        true
                    }

                    else -> false
                }
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables", "SetTextI18n")
    private fun handleNumber() {
        val numbers = arrayOf(tv0, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9)
        numbers.forEach { number ->
            number?.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (concatChar.length >= 0) tvAC?.text = "C" else tvAC?.text = "AC"
                        number.background = getDrawable(R.drawable.bg_top_tv)

                        if (checkEventDecimal) tvResult?.text =
                            concatStr(number.text.toString()) else plusStrAndFormat(number.text.toString())

                        autoResizeText(tvResult?.text.toString())
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        number.background = getDrawable(R.drawable.bg_number_tv)
                        true
                    }

                    else -> false
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables", "SetTextI18n")
    private fun handleCalculate() {
        val calculations = arrayOf(tvPlus, tvMinus, tvTimes, tvDiv, tvEqualSign)
        calculations.forEach { calculation ->
            calculation?.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        calculation.background = getDrawable(R.drawable.bg_white_radius)
                        calculation.setTextColor(Color.rgb(241, 163, 59))
                        when (calculation) {
                            tvPlus -> {
                                tvResult?.gravity = Gravity.END or Gravity.BOTTOM
                                list.add(concatChar)
                                concatChar = ""
                                list.add("+")
                            }

                            tvMinus -> {
                                tvResult?.gravity = Gravity.END or Gravity.BOTTOM
                                @Suppress("EqualsBetweenInconvertibleTypes")
                                if (!(tvResult?.text.toString().length - 1).equals("-")) {
                                    list.add(concatChar)
                                    concatChar = ""
                                    list.add("-")
                                }

                            }

                            tvTimes -> {
                                tvResult?.gravity = Gravity.END or Gravity.BOTTOM
                                @Suppress("EqualsBetweenInconvertibleTypes")
                                if (!(tvResult?.text.toString().length - 1).equals("*")) {
                                    list.add(concatChar)
                                    concatChar = ""
                                    list.add("*")
                                }
                            }

                            tvDiv -> {
                                tvResult?.gravity = Gravity.END or Gravity.BOTTOM
                                list.add(concatChar)
                                concatChar = ""
                                list.add("/")
                            }

                            tvEqualSign -> {
                                tvResult?.gravity = Gravity.END or Gravity.BOTTOM
                                try {
                                    list.add(concatChar)
                                    handleLogic()
                                    autoResizeText(tvResult?.text.toString())

                                    concatChar = tvResult?.text.toString()
                                    list.clear()
                                    list.add(concatChar)

                                    concatChar = ""
                                } catch (e: Exception) {
                                    tvResult?.text = "Error Cal"
                                    autoResizeText(tvResult?.text.toString())
                                }
                            }
                        }
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        calculation.background = getDrawable(R.drawable.bg_calculation_tv)
                        calculation.setTextColor(Color.WHITE)
                        true
                    }

                    else -> false
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables", "SetTextI18n")
    private fun handleDot() {
        tvDot?.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    tvDot?.background = getDrawable(R.drawable.bg_top_tv)
                    if (concatChar.length >= 0) tvAC?.text = "C" else tvAC?.text = "AC"

                    if (!checkEventDecimal) {
                        tvResult?.text = concatStr(tvDot?.text.toString())
                        autoResizeText(tvResult?.text.toString())
                        checkEventDecimal = true
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    tvDot?.background = getDrawable(R.drawable.bg_number_tv)
                    true
                }

                else -> false
            }
        }
    }


    private fun formatNumber(number: Long): String {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.minimumFractionDigits = 0
        numberFormat.maximumFractionDigits = 3
        return numberFormat.format(number)
    }


    @SuppressLint("SetTextI18n")
    private fun plusStrAndFormat(str: String) {
        try {
            tvResult?.text = formatNumber(concatStr(str).toLong())
        } catch (e: Exception) {
            e.printStackTrace()
            tvResult?.text = "Error Format"
        }
    }


    @SuppressLint("SetTextI18n")
    private fun concatStr(str: String): String {
        if (concatChar.length < 9) {
            concatChar += str
        }
        return concatChar
    }


    private fun autoResizeText(input: String) {
        when (input.length) {
            in 1..5 -> tvResult?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 145f)
            in 6..6 -> tvResult?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 125f)
            in 7..7 -> tvResult?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 110f)
            in 8..8 -> tvResult?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 109f)
            in 9..9 -> {
                tvResult?.gravity = Gravity.CENTER or Gravity.BOTTOM
                tvResult?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 94f)
            }
            in 10..200 -> tvResult?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 74f)
        }
    }

    private fun handleLogic() {
        val operands = mutableListOf<Double>() //toan tử [2, 5, 2, 12, 8]
        val operators = mutableListOf<String>() // toan hang [+ , - , * , /]

        for (item in list) {
            when {
                item.contains(",") -> operands.add(handleDecimal(item).toDouble())

                isNumeric(item) -> operands.add(item.toDouble())

                item == "*" || item == "/" -> operators.add(item)

                item == "+" || item == "-" -> {
                    while (operators.isNotEmpty() && (operators.last() == "*" || operators.last() == "/")) {
                        val operator = operators.removeLast()
                        val operand2 = operands.removeLast()
                        val operand1 = operands.removeLast()
                        val result = performOperation(operand1, operand2, operator)
                        operands.add(result)
                    }
                    operators.add(item)
                }
            }
        }

        while (operators.isNotEmpty()) {
            val operator = operators.removeLast()
            val operand2 = operands.removeLast()
            val operand1 = operands.removeLast()
            val result = performOperation(operand1, operand2, operator)
            operands.add(result)
        }
        tvResult?.text = convertToIntOrDouble(operands.last())
    }

    private fun convertToIntOrDouble(value: Double): String {
        return if (value % 1 == 0.0) {
            value.toInt().toString()
        } else {
            value.toString()
        }
    }

    private fun performOperation(operand1: Double, operand2: Double, operator: String): Double {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> operand1 / operand2
            else -> throw IllegalArgumentException("Error Cal: $operator")
        }
    }

    private fun isNumeric(input: String): Boolean {
        return try {
            input.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun handleDecimal(str: String): String {
        val decimalFormat = DecimalFormat("#.######")
        val formatNumber = decimalFormat.parse(str)!!.toDouble()
        return formatNumber.toString()
    }

}