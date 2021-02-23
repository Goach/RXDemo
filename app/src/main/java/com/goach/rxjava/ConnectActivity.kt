package com.goach.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Flowable
import io.reactivex.observables.ConnectableObservable
import kotlinx.android.synthetic.main.activity_connect.*

class ConnectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)
        title = intent.getStringExtra("title")
        btn_connect.setOnClickListener {
            /**
             * 只有调用了connect才会发射数据
             */
            val connectableObservable = ConnectableObservable.fromArray(1, 2, 3, 4, 5)
                .publish()
            connectableObservable.subscribe({
                Log.d("subscribe", "接收到消息：$it")
            }, {
                Log.d("subscribe", "onError")
            })
            connectableObservable.connect()
        }
        btn_replay.setOnClickListener {
            /**
             * Replay 操作符返回一个 ConnectableObservable 对象并且可以缓存其发射过的数据
             */
            val connectableObservable = ConnectableObservable.fromArray(1, 2, 3, 4, 5)
                .replay()
            connectableObservable.subscribe({
                Log.d("subscribe", "接收到消息：$it")
            }, {
                Log.d("subscribe", "onError")
            })
            connectableObservable.connect()
        }
        btn_refCount.setOnClickListener {
            /**
             * RefCount 操作符把从一个 ConnectableObservable 连接和断开的过程自动化了。调用 RefCount，返回一个普通的 Observable。当第一个订阅者订阅这个 Observable 时，RefCount 连接到下层的可连接 Observable。RefCount 跟踪有多少个观察者订阅它，直到最后一个观察者完成才断开与下层可连接 Observable 的连接。
             */
            ConnectableObservable.fromArray(1, 2, 3, 4, 5)
                .replay().refCount().subscribe({
                    Log.d("subscribe", "接收到消息：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
    }
}