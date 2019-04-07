package com.github.gibbrich.paintfactory.data

import com.github.gibbrich.paintfactory.domain.Customer

class CustomerRepository {
    val customers: MutableList<Customer> = mutableListOf()
}