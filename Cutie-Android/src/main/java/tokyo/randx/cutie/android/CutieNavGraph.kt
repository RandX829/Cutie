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
package tokyo.randx.cutie.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tokyo.randx.cutie.android.hab.presentation.CutieHabDestination
import tokyo.randx.cutie.android.hab.presentation.cutieHabNavGraph

enum class CutieDestination(
    val route: String
) {
    CutieRoot(route = "Root")
}

@Composable
fun CutieNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = CutieDestination.CutieRoot.route
    ) {
        cutieNavGraph(modifier, navController)
        cutieHabNavGraph(modifier, navController)
    }
}

fun NavGraphBuilder.cutieNavGraph(
    modifier: Modifier,
    navController: NavController) {
    composable(route = CutieDestination.CutieRoot.route) {
        CutieHomeRoute(
            modifier = modifier,
            onClickHab = { navController.navigate(CutieHabDestination.CutieHab.route) }
        )
    }
}
