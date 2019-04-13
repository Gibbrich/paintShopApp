package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.github.gibbrich.paintfactory.dto.CustomerDetailParams
import com.github.gibbrich.paintfactory.utils.ListChangeType
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CustomersUseCaseTest {
    private lateinit var customerRepository: CustomerRespository
    private lateinit var customersUseCase: CustomersUseCase

    @Before
    fun setUp() {
        customerRepository = mock()
        customersUseCase = CustomersUseCase(customerRepository)
    }

    @Test
    fun `addCustomer returns list with ChangeCustomerList and SwitchToCustomerDetailScreen`() {
        val customerId = 42
        whenever(customerRepository.addCustomer(any())).thenReturn(customerId)

        val action1 = CustomersUseCase.Action.ChangeCustomerList(customerId, ListChangeType.ADD)
        val action2 =
            CustomersUseCase.Action.SwitchToCustomerDetailScreen(CustomerDetailParams(customerId))
        val expected = listOf(action1, action2)
        val result = customersUseCase.addCustomer()

        assertEquals(expected, result)
    }

    @Test
    fun `deleteCustomer returns Action#ChangeCustomerList`() {
        val customerId = 42
        val expected = CustomersUseCase.Action.ChangeCustomerList(customerId, ListChangeType.REMOVE)
        val result = customersUseCase.deleteCustomer(customerId)

        assertEquals(expected, result)
    }
}