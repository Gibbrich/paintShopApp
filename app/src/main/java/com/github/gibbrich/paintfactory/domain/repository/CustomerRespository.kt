package com.github.gibbrich.paintfactory.domain.repository

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.models.Customer

interface CustomerRespository {
    /**
     * Add [customer] to the list.
     * @return position of added [Customer]
     */
    fun addCustomer(customer: Customer): Int

    /**
     * List of all customers, that user created.
     */
    fun getCustomers(): List<Customer>

    /**
     * Return [Customer.wishList], based on its position in the list.
     */
    fun getCustomerWishList(customerId: Int): MutableMap<Color, ColorType>

    /**
     * Delete [Customer] from the list, based on its position.
     */
    fun deleteCustomer(customerId: Int)
}