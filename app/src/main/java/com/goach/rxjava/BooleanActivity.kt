package com.goach.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_boolean.*
import java.util.concurrent.TimeUnit

class BooleanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boolean)
        title = intent.getStringExtra("title")
        btn_all.setOnClickListener {
            /**
             * 所有元素是否满足条件
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .all {
                    return@all it > 3
                }
                .subscribe({
                    Log.d("subscribe", "接收到消息：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_contain.setOnClickListener {
            /**
             * 是否有对应的元素
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .contains(2)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_sequenceEqual.setOnClickListener {
            /**
             * 丢弃原始 Observable 发射的数据，直到满足条件，才发射原始 Observable 后面的数据
             */
            Observable.sequenceEqual(Observable.fromArray(1, 2, 3, 4, 5),Observable.fromArray(1, 2, 3, 4, 5))
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_isEmpty.setOnClickListener {
            /**
             * 是否是空
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .isEmpty
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
    }
}