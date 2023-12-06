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
package tokyo.randx.cutie.android.hab.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tokyo.randx.cutie.android.R

data class CutieHabExpenseOverviewUiState(
    val totalAmount: String = String(),
    val userList: List<String> = emptyList(),
    val formattedTotalAmountList: List<String> = emptyList()
)

@Composable
fun CutieHabExpenseOverview(
    modifier: Modifier = Modifier,
    uiState: CutieHabExpenseOverviewUiState
) {
    Column(
        modifier = modifier.padding(top = 16.dp)
    ) {
        Text(
            modifier = modifier.fillMaxWidth().height(48.dp),
            text = stringResource(R.string.hab_total) + stringResource(R.string.currency_symbol) + uiState.totalAmount,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier)

        Row(
            modifier = modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            LazyColumn(
                modifier = modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                items(
                    items = uiState.userList
                ) { user ->
                    Text(
                        text = user,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = modifier.wrapContentSize()
                    )
                }
            }

            LazyColumn(modifier = modifier.wrapContentSize()) {
                items(
                    items = uiState.formattedTotalAmountList
                ) { amount ->
                    Text(
                        text = stringResource(R.string.currency_symbol) + amount,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = modifier.wrapContentSize()
                    )
                }
            }
        }
    }
}
