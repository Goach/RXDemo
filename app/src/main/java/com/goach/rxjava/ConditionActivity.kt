package com.goach.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_condition.*
import java.lang.NullPointerException
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class ConditionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condition)
        title = intent.getStringExtra("title")
        btn_amb.setOnClickListener {
            /**
             * 当传递多个Observable给amb操作符时，该操作符只发射其中一个Observable的数据和通知：
            * 首先发送通知给amb操作符的的那个Observable，
            * 不管发射的是一项数据还是一个onError或onCompleted通知，amb将忽略和丢弃其它所有Observables的发射物
             */
            Observable.amb(mutableListOf(Observable.fromArray(1,2,3,4,5),Observable.fromArray(4,5,6,7,8)))
                .subscribe({
                    Log.d("subscribe", "接收到消息：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_defaultIfEmpty.setOnClickListener {
            /**
             * 设置空情况下的默认元素
             */
            Observable.empty<Int>()
                .defaultIfEmpty(2)
                .subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_skipUntil.setOnClickListener {
            /**
             * 丢弃原始 Observable 发射的数据，直到第二个 Observable 发射了一个数据，才发射原始 Observable 后面的数据
             */
            Observable.interval(1,TimeUnit.SECONDS)
                .skipUntil(Observable.interval(4,TimeUnit.SECONDS)).subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_skipWhile.setOnClickListener {
            /**
             * 丢弃原始 Observable 发射的数据，直到满足条件，才发射原始 Observable 后面的数据
             */
            Observable.fromArray(1,2,3,4,5)
                .skipWhile{
                    return@skipWhile it <= 2
                }.subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_takeUtil.setOnClickListener {
            /**
             * 获取原始 Observable 发射的数据，直到第二个 Observable 发射了一个数据，放弃 Observable 后面的数据
             */
            Observable.interval(1,TimeUnit.SECONDS)
                .takeUntil(Observable.interval(4,TimeUnit.SECONDS)).subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_takeWhile.setOnClickListener {
            /**
             * 发送原始 Observable 发射的数据，直到不满足条件
             */
            Observable.fromArray(1,2,3,4,5)
                .takeWhile{
                    return@takeWhile it <= 2
                }.subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
    }
}