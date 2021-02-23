package com.goach.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_filter.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        title = intent.getStringExtra("title")
        btn_filter.setOnClickListener {
            Observable.fromArray(1, 2, 3, 4, 5)
                .filter { return@filter it % 3 == 0 }
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_take_last.setOnClickListener {
            /**
             * 取最后的几个元素
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .takeLast(2)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_last.setOnClickListener {
            /**
             * 取最后一个元素，默认是3
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .last(3)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_last_or_error.setOnClickListener {
            /**
             * 取最后一个元素
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .lastOrError()
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_skip.setOnClickListener {
            /**
             *跳过从开始的多少个元素
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_skip_last.setOnClickListener {
            /**
             *从结尾跳过多少个元素
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .skipLast(2)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_take.setOnClickListener {
            /**
             *发送多少次
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .take(2)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_first.setOnClickListener {
            /**
             *取第一个元素
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .first(2)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_first_or_error.setOnClickListener {
            /**
             *取第一个元素或者抛出异常
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .firstOrError()
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_element_at.setOnClickListener {
            /**
             *取第几个元素
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .elementAt(2)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_element_or_error.setOnClickListener {
            /**
             *取第几个元素
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .elementAtOrError(2)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                })
        }
        btn_sample.setOnClickListener {
            /**
             *间隔固定时间进行采样
             */
            Observable.interval(1, TimeUnit.SECONDS)
                .sample(2, TimeUnit.SECONDS)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_throttle_last.setOnClickListener {
            /**
             *throttleLast 间隔固定时间取最后一个发射元素 throttleFirst 则是间隔时间内取第一个发射元素，其他抛弃
             */
            Observable.interval(1, TimeUnit.SECONDS)
                .throttleLast(2, TimeUnit.SECONDS)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_debouce.setOnClickListener {
            /**
             *当一个事件发送出来之后，在约定时间内没有再次发送这个事件，则发射这个事件，如果再次触发了，则重新计算时间。
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_timeout.setOnClickListener {
            /**
             *每当原始Observable发射了一项数据，computation调度器就启动一个计时器，如果计时器超过了指定指定的时长而原始Observable没有发射另一项数据，timeout就抛出 TimeoutException，以一个错误通知终止Observable。
             */
            Observable.create(ObservableOnSubscribe<Int> {
                try {
                    it.onNext(1)
                    Thread.sleep(100)
                    it.onNext(2)
                    Thread.sleep(200)
                    it.onNext(3)
                    Thread.sleep(600)
                    it.onNext(4)
                    Thread.sleep(600)
                    it.onNext(5)
                } catch (e: Exception) {
                    it.tryOnError(e)
                }
                it.onComplete()

            })
                .subscribeOn(Schedulers.newThread())
                .timeout(100, TimeUnit.MILLISECONDS)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_distinct.setOnClickListener {
            /**
             * 去重，只允许还没有发射过的数据项通过。
             */
            Observable.fromArray(1,1,2,3,4,5,4,6)
                .distinct()
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_ofType.setOnClickListener {
            /**
             * 过滤不属于这个类型的元素
             */
            Observable.fromArray(1,1,2,3,"4",5,4,6)
                .ofType(String::class.java)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                }, {
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_ignoreElements.setOnClickListener {
            /**
             * 忽略所有源Observable产生的结果，只会执行onCpmpleted()或者onError()方法
             */
            Observable.fromArray(1,1,2,3,4,5,4,6)
                .ignoreElements()
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：${it.context}")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                })
        }
    }
}