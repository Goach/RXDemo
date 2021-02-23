package com.goach.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.activity_merge.*
import java.util.concurrent.TimeUnit

class MergeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merge)
        title = intent.getStringExtra("title")
        btn_startWith.setOnClickListener {
            /**
             * 在开头插入元素
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .startWith(0)
                .startWith(arrayListOf(5, 6, 7))
                .subscribe({
                    Log.d("subscribe", "接收到消息：$it")
                }, {
                    Log.d("subscribe", "onError")
                },{
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_merge.setOnClickListener {
            /**
             * 合并操作符
             */
            Observable.fromArray(1, 2, 3, 4, 5)
                .mergeWith(Observable.fromArray(6, 7, 8, 9))
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                },{
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_merge_delay_error.setOnClickListener {
            /**
             * 合并操作符,和 merge 区别就是所有数据发射完毕后才执行onError
             */
            Observable.mergeDelayError(
                Observable.fromArray(1, 2, 3, 4, 5),
                Observable.fromArray(6, 7, 8)
            )
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                },{
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_zip.setOnClickListener {
            /**
             * 多个数据源操作符,并且为每一个数据源发送数据
             */
            Observable.zip(Observable.fromArray(1, 2, 3, 4, 5),
                Observable.fromArray(6, 7, 8), BiFunction<Int, Int, ArrayList<Int>> { t1, t2 ->
                    return@BiFunction arrayListOf(t1, t2)
                })
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：0-->${it[0]} 1-->${it[1]}")
                }, {
                    Log.d("subscribe", "onError")
                },{
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_join.setOnClickListener {
            /**
             * 源数据源和目标数据源的每个元素合并操作符,然后返回数据
             * Observable：源Observable需要组合的Observable,这里我们姑且称之为目标Observable；
             * Func1：接收从源Observable发射来的数据，并返回一个Observable，这个Observable的声明周期决定了源Obsrvable发射出来的数据的有效期；
             * Func1：接收目标Observable发射来的数据，并返回一个Observable，这个Observable的声明周期决定了目标Obsrvable发射出来的数据的有效期；
             * Func2：接收从源Observable和目标Observable发射出来的数据，并将这两个数据组合后返回。
             */
            Observable.just("Hello")
                .join(
                    Observable.fromArray(6, 7, 8, 9, 10),
                    Function<String, Observable<Long>> {
                        return@Function Observable.timer(3000, TimeUnit.MILLISECONDS)
                    },
                    Function<Int, Observable<Long>> {
                        return@Function Observable.timer(2000, TimeUnit.MILLISECONDS)
                    },
                    BiFunction<String, Int, String> { t1, t2 ->
                        return@BiFunction t1 + t2
                    })
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                },{
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_group_join.setOnClickListener {
            /**
             * 多个数据源操作符,并且为每一个数据源发送数据,数据源结合不一样
             */
            Observable.just("Hello")
                .groupJoin(
                    Observable.fromArray(6, 7, 8, 9, 10),
                    Function<String, Observable<Long>> {
                        return@Function Observable.timer(3000, TimeUnit.MILLISECONDS)
                    },
                    Function<Int, Observable<Long>> {
                        return@Function Observable.timer(2000, TimeUnit.MILLISECONDS)
                    },
                    BiFunction<String,Observable<Int>,Observable<String>> { t1, t2 ->
                        return@BiFunction t2.map {
                            return@map t1 + it
                        }
                    })
                .subscribe({
                    it.subscribe({value ->
                        Log.d("subscribe", "接收到消息onNext value：$value")
                    }, {
                        Log.d("subscribe", "onError value")
                    },{
                        Log.d("subscribe", "onComplete value")
                    })
                }, {
                    Log.d("subscribe", "onError")
                },{
                    Log.d("subscribe", "onComplete")
                })
        }
        btn_switch_on_next.setOnClickListener {
            /**
             * 发送新的元素
             */
            Observable.switchOnNext(Observable.fromArray(1, 2, 3, 4, 5).map {
                return@map Observable.fromArray(6,7,8,9)
            })
                .subscribe({
                    Log.d("subscribe", "接收到消息onNext：$it")
                }, {
                    Log.d("subscribe", "onError")
                })
        }
    }
}