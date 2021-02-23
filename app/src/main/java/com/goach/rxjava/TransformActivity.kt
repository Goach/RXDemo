package com.goach.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_transform.*
import java.util.concurrent.TimeUnit

class TransformActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transform)
        title = intent.getStringExtra("title")
        btn_map.setOnClickListener {
            Observable.just(1)
                .map { return@map it + 1 }
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_flat_map.setOnClickListener {
            Observable.fromArray(1, 2, 3, 4, 5)
                .flatMap {
                    if (it == 2) {
                        return@flatMap Observable.just(it * 10).delay(1, TimeUnit.SECONDS)
                    }
                    return@flatMap Observable.just(it * 10)
                }
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_concat.setOnClickListener {
            /**
             * 和 flatMap 比较这个是有序的
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .concatMap {
                    if (it == 2) {
                        return@concatMap Observable.just(it * 10).delay(1, TimeUnit.SECONDS)
                    }
                    return@concatMap Observable.just(it * 10)
                }
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_flat_map_iterable.setOnClickListener {
            /**
             * 将数据转换为 Iterable
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .flatMapIterable(Function<Int, Iterable<Int>> {
                    return@Function arrayListOf(it + 1)
                })
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_switch_map.setOnClickListener {
            /**
             *switchMap 最近一个发射源和创建完后的发射源
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .switchMap(Function<Int, ObservableSource<Int>> {
                    return@Function Observable.just(it + 1).delay(6,TimeUnit.SECONDS)
                })
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_scan.setOnClickListener {
            /**
             *将scan函数结果作为下一个t1发射，输出结果为
             * 1，3，6，10，15
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .scan { t1: Int, t2: Int ->
                    return@scan t1 + t2
                }
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_group_by.setOnClickListener {
            /**
             *分组，groupBy返回的是key
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .groupBy {
                    return@groupBy it % 3
                }
                .subscribe({
                    it.subscribe({ gb ->
                        Log.d("subscribe", "接收到消息onNext key:${it.key} value:$gb")
                    }, {
                        Log.d("subscribe", "onError gb")
                    }, {
                        Log.d("subscribe", "onComplete gb")
                    })
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_buffer.setOnClickListener {
            /**
             *数组分割
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .buffer(2)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_window.setOnClickListener {
            /**
             *和 buffer 的区别是以源的方式发布消息
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .window(2)
                .subscribe({
                    it.subscribe({ value ->
                        Log.d("subscribe", "接收到消息onNext value:$value")
                    }, {
                        Log.d("subscribe", "onError value")
                    }, {
                        Log.d("subscribe", "onComplete value")
                    })
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_cast.setOnClickListener {
            /**
             *类型校验，如下面就会报错
             */
            Observable.fromArray(1, 2, 3, 4, "5")
                .cast(Int::class.java)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
    }
}