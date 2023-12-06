/*
 * Copyright 2023 RandX <010and1001@gmail.com>
 *
 * This file is part of Cutie.
 *
 * Cutie is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Cutie is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with Cutie.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 */
package tokyo.randx.cutie.android.hab.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tokyo.randx.cutie.android.hab.domain.CutieHabExpenseCreateUpdateUseCase
import tokyo.randx.cutie.hab.CutieHabUser
import tokyo.randx.cutie.hab.CutieHabUtil
import tokyo.randx.cutie.util.CutieConstant.DAY
import tokyo.randx.cutie.util.CutieConstant.HOUR
import tokyo.randx.cutie.util.CutieConstant.MINUTE
import tokyo.randx.cutie.util.CutieConstant.MONTH
import tokyo.randx.cutie.util.CutieConstant.YEAR
import tokyo.randx.cutie.util.CutieDateTimeUtil
import javax.inject.Inject

@HiltViewModel
class CutieHabExpenseCreateUpdateViewModel @Inject constructor(
    private val useCase: CutieHabExpenseCreateUpdateUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CutieHabExpenseCreateUpdateUiState())
    val uiState: StateFlow<CutieHabExpenseCreateUpdateUiState> get() = _uiState

    private val _users = MutableStateFlow(listOf(CutieHabUser()))
    private val users: StateFlow<List<CutieHabUser>> get() = _users

    private val _userId = MutableStateFlow(0)
    private val userId: StateFlow<Int> get() = _userId

    init {
        initUiState()
    }

    private fun initUiState() = viewModelScope.launch {
        getUserList()
        val datetime = useCase.getDatetime()
        _uiState.update { currentState ->
            currentState.copy(
                date = CutieDateTimeUtil.formatDate(datetime[YEAR], datetime[MONTH], datetime[DAY]),
                time = CutieDateTimeUtil.formatTime(datetime[HOUR], datetime[MINUTE]),
                category = useCase.getCategoryList()[0],
                categoryList = useCase.getCategoryList(),
                paymentMethod = useCase.getPaymentMethodList()[0],
                paymentMethodList = useCase.getPaymentMethodList(),
                datetime = datetime
            )
        }
    }

    private fun getUserList() = viewModelScope.launch {
        useCase.getUserList().distinctUntilChanged().collect {
            _users.value = it

            _uiState.update { currentState ->
                currentState.copy(
                    user = CutieHabUtil.getUser(userId.value, it),
                    userList = users.value.map { user -> user.name }
                )
            }
        }
    }

    fun updateDate(year: Int, month: Int, day: Int) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                date = CutieDateTimeUtil.formatDate(year, month, day)
            )
        }
    }

    fun updateTime(hour: Int, minute: Int) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                time = CutieDateTimeUtil.formatTime(hour, minute)
            )
        }
    }

    fun updateCategory(category: String) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                category = category
            )
        }
    }

    fun updateUser(name: String) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                user = name
            )
        }
    }

    fun updatePaymentMethod(paymentMethod: String) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                paymentMethod = paymentMethod
            )
        }
    }

    fun updateAmount(amount: String) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                amountStr = amount
            )
        }
    }

    fun updateMemo(memo: String) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                memo = memo
            )
        }
    }

    fun createOrUpdateRecord() = viewModelScope.launch {
        val expense = uiState.value
        useCase.createOrUpdate(
            expense.recordId,
            expense.date,
            expense.time,
            expense.category,
            CutieHabUtil.getUserId(expense.user, users.value),
            expense.paymentMethod,
            expense.amountStr,
            expense.memo
        )
    }

    fun getRecord(id: String?) = CoroutineScope(Dispatchers.IO).launch() {
        val record = useCase.get(id)

        _uiState.update { currentState ->
            currentState.copy(
                recordId = record.id,
                date = record.date,
                time = record.time,
                category = useCase.getCategory(record.categoryId),
                user = CutieHabUtil.getUser(record.uid, users.value),
                paymentMethod = useCase.getPaymentMethod(record.paymentMethodId),
                amountStr = record.amount.toString(),
                memo = record.memo,
                isUpdate = record.id.isNotEmpty()
            )
        }

        _userId.value = record.uid
    }

    fun deleteRecord() = viewModelScope.launch {
        useCase.delete(uiState.value.recordId)
    }
}

data class CutieHabExpenseCreateUpdateUiState(
    val recordId: String = String(),
    val date: String = String(),
    val time: String = String(),
    val category: String = String(),
    val categoryList: List<String> = emptyList(),
    val user: String = String(),
    val userList: List<String> = emptyList(),
    val paymentMethod: String = String(),
    val paymentMethodList: List<String> = emptyList(),
    val amountStr: String = String(),
    val memo: String = String(),
    val datetime: List<Int> = listOf(1970, 1, 1, 0, 0),
    val isUpdate: Boolean = false
)
