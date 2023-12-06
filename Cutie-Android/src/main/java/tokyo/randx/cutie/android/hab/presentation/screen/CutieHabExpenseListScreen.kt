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
package tokyo.randx.cutie.android.hab.presentation.screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Divider
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tokyo.randx.cutie.android.R
import tokyo.randx.cutie.android.hab.presentation.component.CutieHabExpenseItemView
import tokyo.randx.cutie.android.hab.presentation.component.CutieHabExpenseOverview
import tokyo.randx.cutie.android.hab.presentation.component.CutieHabExpenseOverviewUiState
import tokyo.randx.cutie.android.hab.presentation.viewmodel.CutieHabExpenseListUiState
import tokyo.randx.cutie.android.hab.presentation.viewmodel.CutieHabExpenseListViewModel
import tokyo.randx.cutie.android.hab.presentation.viewmodel.CutieHabExpenseUiState
import tokyo.randx.cutie.util.CutieConstant.MONTH
import tokyo.randx.cutie.util.CutieConstant.YEAR

@Composable
fun CutieHabExpenseListRoute(
    modifier: Modifier = Modifier,
    onItemClick: (CutieHabExpenseUiState) -> Unit,
    viewModel: CutieHabExpenseListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiStateOverview by viewModel.uiStateOverview.collectAsStateWithLifecycle()

    CutieHabExpenseListScreen(
        modifier = modifier,
        uiState = uiState,
        uiStateOverview = uiStateOverview,
        onItemClick = onItemClick,
        onDateFilterChange = { year, month ->
            viewModel.getRecord(year, month)
        },
        onUserFilterChange = { viewModel.filterByUser(it) }
    )
}

@Composable
internal fun CutieHabExpenseListScreen(
    modifier: Modifier = Modifier,
    uiState: CutieHabExpenseListUiState,
    uiStateOverview: CutieHabExpenseOverviewUiState,
    onItemClick: (CutieHabExpenseUiState) -> Unit,
    onDateFilterChange: (Int, Int) -> Unit,
    onUserFilterChange: (String) -> Unit
) {
    Column {
        CutieHabExpenseOverview(
            modifier = modifier,
            uiState = uiStateOverview
        )

        CutieHabExpenseFilterUser(
            modifier = modifier,
            currentUser = uiState.currentUser,
            userList = uiState.userList,
            onUserFilterChange = onUserFilterChange
        )

        CutieHabExpenseFilterDate(
            modifier = modifier,
            currentDate = uiState.currentDate,
            datetime = uiState.datetime,
            onDateFilterChange = { year, month ->
                onDateFilterChange(year, month)
            }
        )

        CutieHabExpenseList(
            modifier = modifier,
            uiState = uiState.expensesFilteredUiState,
            onClickExpenseItem = onItemClick
        )

        //TODO Pager
//        HorizontalPager(
//            modifier = modifier,
//            state = pagerState,
//            pageSpacing = 0.dp,
//            userScrollEnabled = true,
//            reverseLayout = false,
//            contentPadding = PaddingValues(0.dp),
//            beyondBoundsPageCount = 0,
//            pageSize = PageSize.Fill,
//            key = null,
//            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
//                Orientation.Horizontal
//            ),
//            pageContent = fun PagerScope.(page: Int) {
//                if (page==0) {
//                    LazyColumn(
//                        modifier = modifier
//                    ) {
//            TODO Expense List
//                        items(
//                            items = targetExpenseList,
//                            //define unique keys to prevent unnecessary recompositions.
//                            key = { expense -> expense.id }
//                        ) { expense ->
//                            CutieHabExpenseItemView(
//                                categoryResId = getCategoryIconResId(expense.categoryId),
//                                date = expense.date + " " + expense.time.substring(0, 5),
//                                memo = expense.memo,
//                                paymentMethodResId = getPaymentMethodResId(expense.paymentMethodId),
//                                payerResId = getPayerResIdResId(expense.payerId),
//                                amount = "￥" + NumberFormat.getNumberInstance()
//                                    .format(expense.amount)
//                            ) {
//                                onClickExpenseItem(expense)
//                            }
//                            Divider(color = Color.Black, thickness = 1.dp)
//                        }
//                    }
//                } else {
//                    LazyColumn(
//                        modifier = modifier
//                    ) {
//            TODO Expense List By Category
//                        items(
//                            items = expenseListByCategory,
//                        ) { expense ->
//                            CutieHabExpenseByCategoryItemView(
//                                categoryIconResId = getCategoryIconResId(expense.categoryId),
//                                categoryStringResId = getCategoryStringResId(expense.categoryId),
//                                amount = "￥" + NumberFormat.getNumberInstance()
//                                    .format(expense.amount)
//                            )
//                            Divider(color = Color.LightGray, thickness = 1.dp)
//                        }
//                    }
//                }
//            }
//        )
    }
}

@Composable

internal fun CutieHabExpenseFilterDate(
    modifier: Modifier,
    currentDate: String,
    datetime: List<Int>,
    onDateFilterChange: (Int, Int) -> Unit
) {
    val context = LocalContext.current

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, _ ->
            onDateFilterChange(selectedYear, selectedMonth)
        }, datetime[YEAR], datetime[MONTH] - 1, 1
    )
    val dateInteractionSource = remember { MutableInteractionSource() }
    val isPressed by dateInteractionSource.collectIsPressedAsState()
    if (isPressed) datePicker.show()

    TextField(
        readOnly = true,
        value = currentDate,
        interactionSource = dateInteractionSource,
        onValueChange = {},
        maxLines = 1,
        singleLine = true,
        modifier = modifier.fillMaxWidth().height(48.dp)
    )
}

@Composable
internal fun CutieHabExpenseFilterUser(
    modifier: Modifier,
    currentUser: String,
    userList: List<String>,
    onUserFilterChange: (String) -> Unit
) {
    var isUserOptionsExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isUserOptionsExpanded,
        modifier = modifier.fillMaxWidth(),
        onExpandedChange = {
            isUserOptionsExpanded = !isUserOptionsExpanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = currentUser,
            onValueChange = { },
            label = { Text(stringResource(R.string.hab_user)) },
            modifier = modifier.menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isUserOptionsExpanded
                )
            },
        )
        ExposedDropdownMenu(
            expanded = isUserOptionsExpanded,
            onDismissRequest = {
                isUserOptionsExpanded = false
            }
        ) {
            userList.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onUserFilterChange(selectionOption)
                        isUserOptionsExpanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@Composable
internal fun CutieHabExpenseList(
    modifier: Modifier,
    uiState: List<CutieHabExpenseUiState>,
    onClickExpenseItem: (CutieHabExpenseUiState) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = uiState,
            //define unique keys to prevent unnecessary recompositions.
            key = { uiState -> uiState.expense.id }
        ) { uiState ->
            CutieHabExpenseItemView(
                categoryResId = uiState.categoryResId,
                date = uiState.expense.date + " " + uiState.expense.time,
                memo = uiState.expense.memo,
                paymentMethodResId = uiState.paymentMethodResId,
                userResId = uiState.userResId,
                amount = stringResource(R.string.currency_symbol) + uiState.formattedAmount,
                onItemClick = { onClickExpenseItem(uiState) }
            )

            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}
