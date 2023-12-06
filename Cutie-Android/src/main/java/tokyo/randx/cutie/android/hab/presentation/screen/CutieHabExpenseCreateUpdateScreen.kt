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
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tokyo.randx.cutie.android.R
import tokyo.randx.cutie.android.hab.presentation.viewmodel.CutieHabExpenseCreateUpdateUiState
import tokyo.randx.cutie.android.hab.presentation.viewmodel.CutieHabExpenseCreateUpdateViewModel
import tokyo.randx.cutie.util.CutieConstant.DAY
import tokyo.randx.cutie.util.CutieConstant.HOUR
import tokyo.randx.cutie.util.CutieConstant.MINUTE
import tokyo.randx.cutie.util.CutieConstant.MONTH
import tokyo.randx.cutie.util.CutieConstant.YEAR

@Composable
fun CutieHabExpenseCreateUpdateRoute(
    modifier: Modifier = Modifier,
    recordId: String? = String(),
    isUpdate: Boolean = false,
    onComplete: () -> Unit,
    viewModel: CutieHabExpenseCreateUpdateViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.recordId.isEmpty() && isUpdate) viewModel.getRecord(recordId)

    CutieHabExpenseCreateUpdateScreen(modifier = modifier,
        uiState = uiState,
        onDateChange = { year, month, day ->
            viewModel.updateDate(year, month, day)
        },
        onTimeChange = { hour, minute ->
            viewModel.updateTime(hour, minute)
        },
        onCategoryChange = { viewModel.updateCategory(it) },
        onUserChange = { viewModel.updateUser(it) },
        onPaymentMethodChange = { viewModel.updatePaymentMethod(it) },
        onAmountChange = { viewModel.updateAmount(it) },
        onMemoChange = { viewModel.updateMemo(it) },
        onCreateUpdateClick = {
            onComplete()
            viewModel.createOrUpdateRecord()
        },
        onCancelClick = { onComplete() },
        onDeleteClick = {
            onComplete()
            viewModel.deleteRecord()
        })
}

