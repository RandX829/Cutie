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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import tokyo.randx.cutie.android.R

@Composable
fun CutieHabExpenseItemView(
    categoryResId: Int,
    date: String,
    memo: String,
    paymentMethodResId: Int,
    userResId: Int,
    amount: String,
    onItemClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .height(96.dp)
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        val (imageCategory,
            textDate,
            textMemo,
            imagePaymentMethod,
            imageUser,
            textAmount,
            iconArrowRight
        ) = createRefs()

        Image(
            painter = painterResource(categoryResId),
            contentDescription = stringResource(R.string.hab_category),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .constrainAs(imageCategory) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = date,
            modifier = Modifier
                .size(144.dp, 24.dp)
                .constrainAs(textDate) {
                    start.linkTo(imageCategory.end, margin = 16.dp)
                    top.linkTo(parent.top, margin = 8.dp)
                }
        )
        Text(
            text = memo,
            modifier = Modifier
                .size(144.dp, 24.dp)
                .constrainAs(textMemo) {
                    start.linkTo(imageCategory.end, margin = 16.dp)
                    top.linkTo(textDate.bottom)
                }

        )
        Image(
            painter = painterResource(paymentMethodResId),
            contentDescription = stringResource(R.string.hab_payment_method),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp, 24.dp)
                .constrainAs(imagePaymentMethod) {
                    start.linkTo(imageCategory.end, 16.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
        )
        Image(
            painter = painterResource(userResId),
            contentDescription = stringResource(R.string.hab_user),
            modifier = Modifier
                .size(24.dp)
                .constrainAs(imageUser) {
                    start.linkTo(imagePaymentMethod.end, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
        )
        Text(
            text = amount,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .height(24.dp)
                .constrainAs(textAmount) {
                    end.linkTo(iconArrowRight.start, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(iconArrowRight) {
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}
