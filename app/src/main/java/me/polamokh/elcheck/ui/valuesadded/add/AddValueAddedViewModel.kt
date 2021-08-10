package me.polamokh.elcheck.ui.valuesadded.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.valueadded.ValueAdded
import me.polamokh.elcheck.data.local.valueadded.ValueAddedDao
import javax.inject.Inject

@HiltViewModel
class AddValueAddedViewModel @Inject constructor(
    private val valueAddedDao: ValueAddedDao,
    private val state: SavedStateHandle
) : ViewModel() {

    private val valueAddedValue = MutableLiveData(0.0)

    private val valueAddedType = MutableLiveData(true)

    fun setValueAddedValue(value: Double) {
        valueAddedValue.value = value
    }

    fun setValueAddedType(isPercentage: Boolean) {
        valueAddedType.value = isPercentage
    }

    fun saveValueAdded() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                valueAddedDao.insertValuesAdded(
                    ValueAdded(
                        isPercentage = valueAddedType.value!!,
                        value = valueAddedValue.value!!,
                        orderId = state.get<Long>("orderId")!!
                    )
                )
            }
        }
    }
}