@Composable
internal fun CutieHabExpenseCreateUpdateScreen(
    modifier: Modifier = Modifier,
    uiState: CutieHabExpenseCreateUpdateUiState,
    onDateChange: (Int, Int, Int) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onCategoryChange: (String) -> Unit,
    onUserChange: (String) -> Unit,
    onPaymentMethodChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onMemoChange: (String) -> Unit,
    onCreateUpdateClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val context = LocalContext.current
    val datetime = uiState.datetime
    // Date
    val date = uiState.date
    val datePicker = DatePickerDialog(
        context, { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            onDateChange(selectedYear, selectedMonth + 1, selectedDayOfMonth)
        }, datetime[YEAR], datetime[MONTH] - 1, datetime[DAY]
    )
    val datePickerInteractionSource = remember { MutableInteractionSource() }
    val isPressedDatePicker by datePickerInteractionSource.collectIsPressedAsState()
    if (isPressedDatePicker) datePicker.show()
    // Time
    val time = uiState.time
    val timePicker = TimePickerDialog(
        context, { _, selectedHour: Int, selectedMinute: Int ->
            onTimeChange(selectedHour, selectedMinute)
        }, datetime[HOUR], datetime[MINUTE], true
    )
    val timePickerInteractionSource = remember { MutableInteractionSource() }
    val isPressedTimePicker by timePickerInteractionSource.collectIsPressedAsState()
    if (isPressedTimePicker) timePicker.show()
    // Category
    val categoryOptions = uiState.categoryList
    val category = uiState.category
    var isCategoryOptionsExpanded by rememberSaveable { mutableStateOf(false) }
    // User
    val userOptions = uiState.userList
    val user = uiState.user
    var isUserOptionsExpanded by rememberSaveable { mutableStateOf(false) }
    // Payment Method
    val paymentMethodOptions = uiState.paymentMethodList
    val paymentMethod = uiState.paymentMethod
    var isPaymentMethodOptionsExpanded by rememberSaveable { mutableStateOf(false) }
    // Amount
    val amount = uiState.amountStr
    // Memo
    val memo = uiState.memo

    ConstraintLayout(
        modifier = modifier.fillMaxHeight().fillMaxWidth()
    ) {
        val (btnSave, btnCancel, labelDate, labelTime, labelCategory, labelUser, labelPaymentMethod, labelAmount, textFieldMemo, btnDelete) = createRefs()

        TextButton(onClick = { onCreateUpdateClick() }, content = {
            Text(
                text = if (uiState.isUpdate) stringResource(R.string.update) else stringResource(
                    R.string.hab_create
                ), fontWeight = FontWeight.Bold
            )
        }, modifier = modifier.size(144.dp, 48.dp).constrainAs(btnSave) {
            end.linkTo(parent.end, margin = 8.dp)
            top.linkTo(parent.top, margin = 8.dp)
        })

        TextButton(onClick = {
            onCancelClick()
        }, content = {
            Text(
                text = stringResource(R.string.cancel), fontWeight = FontWeight.Bold
            )
        }, modifier = modifier.size(144.dp, 48.dp).constrainAs(btnCancel) {
            start.linkTo(parent.start, margin = 8.dp)
            top.linkTo(parent.top, margin = 8.dp)
        })

        OutlinedTextField(readOnly = true,
            value = date,
            interactionSource = datePickerInteractionSource,
            label = { Text(stringResource(R.string.date)) },
            onValueChange = {},
            maxLines = 1,
            singleLine = true,
            modifier = modifier.constrainAs(labelDate) {
                top.linkTo(btnSave.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        OutlinedTextField(readOnly = true,
            value = time,
            onValueChange = { },
            interactionSource = timePickerInteractionSource,
            label = { Text(stringResource(R.string.time)) },
            maxLines = 1,
            singleLine = true,
            modifier = modifier.constrainAs(labelTime) {
                top.linkTo(labelDate.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        ExposedDropdownMenuBox(expanded = isCategoryOptionsExpanded,
            modifier = modifier.constrainAs(labelCategory) {
                top.linkTo(labelTime.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onExpandedChange = {
                isCategoryOptionsExpanded = !isCategoryOptionsExpanded
            }) {
            OutlinedTextField(
                readOnly = true,
                value = category,
                onValueChange = { },
                label = { Text(stringResource(R.string.hab_category)) },
                modifier = modifier.menuAnchor(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isCategoryOptionsExpanded
                    )
                },
            )
            ExposedDropdownMenu(expanded = isCategoryOptionsExpanded, onDismissRequest = {
                isCategoryOptionsExpanded = false
            }) {
                categoryOptions.forEach { selectionOption ->
                    DropdownMenuItem(onClick = {
                        onCategoryChange(selectionOption)
                        isCategoryOptionsExpanded = false
                    }) {
                        Text(text = selectionOption)
                    }
                }
            }
        }

        ExposedDropdownMenuBox(expanded = isUserOptionsExpanded,
            modifier = modifier.constrainAs(labelUser) {
                top.linkTo(labelCategory.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onExpandedChange = {
                isUserOptionsExpanded = !isUserOptionsExpanded
            }) {
            OutlinedTextField(
                readOnly = true,
                value = user,
                onValueChange = { },
                label = { Text(stringResource(R.string.hab_user)) },
                modifier = modifier.menuAnchor(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isUserOptionsExpanded
                    )
                },
            )
            ExposedDropdownMenu(expanded = isUserOptionsExpanded, onDismissRequest = {
                isUserOptionsExpanded = false
            }) {
                userOptions.forEach { selectionOption ->
                    DropdownMenuItem(onClick = {
                        onUserChange(selectionOption)
                        isUserOptionsExpanded = false
                    }) {
                        Text(text = selectionOption)
                    }
                }
            }
        }

        ExposedDropdownMenuBox(expanded = isPaymentMethodOptionsExpanded,
            modifier = modifier.constrainAs(labelPaymentMethod) {
                top.linkTo(labelUser.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onExpandedChange = {
                isPaymentMethodOptionsExpanded = !isPaymentMethodOptionsExpanded
            }) {
            OutlinedTextField(
                readOnly = true,
                value = paymentMethod,
                onValueChange = { },
                label = { Text(stringResource(R.string.hab_payment_method)) },
                modifier = modifier.menuAnchor(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isPaymentMethodOptionsExpanded
                    )
                },
            )
            ExposedDropdownMenu(expanded = isPaymentMethodOptionsExpanded, onDismissRequest = {
                isPaymentMethodOptionsExpanded = false
            }) {
                paymentMethodOptions.forEach { selectionOption ->
                    DropdownMenuItem(onClick = {
                        onPaymentMethodChange(selectionOption)
                        isPaymentMethodOptionsExpanded = false
                    }) {
                        Text(text = selectionOption)
                    }
                }
            }
        }

        OutlinedTextField(value = amount,
            onValueChange = { onAmountChange(it) },
            label = { Text(stringResource(R.string.hab_amount)) },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier.constrainAs(labelAmount) {
                top.linkTo(labelPaymentMethod.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        OutlinedTextField(value = memo,
            onValueChange = { onMemoChange(it) },
            label = { Text(stringResource(R.string.hab_memo)) },
            modifier = modifier.constrainAs(textFieldMemo) {
                top.linkTo(labelAmount.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        if (uiState.isUpdate) {
            TextButton(onClick = { onDeleteClick() }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red, contentColor = Color.White
            ), content = {
                Text(
                    text = stringResource(R.string.delete), fontWeight = FontWeight.Bold
                )
            }, modifier = modifier.size(256.dp, 48.dp).constrainAs(btnDelete) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(textFieldMemo.bottom, margin = 16.dp)
            })
        }
    }
}
