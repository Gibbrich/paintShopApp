package com.github.gibbrich.paintfactory.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.data.CustomerRepository
import com.github.gibbrich.paintfactory.domain.Customer
import com.github.gibbrich.paintfactory.dto.CustomerDetailParams
import com.github.gibbrich.paintfactory.utils.ListChangeType
import javax.inject.Inject

class CustomersActivityViewModel(app: Application) : AndroidViewModel(app) {

    @Inject
    internal lateinit var customerRepository: CustomerRepository
    val actions = MutableLiveData<Action>()

    init {
        getApplication<PaintShopApp>().appComponent.inject(this)
    }

    fun onAddCustomerButtonClicked() {
        val customer = Customer()
        customerRepository.customers.add(customer)

        actions.value = Action.ChangeCustomerList(customerRepository.customers.lastIndex, ListChangeType.ADD)

        setSwitchToCustomerDetailScreenAction(customerRepository.customers.lastIndex)
    }

    fun onPickColorsButtonClicked() {
        actions.value = Action.SwitchToColorCalculationScreen
    }

    fun onCustomerClicked(customerId: Int) = setSwitchToCustomerDetailScreenAction(customerId)

    private fun setSwitchToCustomerDetailScreenAction(customerId: Int) {
        val params = CustomerDetailParams(customerId)
        actions.value = Action.SwitchToCustomerDetailScreen(params)
    }

    fun getCustomers() = customerRepository.customers

    sealed class Action {
        data class SwitchToCustomerDetailScreen(val params: CustomerDetailParams) : Action()
        object SwitchToColorCalculationScreen : Action()
        data class ChangeCustomerList(
            val customerId: Int,
            val changeType: ListChangeType
        ) : Action()
    }
}