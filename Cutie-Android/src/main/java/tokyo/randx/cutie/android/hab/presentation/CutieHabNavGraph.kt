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
package tokyo.randx.cutie.android.hab.presentation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import tokyo.randx.cutie.android.hab.presentation.screen.CutieHabExpenseCreateUpdateRoute
import tokyo.randx.cutie.android.hab.presentation.screen.CutieHabScreen

enum class CutieHabDestination(val route: String) {
    CutieHab(route = "CutieHab"),
    Hab(route = "Hab"),
    HabExpenseCreateUpdate(route = "HabExpenseCreateUpdate"),
    HabExpenseList(route = "HabExpenseList"),
    HabExpenseDetails(route = "HabExpenseDetails/")
}

fun NavGraphBuilder.cutieHabNavGraph(
    modifier: Modifier,
    navController: NavController
) {
    navigation(
        route = CutieHabDestination.CutieHab.route,
        startDestination = CutieHabDestination.Hab.route
    ) {
        composable(route = CutieHabDestination.Hab.route) {
            CutieHabScreen(
                modifier = modifier,
                onClickAdd = { navController.navigate(CutieHabDestination.HabExpenseCreateUpdate.route) },
                onItemClick = {
                    navController.navigate(
                        CutieHabDestination.HabExpenseDetails.route + it.expense.id
                    )
                }
            )
        }
        composable(route = CutieHabDestination.HabExpenseCreateUpdate.route) {
            CutieHabExpenseCreateUpdateRoute(
                modifier = modifier,
                onComplete = { navController.popBackStack() }
            )
        }
        composable(
            route = CutieHabDestination.HabExpenseDetails.route.plus("{recordId}"),
            arguments = listOf(navArgument("recordId") {
                type = NavType.StringType
            })
        ) {
            CutieHabExpenseCreateUpdateRoute(
                modifier = modifier,
                recordId = it.arguments?.getString("recordId"),
                isUpdate = true,
                onComplete = { navController.popBackStack() }
            )
        }
    }
}
