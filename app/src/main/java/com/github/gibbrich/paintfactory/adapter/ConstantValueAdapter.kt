package com.github.gibbrich.paintfactory.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class ConstantValueAdapter<T, VH : RecyclerView.ViewHolder>(
    private val items: MutableList<T>
) : RecyclerView.Adapter<VH>() {

    final override fun getItemCount() = items.size

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createHolder(LayoutInflater.from(parent.context).inflate(lineResourceId, parent, false))
    }

    abstract fun createHolder(view: View): VH

    abstract val lineResourceId: Int

    override fun onBindViewHolder(holder: VH, position: Int) {
        bind(holder, items[position],position)
    }

    abstract fun bind(holder: VH, item: T, position: Int)

    fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    fun replaceData(data: List<T>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}