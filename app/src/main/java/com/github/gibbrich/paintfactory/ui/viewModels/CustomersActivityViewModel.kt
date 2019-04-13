package com.github.gibbrich.paintfactory.ui.viewModels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.gibbrich.paintfactory.di.Injector
import com.github.gibbrich.paintfactory.domain.usecase.CustomersUseCase
import com.github.gibbrich.paintfactory.domain.usecase.CustomersUseCase.Action
import javax.inject.Inject

class CustomersActivityViewModel : ViewModel() {

    @Inject
    internal lateinit var customersUseCase: CustomersUseCase

    val actions = MutableLiveData<Action>()

    init {
        Injector.componentManager.customersComponent.inject(this)
    }

    fun onAddCustomerButtonClicked() {
        customersUseCase.addCustomer().forEach(actions::setValue)
    }

    fun onPickColorsButtonClicked() {
        actions.value = customersUseCase.pickColors()
    }

    fun onCustomerClicked(customerId: Int) {
        actions.value = customersUseCase.configureCustomerWishlist(customerId)
    }

    fun onCustomerDeleted(customerId: Int) {
        actions.value = customersUseCase.deleteCustomer(customerId)
    }

    fun getCustomers() = customersUseCase.getCustomers()
}