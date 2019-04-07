package com.github.gibbrich.paintfactory.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.domain.Color
import com.github.gibbrich.paintfactory.domain.ColorType
import kotlinx.android.synthetic.main.customer_color_item_layout.view.*

class CustomerColorsAdapter(
    items: MutableList<Color>,
    private val customerColors: MutableMap<Color, ColorType>
): ConstantValueAdapter<Color, CustomerColorsAdapter.Holder>(items) {
    override fun createHolder(view: View): Holder = Holder(
        wishlistCheckBox = view.is_in_wishlist_check_box,
        colorPreview = view.color_preview_image,
        titleLabel = view.color_hex_label,
        matteCheckBox = view.is_matte_check_box,
        view = view
    )

    override val lineResourceId = R.layout.customer_color_item_layout

    override fun bind(holder: Holder, item: Color, position: Int) {
        holder.wishlistCheckBox.isChecked = customerColors.contains(item)
        holder.wishlistCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val colorType = if (holder.matteCheckBox.isChecked) ColorType.MATTE else ColorType.GLOSSY
                customerColors.put(item, colorType)
            } else {
                customerColors.remove(item)
            }
        }

        holder.colorPreview.setBackgroundColor(item.value)
        holder.titleLabel.text = "#${item.value.toString(16).capitalize()}"
        holder.matteCheckBox.isChecked = customerColors.get(item)?.let { it == ColorType.MATTE } ?: false
        holder.matteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (customerColors.contains(item)) {
                    customerColors.put(item, ColorType.MATTE)
                }
            } else {
                if (customerColors.contains(item)) {
                    customerColors.put(item, ColorType.GLOSSY)
                }
            }
        }
    }

    class Holder(
        val wishlistCheckBox: CheckBox,
        val colorPreview: View,
        val titleLabel: TextView,
        val matteCheckBox: CheckBox,
        view: View
    ): RecyclerView.ViewHolder(view)
}