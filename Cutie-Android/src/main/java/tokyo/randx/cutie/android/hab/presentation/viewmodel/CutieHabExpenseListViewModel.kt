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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tokyo.randx.cutie.android.hab.domain.CutieHabExpenseGetUseCase
import tokyo.randx.cutie.android.hab.presentation.component.CutieHabExpenseOverviewUiState
import tokyo.randx.cutie.android.hab.util.CutieHabResUtil
import tokyo.randx.cutie.android.util.CutieDateTimeProvider
import tokyo.randx.cutie.hab.CutieHabUser
import tokyo.randx.cutie.hab.CutieHabRecord
import tokyo.randx.cutie.hab.CutieHabUtil
import tokyo.randx.cutie.util.CutieConstant.MONTH
import tokyo.randx.cutie.util.CutieConstant.YEAR
import tokyo.randx.cutie.util.CutieDateTimeUtil
import java.text.NumberFormat
import javax.inject.Inject

@HiltViewModel
class CutieHabExpenseListViewModel @Inject constructor(
    private val useCase: CutieHabExpenseGetUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CutieHabExpenseListUiState())
    val uiState: StateFlow<CutieHabExpenseListUiState> get() = _uiState

    private val _uiStateOverview = MutableStateFlow(CutieHabExpenseOverviewUiState())
    val uiStateOverview: StateFlow<CutieHabExpenseOverviewUiState> get() = _uiStateOverview

    private val _users = MutableStateFlow(listOf(CutieHabUser()))
    private val users: StateFlow<List<CutieHabUser>> get() = _users

    init {
        getRecord()
        getUserList()
    }

    private fun getRecord() {
        val datetime = CutieDateTimeProvider.getDatetime()
        getRecord(datetime[YEAR], datetime[MONTH])
    }

    private fun getUserList() = viewModelScope.launch {
        useCase.getUserList().distinctUntilChanged().collect { users ->
            val currentUser = users[0].name
            _uiState.update { currentState ->
                currentState.copy(
                    currentUser = currentUser,
                    userList = users.map { it.name }
                )
            }

            _uiStateOverview.update { currentState ->
                currentState.copy(
                    userList = users.map { it.name },
                )
            }

            _users.value = users
        }
    }

    fun filterByUser(name: String) {
        val userId = CutieHabUtil.getUserId(name, users.value)

        _uiState.update { currentState ->
            currentState.copy(
                currentUser = name,
                expensesFilteredUiState = useCase.filterByUser(
                    uiState.value.expensesUiState.asModel(),
                    userId
                ).asUiState()
            )
        }
    }

    private fun List<CutieHabExpenseUiState>.asModel(): List<CutieHabRecord> = map {
        CutieHabRecord(
            id = it.expense.id,
            date = it.expense.date,
            time = it.expense.time,
            categoryId = it.expense.categoryId,
            uid = it.expense.uid,
            paymentMethodId = it.expense.paymentMethodId,
            amount = it.expense.amount,
            memo = it.expense.memo
        )
    }

    private fun List<CutieHabRecord>.asUiState(): List<CutieHabExpenseUiState> = map {
        CutieHabExpenseUiState(
            expense = it,
            categoryResId = CutieHabResUtil.getCategoryDrawableResId(it.categoryId),
            userResId = CutieHabResUtil.getUserDrawableResId(it.uid),
            paymentMethodResId = CutieHabResUtil.getPaymentMethodDrawableResId(it.paymentMethodId),
            formattedAmount = NumberFormat.getInstance().format(it.amount)
        )
    }

    fun getRecord(year: Int, month: Int) = viewModelScope.launch {
        val date = CutieDateTimeUtil.formatDate(year, month)
        //Get From Remote
        getRemoteRecord(date)
        useCase.getByDate(date).distinctUntilChanged().collect { expenses ->
            _uiState.update { currentState ->
                currentState.copy(
                    currentDate = date,
                    datetime = listOf(year, month),
                    expensesUiState = expenses.asUiState(),
                    expensesFilteredUiState = useCase.filterByUser(expenses, 0).asUiState()
                )
            }

            _uiStateOverview.update { currentState ->
                currentState.copy(
                    totalAmount = NumberFormat.getNumberInstance()
                        .format(useCase.getTotalAmount(expenses)),
                    formattedTotalAmountList = useCase.getTotalAmountList(expenses)
                        .map { NumberFormat.getNumberInstance().format(it) },
                )
            }
        }
    }

    private fun getRemoteRecord(date: String) = viewModelScope.launch {
        useCase.getRemoteByDate(date)
    }
}

data class CutieHabExpenseListUiState(
    val currentDate: String = String(),
    val datetime: List<Int> = listOf(1970, 1),
    val currentUser: String = String(),
    val userList: List<String> = emptyList(),
    val expensesUiState: List<CutieHabExpenseUiState> = emptyList(),
    val expensesFilteredUiState: List<CutieHabExpenseUiState> = emptyList()
)

data class CutieHabExpenseUiState(
    val expense: CutieHabRecord = CutieHabRecord(),
    val categoryResId: Int = 0,
    val userResId: Int = 0,
    val paymentMethodResId: Int = 0,
    val formattedAmount: String = String()
)
