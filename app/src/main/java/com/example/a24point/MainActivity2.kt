package com.example.a24point

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class MainActivity2 : AppCompatActivity() {
    companion object {
        const val ONE = "yi"
        const val TWO = "er"
        const val THREE = "san"
        const val FOUR = "si"
    }
    //用来判断是否有解
    private var flag = false
    //存放操作符
    private val operator = charArrayOf('+', '-', '*', '/')
    //存放所有结果
    private val results = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val my= ViewModelProvider(this).get(Myvi::class.java)
        val number=DoubleArray(4)
        number[0] = intent.getDoubleExtra(ONE, 0.0)
        number[1] = intent.getDoubleExtra(TWO, 0.0)
        number[2] = intent.getDoubleExtra(THREE, 0.0)
        number[3] = intent.getDoubleExtra(FOUR, 0.0)
        duplicateRemoval(number)
        if(flag) {
            val lay = GridLayoutManager(this, 1)
            recc.layoutManager = lay
            my.ac = Equationadapter(this, results)
            recc.adapter = my.ac
        }
        else
            Toast.makeText(this,"没有可能的算式!", Toast.LENGTH_LONG).show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun getNumber(number: DoubleArray) {
        for (i in 0..3) {
            for (j in 0..3) {
                if (i == j)
                    continue
                for (k in 0..3) {
                    if (k == j || k == i)
                        continue
                    for (m in 0..3) {
                        if (m == k || m == j || m == i)
                            continue
                        calculation(number[i], number[j], number[k], number[m])
                    }
                }
            }
        }
    }
    private fun calculation(num1: Double, num2: Double, num3: Double, num4: Double): Boolean {
        for (i in 0..3) {
            /*	第一次计算，保存此时的操作符和计算结果
        此时有3中情况，相当于从4个数中选择2个相邻的数来计算
        如（1-2）-3-4， 1-（2-3）-4， 1-2-（3-4）
        则保存此时第一次计算的结果和操作符*/
            val operator1 = operator[i]
            //根据操作符，先计算第1,2两个数，如输入数字是1,2,3,4  则计算1+2（1-2,1*2,1/2等），
            //这里通过循环来改变操作符，下同
            val firstResult: Double = calcute(num1, num2, operator1)
            //根据操作符，先计算第2,3两个数，如输入数字是1,2,3,4  则计算2+3
            val midResult: Double = calcute(num2, num3, operator1)
            //根据操作符，先计算第3,4两个数，如输入数字是1,2,3,4  则计算3+4
            val tailResult: Double = calcute(num3, num4, operator1)
            for (j in 0..3) {
                /*	第2次计算，保存此时的操作符和计算结果
            此时有5中情况，相当于从4个数中选择2个相邻的数来计算
            如（（1-2）-3）-4， （1-（2-3））-4， （1-2）-（3-4），1-（（2-3）-4），1-（2-（3-4））
            则保存此时第2次计算的结果和操作符*/
                val operator2 = operator[j]
                //根据操作符和第1次计算的结果，计算第2次的情况，如第一次计算是（1-2）-3-4，
                //就计算（（1-2）-3）-4 ，则第一次计算结果为1-2=-1  -->   即计算-1-3，即firstResult-3
                //下面的原理类似
                val firstMidResult: Double = calcute(firstResult, num3, operator2)
                val firstTailResult: Double = calcute(num3, num4, operator2)
                val midFirstResult: Double = calcute(num1, midResult, operator2)
                val midTailResult: Double = calcute(midResult, num4, operator2)
                val tailMidResult: Double = calcute(num2, tailResult, operator2)
                for (k in 0..3) {
                    //最后1次计算，得出结果，如果是24则保存表达式，原理同上
                    val operator3 = operator[k]
                    if (calcute(firstMidResult, num4, operator3) == 24.0) {
                        val expression =
                            "((" + num1.toInt() + operator1 + num2.toInt() + ")" + operator2 + num3.toInt() + ")" + operator3 + num4.toInt()
                        results.add(expression)
                        flag = true
                    }
                    if (calcute(firstResult, firstTailResult, operator3) == 24.0) {
                        val expression =
                            "(" + num1.toInt() + operator1 + num2.toInt() + ")" + operator3 + "(" + num3.toInt() + operator2 + num4.toInt() + ")"
                        results.add(expression)
                        flag = true
                    }
                    if (calcute(midFirstResult, num4, operator3) == 24.0) {
                        val expression =
                            "(" + num1.toInt() + operator2 + "(" + num2.toInt() + operator1 + num3.toInt() + "))" + operator3 + num4.toInt()
                        results.add(expression)
                        flag = true
                    }
                    if (calcute(num1, midTailResult, operator3) == 24.0) {
                        val expression =
                            "" + num1.toInt() + operator3 + "((" + num2.toInt() + operator1 + num3.toInt() + ")" + operator2 + num4.toInt() + ")"
                        results.add(expression)
                        flag = true
                    }
                    if (calcute(num1, tailMidResult, operator3) == 24.0) {
                        val expression =
                            "" + num1.toInt() + operator3 + "(" + num2.toInt() + operator2 + "(" + num3.toInt() + operator1 + num4.toInt() + "))"
                        results.add(expression)
                        flag = true
                    }
                }
            }
        }
        return flag
    }
    private fun calcute(number1: Double, number2: Double, operator: Char): Double {
        return if (operator == '+') {
            number1 + number2
        } else if (operator == '-') {
            number1 - number2
        } else if (operator == '*') {
            number1 * number2
        } else if (operator == '/' && number2 != 0.0) {
            number1 / number2
        } else {
            (-1).toDouble()
        }
    }
    private fun duplicateRemoval(number: DoubleArray) {
        val map: MutableMap<Double, Int> = HashMap()
        //存放数字，用来判断输入的4个数字中有几个重复的，和重复的情况
        for (i in number.indices) {
            if (map[number[i]] == null)
                map[number[i]] = 1
             else
                map[number[i]] = map[number[i]]!! + 1
        }
        if (map.size == 1) {
            //如果只有一种数字（4个不同花色的），此时只有一种排列组合，如6,6,6,6
            calculation(number[0], number[1], number[2], number[3])
        } else if (map.size == 2) {
            //如果只有2种数字，有2种情况，如1,1,2,2和1,1,1,2
            var index = 0 //用于数据处理
            var state = 0 //判断是那种情况
            for (key in map.keys) {
                when {
                    map[key] == 1 -> {
                        //如果是有1个数字和其他3个都不同，将number变为 number[0]=number[1]=number[2]，
                        //将不同的那个放到number[3]，方便计算
                        number[3] = key
                        state = 1
                    }
                    map[key] == 2 -> {
                        //两两相同的情况，将number变为number[0]=number[1],number[2]=number[3]的情况，方便计算
                        number[index++] = key
                        number[index++] = key
                    }
                    else ->
                        number[index++] = key
                }
            }
            //列出2种情况的所有排列组合，并分别计算
            if (state == 1) {
                calculation(number[3], number[1], number[1], number[1])
                calculation(number[1], number[3], number[1], number[1])
                calculation(number[1], number[1], number[3], number[1])
                calculation(number[1], number[1], number[1], number[3])
            }
            if (state == 0) {
                calculation(number[1], number[1], number[3], number[3])
                calculation(number[1], number[3], number[1], number[3])
                calculation(number[1], number[3], number[3], number[1])
                calculation(number[3], number[1], number[1], number[3])
                calculation(number[3], number[3], number[1], number[1])
                calculation(number[3], number[1], number[3], number[1])
            }
        } else if (map.size == 3) {
            //有3种数字的情况
            var index = 0
            for (key in map.keys) {
                if (map[key] == 2) {
                    //将相同的2个数字放到number[2]=number[3]，方便计算
                    number[2] = key
                    number[3] = key
                } else
                    number[index++] = key
            }
            //排列组合，所有情况
            calculation(number[0], number[1], number[3], number[3])
            calculation(number[0], number[3], number[1], number[3])
            calculation(number[0], number[3], number[3], number[1])
            calculation(number[1], number[0], number[3], number[3])
            calculation(number[1], number[3], number[0], number[3])
            calculation(number[1], number[3], number[3], number[0])
            calculation(number[3], number[0], number[1], number[3])
            calculation(number[3], number[0], number[3], number[1])
            calculation(number[3], number[1], number[0], number[3])
            calculation(number[3], number[1], number[3], number[0])
            calculation(number[3], number[3], number[0], number[1])
            calculation(number[3], number[3], number[1], number[0])
        } else if (map.size == 4)
            //4个数都不同的情况
            getNumber(number)
    }
}