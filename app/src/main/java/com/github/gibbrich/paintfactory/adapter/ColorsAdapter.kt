package com.github.gibbrich.paintfactory.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.gibbrich.paintfactory.domain.Color
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.domain.ColorType
import kotlinx.android.synthetic.main.color_item_layout.view.*

class ColorsAdapter(
    colors: MutableList<Color> = mutableListOf()
): ConstantValueAdapter<Color, ColorsAdapter.Holder>(colors) {
    override fun createHolder(view: View): Holder = Holder(
        colorPreview = view.color_preview_image,
        titleLabel = view.color_hex_label,
        view = view
    )

    override val lineResourceId = R.layout.color_item_layout

    override fun bind(holder: Holder, item: Color, position: Int) {
        holder.colorPreview.setBackgroundColor(item.value)
        holder.titleLabel.text = "#${item.value.toString(16).capitalize()}"
    }

    class Holder(
        val colorPreview: View,
        val titleLabel: TextView,
        view: View
    ): RecyclerView.ViewHolder(view)
}