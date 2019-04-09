package com.github.gibbrich.paintfactory.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.data.ColorsDataRepository
import com.github.gibbrich.paintfactory.data.CustomerDataRepository
import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import javax.inject.Inject
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespoitory

class CustomerDetailViewModel(
    private val customerId: Int,
    app: Application
) : AndroidViewModel(app) {

    @Inject
    lateinit var customerRepository: CustomerRespoitory

    @Inject
    lateinit var colorsRepository: ColorsRepository

    init {
        getApplication<PaintShopApp>().appComponent.inject(this)
    }

    fun getCustomerWishlist(): Map<Color, ColorType> = customerRepository.getCustomerWishList(customerId)

    fun getColors() = colorsRepository.getColors()

    fun onAddToWishListCkeckboxClicked(
        isChecked: Boolean,
        item: Color,
        isMatteChecked: Boolean
    ) {
        val wishList = customerRepository.getCustomerWishList(customerId)
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
        val wishList = customerRepository.getCustomerWishList(customerId)
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