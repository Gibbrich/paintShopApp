package com.github.gibbrich.paintfactory.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.data.ColorsRepository
import com.github.gibbrich.paintfactory.data.CustomerRepository
import com.github.gibbrich.paintfactory.domain.Color
import com.github.gibbrich.paintfactory.domain.ColorType
import javax.inject.Inject
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class CustomerDetailViewModel(
    private val customerId: Int,
    app: Application
) : AndroidViewModel(app) {

    @Inject
    lateinit var customerRepository: CustomerRepository

    @Inject
    lateinit var colorsRepository: ColorsRepository

    init {
        getApplication<PaintShopApp>().appComponent.inject(this)
    }

    fun getCustomerWishlist() = customerRepository.customers[customerId].wishList

    fun getColors() = colorsRepository.colors

    fun onAddToWishListCkeckboxClicked(
        isChecked: Boolean,
        item: Color,
        isMatteChecked: Boolean
    ) {
        val wishList = getCustomerWishlist()
        if (isChecked) {
            val colorType = if (isMatteChecked) ColorType.MATTE else ColorType.GLOSSY
            wishList.put(item, colorType)
        } else {
            wishList.remove(item)
        }
    }

    fun onIsMatteCheckboxClicked(
        isChecked: Boolean,
        item: Color
    ) {
        val wishList = getCustomerWishlist()
        if (isChecked) {
            wishList.put(item, ColorType.MATTE)

            for (customerColor in wishList) {
                if (customerColor.key != item) {
                    wishList[customerColor.key] = ColorType.GLOSSY
                }
            }
        } else {
            if (item in wishList) {
                wishList.put(item, ColorType.GLOSSY)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CustomerDetailModelFactory(
    private val mApplication: Application,
    private val customerId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomerDetailViewModel(customerId, mApplication) as T
    }
}