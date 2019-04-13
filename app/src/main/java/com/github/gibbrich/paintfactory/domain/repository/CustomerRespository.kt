package com.github.gibbrich.paintfactory.domain.repository

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.models.Customer

interface CustomerRespository {
    fun addCustomer(customer: Customer): Int
    fun getCustomers(): List<Customer>
    fun getCustomerWishList(customerId: Int): MutableMap<Color, ColorType>
    fun deleteCustomer(customerId: Int)
}