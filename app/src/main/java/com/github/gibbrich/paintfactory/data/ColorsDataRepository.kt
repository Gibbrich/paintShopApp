package com.github.gibbrich.paintfactory.data

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository

class ColorsDataRepository : ColorsRepository {
    private val colors: MutableList<Color> = mutableListOf()

    override fun getColorId(color: Color): Int = colors.indexOf(color)

    override fun addColor(color: Color): Int? =
        if (color !in colors) {
            colors.add(color)
            colors.lastIndex
        } else {
            null
        }

    override fun removeColor(colorId: Int) {
        colors.removeAt(colorId)
    }

    override fun getColors(): List<Color> = colors
}