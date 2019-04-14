package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.models.Customer
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.github.gibbrich.paintfactory.dto.CustomerDetailParams
import com.github.gibbrich.paintfactory.utils.ListChangeType

class CustomersUseCase(private val customerRepository: CustomerRespository) {

    /**
     * Add new [Customer] to the list.
     * @return list of actions, which should be applied to UI ([Action.ChangeCustomerList] and [Action.SwitchToCustomerDetailScreen])
     */
    fun addCustomer(): List<Action> {
        val customer = Customer()
        val customerId = customerRepository.addCustomer(customer)

        return listOf(
            Action.ChangeCustomerList(customerId, ListChangeType.ADD),
            configureCustomerWishlist(customerId)
        )
    }

    fun pickColors(): Action.SwitchToColorCalculationScreen = Action.SwitchToColorCalculationScreen

    fun getCustomers() = customerRepository.getCustomers()

    fun configureCustomerWishlist(customerId: Int): Action.SwitchToCustomerDetailScreen {
        val params = CustomerDetailParams(customerId)
        return Action.SwitchToCustomerDetailScreen(params)
    }

    /**
     * Delete customer from the list.
     */
    fun deleteCustomer(customerId: Int): Action.ChangeCustomerList {
        customerRepository.deleteCustomer(customerId)
        return Action.ChangeCustomerList(customerId, ListChangeType.REMOVE)
    }

    sealed class Action {
        data class SwitchToCustomerDetailScreen(val params: CustomerDetailParams) : Action()
        object SwitchToColorCalculationScreen : Action()
        data class ChangeCustomerList(
            val customerId: Int,
            val changeType: ListChangeType
        ) : Action()
    }
}