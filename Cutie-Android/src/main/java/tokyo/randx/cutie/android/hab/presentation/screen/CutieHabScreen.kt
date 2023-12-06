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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tokyo.randx.cutie.android.R
import tokyo.randx.cutie.android.hab.presentation.viewmodel.CutieHabExpenseUiState
import tokyo.randx.cutie.hab.CutieHabRecord

@Composable
fun CutieHabScreen(
    modifier: Modifier = Modifier,
    onClickAdd: () -> Unit,
    onItemClick: (CutieHabExpenseUiState) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(R.string.feature_hab),
                fontWeight = FontWeight.Bold
            )
        }, actions = {
            IconButton(
                onClick = onClickAdd,
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                },
                modifier = modifier
            )
        })
    }) { contentPadding ->
        Box(
            modifier = modifier.padding(contentPadding).fillMaxSize()
        ) {
            Divider(
                modifier = modifier,
                thickness = 1.dp,
                color = Color.Gray
            )

            CutieHabExpenseListRoute(
                modifier = modifier,
                onItemClick = onItemClick
            )
        }
    }
}
