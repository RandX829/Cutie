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
package tokyo.randx.cutie.android.hab.data.remote

import tokyo.randx.cutie.hab.CutieHabUser

data class CutieHabUserJson(
    val uid: Int = 0,
    val name: String = String()
)

fun CutieHabUser.asJson(): CutieHabUserJson = CutieHabUserJson(uid = uid, name = name)

fun List<CutieHabUserJson>.asModel(): List<CutieHabUser> = map {
    CutieHabUser(uid = it.uid, name = it.name)
}
