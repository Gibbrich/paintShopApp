package com.github.gibbrich.paintfactory.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.data.CustomerDataRepository
import com.github.gibbrich.paintfactory.domain.models.Customer
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespoitory
import com.github.gibbrich.paintfactory.dto.CustomerDetailParams
import com.github.gibbrich.paintfactory.utils.ListChangeType
import javax.inject.Inject

class CustomersActivityViewModel(app: Application) : AndroidViewModel(app) {

    @Inject
    internal lateinit var customerRepository: CustomerRespoitory
    val actions = MutableLiveData<Action>()

    init {
        getApplication<PaintShopApp>().appComponent.inject(this)
    }

    fun onAddCustomerButtonClicked() {
        val customer = Customer()
        val customerId = customerRepository.addCustomer(customer)

        actions.value = Action.ChangeCustomerList(customerId, ListChangeType.ADD)

        setSwitchToCustomerDetailScreenAction(customerId)
    }

    fun onPickColorsButtonClicked() {
        actions.value = Action.SwitchToColorCalculationScreen
    }

    fun onCustomerClicked(customerId: Int) = setSwitchToCustomerDetailScreenAction(customerId)

    fun getCustomers() = customerRepository.getCustomers()

    fun onCustomerDeleted(customerId: Int) {
        customerRepository.deleteCustomer(customerId)
        actions.value = Action.ChangeCustomerList(customerId, ListChangeType.REMOVE)
    }

    private fun setSwitchToCustomerDetailScreenAction(customerId: Int) {
        val params = CustomerDetailParams(customerId)
        actions.value = Action.SwitchToCustomerDetailScreen(params)
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