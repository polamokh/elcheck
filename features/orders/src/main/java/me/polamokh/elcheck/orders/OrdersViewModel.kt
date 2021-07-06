package me.polamokh.elcheck.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.order.Order
import me.polamokh.elcheck.data.local.order.OrderDao
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val orderDao: OrderDao) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _orders.postValue(orderDao.getOrders())
            }
        }
    }

    fun addOrder() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                orderDao.insertOrders(Order(0, "Title", "2021-07-07"))
                _orders.postValue(orderDao.getOrders())
            }
        }
    }
}