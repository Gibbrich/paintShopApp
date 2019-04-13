package com.github.gibbrich.paintfactory.ui.viewModels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.gibbrich.paintfactory.di.Injector
import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.usecase.CustomerDetailUseCase
import javax.inject.Inject

class CustomerDetailViewModel(
    private val customerId: Int
) : ViewModel() {

    @Inject
    internal lateinit var customerDetailUseCase: CustomerDetailUseCase

    init {
        Injector.componentManager.customerDetailComponent.inject(this)
    }

    fun getCustomerWishlist(): Map<Color, ColorType> = customerDetailUseCase.getCustomerWishlist(customerId)

    fun getColors() = customerDetailUseCase.getColors()

    fun onAddToWishListCkeckboxClicked(
        isChecked: Boolean,
        item: Color,
        isMatteChecked: Boolean
    ) = customerDetailUseCase.addColorToWishlist(customerId, isChecked, item, isMatteChecked)

    fun onIsMatteCheckboxClicked(
        isChecked: Boolean,
        item: Color
    ) = customerDetailUseCase.changeMatteColors(customerId, isChecked, item)
}

@Suppress("UNCHECKED_CAST")
class CustomerDetailModelFactory(
    private val customerId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomerDetailViewModel(customerId) as T
    }
}