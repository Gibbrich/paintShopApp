package com.github.gibbrich.paintfactory.domain.repository

import com.github.gibbrich.paintfactory.domain.models.Color

interface ColorsRepository {
    /**
     *  Unoptimal operation, as it will look through whole colors list, which can affect performance
     *  on large data scale. Unfortunately, other operations rely on random access,
     *  and they will be called more often, so this trade-off is acceptable.
     *
     *  @return position of the [color] in the list
     */
    fun getColorId(color: Color): Int

    /**
     * Add color to the list.
     * @return last index of added [color], if there is no such [Color]; null otherwise
     */
    fun addColor(color: Color): Int?

    /**
     * Remove color from the list, based in it position.
     * @return index of removed [Color]
     */
    fun removeColor(colorId: Int)

    /**
     * @return all colors, user selected.
     */
    fun getColors(): List<Color>
}