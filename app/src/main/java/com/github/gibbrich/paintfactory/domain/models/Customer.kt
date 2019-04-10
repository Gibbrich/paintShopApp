package com.github.gibbrich.paintfactory.domain.models

data class Customer(
    // todo - change to List<Pair<Color, ColorType>> for easier inegration with library
    val wishList: MutableMap<Color, ColorType> = mutableMapOf()
)