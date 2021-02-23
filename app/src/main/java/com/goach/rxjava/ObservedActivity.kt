package com.goach.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_observed.*

class ObservedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observed)
        title = intent.getStringExtra("title")
        btn_observer.setOnClickListener {
            /**
             * Observable 和 observer 模式，能够发送1或n个数据，并以 onError 或 onComplete 结束
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .subscribe({
                    Log.d("subscribe", "接收到消息：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_flowable.setOnClickListener {
            /**
             * Flowable 对应 Subscription ，和 observable 的区别就是它支持背压
             *
             * BackpressureStrategy.ERROR 直接抛出异常MissingBackpressureException
             * BackpressureStrategy.MISSING 友好提示：缓存区满了
             * BackpressureStrategy.BUFFER 将缓存区大小设置成无限大
             * BackpressureStrategy.DROP 超过缓存区大小（128）的事件丢弃
             * BackpressureStrategy.LATEST 只保存最新（最后）事件，超过缓存区大小（128）的事件丢弃
             */
            Flowable.create(FlowableOnSubscribe<String> {
               for (i in 0..1500){
                   Thread.sleep(100)
                   it.onNext("Hello , Flowable")
               }
                it.onComplete()
            },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    Thread.sleep(1000)
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError:${it.message}")
                })
        }
        btn_single.setOnClickListener {
            /**
             * Single 和 SingleObserver 对应只能发送单个数据，只有 onError 和 onSuccess 数据
             */
            Single.just(1)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_completable.setOnClickListener {
            /**
             * 不发送数据只有onComplete方法和onError方法
             */
            Completable.create {
                it.onComplete()
            }
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
        btn_maybe.setOnClickListener {
            /**
             * 发送0或者1个数据，只有onSuccess方法和onError方法
             */
            Maybe.just(1)
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
    }
}