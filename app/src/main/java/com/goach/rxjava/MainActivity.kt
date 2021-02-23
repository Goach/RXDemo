package com.goach.rxjava

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_type.view.*

class MainActivity : AppCompatActivity() {
    private val mDataList: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDataList.add("创建操作符")
        mDataList.add("变换操作符")
        mDataList.add("过滤操作符")
        mDataList.add("条件操作符")
        mDataList.add("布尔操作符")
        mDataList.add("合并操作符")
        mDataList.add("连接操作符")
        mDataList.add("观察者模式")
        mDataList.add("各种Subject")
        mDataList.add("RXLifeCycle")
        rv_list.adapter = TypeAdapter(mDataList)
    }

    class TypeAdapter(private val dataList: MutableList<String>) :
        RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
            val viewHolder = TypeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_type, parent, false)
            )
            viewHolder.itemView.setOnClickListener {view ->
                when(val position = view.tag as? Int ?: -1){
                    0 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,CreateActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                    1 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,TransformActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                    2 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,FilterActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                    3 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,ConditionActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                    4 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,BooleanActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                    5 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,MergeActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                    6 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,ConnectActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                    7 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,ObservedActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                    8 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,SubjectActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                    9 -> {
                        viewHolder.itemView.context.startActivity(Intent(viewHolder.itemView.context,RXLifeCycleActivity::class.java).also {
                            it.putExtra("title",dataList[position])
                        })
                    }
                }
            }
            return viewHolder
        }

        override fun getItemCount(): Int = dataList.size

        override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
            holder.itemView.tv_title.text = dataList[position]
            holder.itemView.tag = position
        }

        inner class TypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

}