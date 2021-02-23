package com.goach.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RXLifeCycleActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxlifecycle)
        Log.d("subscribe", "onCreate()")
        title = intent.getStringExtra("title")
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        Log.d("subscribe", "onStart()")
        Observable.interval(2, TimeUnit.SECONDS)
            .doOnDispose {
                Log.e("subscribe", "bindUntilEvent 解除了订阅")
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(bindUntilEvent(ActivityEvent.STOP))
            .subscribe({
                Log.d("subscribe", "onNext$it")
            },{
                Log.d("subscribe", "onError")
            },{
                Log.d("subscribe", "onComplete")
            })
    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()
        Log.d("subscribe", "onResume()")
        Observable.interval(2, TimeUnit.SECONDS)
            .doOnDispose {
                Log.e("subscribe", "bindToLifecycle 解除了订阅")
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(bindToLifecycle())
            .subscribe({
                Log.d("subscribe", "onNext$it")
            },{
                Log.d("subscribe", "onError")
            },{
                Log.d("subscribe", "onComplete")
            })
    }

    override fun onPause() {
        super.onPause()
        Log.d("subscribe", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("subscribe", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("subscribe", "onDestroy()")
    }
}