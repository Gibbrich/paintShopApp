package com.github.gibbrich.paintfactory.domain

data class Customer(
    val wishList: MutableMap<Color, ColorType> = mutableMapOf()
)