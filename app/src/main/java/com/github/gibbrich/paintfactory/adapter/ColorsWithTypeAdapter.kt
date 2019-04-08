package com.github.gibbrich.paintfactory.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.domain.ColorType
import com.github.gibbrich.paintfactory.domain.ColorWithType
import kotlinx.android.synthetic.main.color_with_types_item_layout.view.*

class ColorsWithTypeAdapter(
    colors: MutableList<ColorWithType>
): ConstantValueAdapter<ColorWithType, ColorsWithTypeAdapter.Holder>(colors) {
    override fun createHolder(view: View): Holder = Holder(
        colorPreview = view.color_with_types_color_preview_image,
        titleLabel = view.color_with_types_color_hex_label,
        matteCheckBox = view.color_with_types_matte_checkbox,
        view = view
    )

    override val lineResourceId = R.layout.color_with_types_item_layout

    override fun bind(holder: Holder, item: ColorWithType, position: Int) {
        holder.colorPreview.setBackgroundColor(item.value)
        holder.titleLabel.text = "#${item.value.toString(16).capitalize()}"
        holder.matteCheckBox.isChecked = item.type == ColorType.MATTE
    }

    class Holder(
        val colorPreview: View,
        val titleLabel: TextView,
        val matteCheckBox: CheckBox,
        view: View
    ): RecyclerView.ViewHolder(view)
}