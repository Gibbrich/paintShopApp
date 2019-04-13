package com.github.gibbrich.paintfactory.domain.models

data class Customer(
    val wishList: MutableMap<Color, ColorType> = mutableMapOf()
)