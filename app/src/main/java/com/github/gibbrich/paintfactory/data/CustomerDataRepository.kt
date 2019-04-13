package com.github.gibbrich.paintfactory.data

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.models.Customer
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository

class CustomerDataRepository: CustomerRespository {
    private val customers: MutableList<Customer> = mutableListOf()

    override fun addCustomer(customer: Customer): Int {
        customers.add(customer)
        return customers.lastIndex
    }

    override fun getCustomers(): List<Customer> {
        return customers
    }

    override fun getCustomerWishList(customerId: Int): MutableMap<Color, ColorType> {
        return customers[customerId].wishList
    }

    override fun deleteCustomer(customerId: Int) {
        customers.removeAt(customerId)
    }
}