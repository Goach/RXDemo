package com.goach.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.*
import kotlinx.android.synthetic.main.activity_subject.*
import java.util.concurrent.TimeUnit

/**
 * Subject 既可以做Observable 或者 Observer,并且它们是一个 Hot Observable
 */
class SubjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        title = intent.getStringExtra("title")
        btn_behavior_subject.setOnClickListener {
            /**
             * 订阅前一个和订阅后的消息
             */
            val subject: BehaviorSubject<String> = BehaviorSubject.create<String>()
            subject.onNext("Hello,Behavior")
            subject.onNext("Hello,Behavior Subject")
            subject.onNext("Hello,Behavior Subject User")
            subject.subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
            subject.onNext("behavior after")
            subject.onNext("behavior after 1")
            subject.onNext("behavior after 2")
            subject.onNext("behavior after 3")
            subject.subscribe(object : Subject<String>() {
                override fun onComplete() {
                    Log.d("subscribe", "Subject ---->onComplete")
                }

                override fun onError(e: Throwable) {
                    Log.d("subscribe", "Subject ---->onError")

                }

                override fun onNext(t: String) {
                    Log.d("subscribe", "Subject ---->onNext")

                }

                override fun hasThrowable(): Boolean {
                    return false
                }

                override fun hasObservers(): Boolean {
                    return false
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("subscribe", "Subject ---->onSubscribe")

                }

                override fun getThrowable(): Throwable? {
                    return null
                }

                override fun subscribeActual(observer: Observer<in String>?) {

                }

                override fun hasComplete(): Boolean {
                    return false
                }

            })
            subject.onComplete()
        }
        btn_publish.setOnClickListener {
            /**
             * 发送订阅后的数据,它和 BehaviorSubject 区别是 subscribe 不会执行订阅者方法，只会添加保存订阅者
             * 然后在 onNext 上面 for 循环执行
             */
            val publishSubject = PublishSubject.create<String>()
            publishSubject.onNext("Hello,Publish before 1")
            publishSubject.onNext("Hello,Publish before 2")
            publishSubject.subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
            publishSubject.onNext("Hello,Publish after 3")
            publishSubject.onNext("Hello,Publish after 4")
            publishSubject.onNext("Hello,Publish after 5")
            publishSubject.onComplete()
        }
        btn_async_subject.setOnClickListener {
            /**
             * 只发射最后一个数据，并且一定要调用 onComplete 才会执行
             */
            val asyncSubject = AsyncSubject.create<String>()
            asyncSubject.onNext("Hello,async before 1")
            asyncSubject.onNext("Hello,async before 2")
            asyncSubject.onNext("Hello,async before 3")
            asyncSubject.subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
            asyncSubject.onNext("Hello,async after 1")
            asyncSubject.onNext("Hello,async after 2")
            asyncSubject.onNext("Hello,async after 3")
            asyncSubject.onNext("Hello,async after 4")
            asyncSubject.onComplete()
        }
        btn_replay.setOnClickListener {
            /**
             * 所有消息都会发送
             */
            val replaySubject = ReplaySubject.create<String>()
            replaySubject.onNext("Hello,replay before 1")
            replaySubject.onNext("Hello,replay before 2")
            replaySubject.onNext("Hello,replay before 3")
            replaySubject.subscribe({
                Log.d("subscribe", "接收到消息onNext：$it")
            }, {
                Log.d("subscribe", "onError")
            }, {
                Log.d("subscribe", "onComplete")
            })
            replaySubject.onNext("Hello,replay after 1")
            replaySubject.onNext("Hello,replay after 2")
            replaySubject.onNext("Hello,replay after 3")
            replaySubject.onNext("Hello,replay after 4")
            replaySubject.onComplete()
        }
    }
}