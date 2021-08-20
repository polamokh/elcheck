package me.polamokh.elcheck.ui.valuesadded

import androidx.lifecycle.LiveData
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
class ValuesAddedViewModel @Inject constructor(
    private val valueAddedDao: ValueAddedDao,
    private val state: SavedStateHandle
) : ViewModel() {

    val valuesAdded: LiveData<List<ValueAdded>> =
        valueAddedDao.getValuesAddedByOrderId(state.get<Long>("orderId")!!)

    fun deleteValueAdded(valueAdded: ValueAdded) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                valueAddedDao.deleteValuesAdded(valueAdded)
            }
        }
    }
}