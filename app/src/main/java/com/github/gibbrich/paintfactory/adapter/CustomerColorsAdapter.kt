package com.github.gibbrich.paintfactory.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.utils.colorToHex
import kotlinx.android.synthetic.main.customer_color_item_layout.view.*

class CustomerColorsAdapter(
    items: List<Color>,
    private val customerWishlist: Map<Color, ColorType>,
    private val onUpdateColorInfo: (isMatte: Boolean, isInWishList: Boolean, item: Color) -> Unit
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
        holder.wishlistCheckBox.isChecked = item in customerWishlist
        holder.wishlistCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked.not()) {
                holder.matteCheckBox.isChecked = false
            }
            onUpdateColorInfo.invoke(holder.matteCheckBox.isChecked, isChecked, item)
        }

        holder.colorPreview.setBackgroundColor(item.value)
        holder.titleLabel.text = item.value.colorToHex()
        holder.matteCheckBox.isChecked = customerWishlist.get(item)?.let { it == ColorType.MATTE } ?: false
        holder.matteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.wishlistCheckBox.isChecked = true
                onUpdateColorInfo.invoke(isChecked, true, item)
                notifyDataSetChanged()
            } else {
                onUpdateColorInfo.invoke(isChecked, holder.wishlistCheckBox.isChecked, item)
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