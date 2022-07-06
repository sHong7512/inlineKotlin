package com.shong.inlinekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val exText1 = findViewById<TextView>(R.id.exText1)
        val exText2 = findViewById<TextView>(R.id.exText2)
        val exText3 = findViewById<TextView>(R.id.exText3)
        val exText4 = findViewById<TextView>(R.id.exText4)

        exText1.countClickListener_1({ 0 }) { cnt, str ->
            exText1.text = "<<$str>>\nCount : $cnt"
        }
        exText2.countClickListener_2({ 0 }) { cnt, str ->
            exText2.text = "<<$str>>\nCount : $cnt"
        }
        exText3.countClickListener_3({ 0 }) {tv, cnt ->
            tv.text = "<<example 3 reified Extension>>\nCount : $cnt"
        }
        exText4.countClickListener_4(null) {tv, cnt ->
            tv.text = "<<example 4 noinline, invoke Extension>>\nCount : $cnt"
        }
    }

    private fun View.countClickListener_1(initialCount: () -> Int, clickOperator: (String, Int) -> Unit){
        var cnt = initialCount()
        setOnClickListener {
            cnt++
            clickOperator("example 1 basic Extension", cnt)
        }
    }

    private inline fun View.countClickListener_2(initialCount: () -> Int, crossinline clickOperator: (String, Int) -> Unit){
        var cnt = initialCount()
        setOnClickListener {
            cnt++
            clickOperator("example 2 Inline, crossinline Extension", cnt)
        }
    }

    private inline fun <reified T: View> T.countClickListener_3(initialCount: () -> Int, crossinline clickOperator: (T, Int) -> Unit){
        var cnt = initialCount()
        setOnClickListener { view ->
            cnt++
            clickOperator(view as T, cnt)
        }
    }

    private inline fun <reified T: View> T.countClickListener_4(noinline initialCount: (() -> Int)?, crossinline clickOperator: (T, Int) -> Unit){
        var cnt = initialCount?.invoke() ?: 100
        setOnClickListener { view ->
            cnt++
            clickOperator(view as T, cnt)
        }
    }
}