package me.polamokh.elcheck.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.order.Order
import me.polamokh.elcheck.data.local.order.OrderDao
import me.polamokh.elcheck.utils.getNowFormattedDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val orderDao: OrderDao) : ViewModel() {

    val orders = orderDao.getOrders()

    fun addOrder(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                orderDao.insertOrders(
                    Order(
                        name = name,
                        orderTime = Date().getNowFormattedDateTime()
                    )
                )
            }
        }
    }

    fun removeOrder(order: Order) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                orderDao.deleteOrders(order)
            }
        }
    }
}