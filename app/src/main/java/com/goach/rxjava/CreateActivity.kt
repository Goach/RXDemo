package com.goach.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create.*
import java.lang.NullPointerException
import java.lang.StringBuilder
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        title = intent.getStringExtra("title")
        btn_create.setOnClickListener {
            /**
             * subscribeActual 方法： subscribe 订阅下游 CreateEmitter 调用下游
             */
            Observable.create(ObservableOnSubscribe<String> {
                it.onNext("Hello,RxJava")
                it.onComplete()
                Log.d("subscribe", "发布线程：${Thread.currentThread()}")
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("subscribe", "订阅线程：${Thread.currentThread()}")
                    Log.d("subscribe", "接收到消息：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_just.setOnClickListener {
            /**
             * subscribeActual 方法： FromArrayDisposable 实现的，和 fromArray 一致
             */
            Observable.just("1", "2", "3").subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_from.setOnClickListener {
            /**
             * 同上
             */
            Observable.fromArray("1", "2", "3").subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_defer.setOnClickListener {
            /**
             * 和create 区别就是这里返回的是 ObservableSource,并且是订阅后才会创建这个 ObservableSource
             */
            Observable.defer(Callable<ObservableSource<String>> {
                return@Callable Observable.just("Hello,defer")
            }).subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_range.setOnClickListener {
            /**
             * ObservableRange
             */
            Observable.range(1,5).subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_empty.setOnClickListener {
            /**
             * ObservableEmpty
             */
            Observable.empty<String>().subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_error.setOnClickListener {
            /**
             * Functions.justCallable
             */
            Observable.error<String>(NullPointerException("error")).subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_never.setOnClickListener {
            /**
             * ObservableNever
             */
            Observable.never<String>().subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_time.setOnClickListener {
            /**
             * ScheduledThreadPoolExecutor 实现的
             */
            Observable.timer(5,TimeUnit.SECONDS).subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
        btn_interval.setOnClickListener {
            /**
             * ScheduledThreadPoolExecutor 实现的
             */
            Observable.interval(5,TimeUnit.SECONDS).subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
        }
    }
}