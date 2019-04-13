package com.github.gibbrich.paintfactory.domain.repository

import com.github.gibbrich.paintfactory.domain.models.Color

interface ColorsRepository {
    /**
     *  Unoptimal operation, as it will look through whole colors list, which can affect performance
     *  on large data scale. Unfortunately, other operations rely on random access,
     *  and they will be called more often, so this trade-off is acceptable.
     */
    fun getColorId(color: Color): Int
    fun addColor(color: Color): Int?
    fun removeColor(colorId: Int)
    fun getColors(): List<Color>
}