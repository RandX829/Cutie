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
package tokyo.randx.cutie.android.util

import java.util.Calendar

object CutieDateTimeProvider {

    fun getDatetime(): List<Int> {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
        val hourOfDay = calendar[Calendar.HOUR_OF_DAY] // 24h
        val minute = calendar[Calendar.MINUTE]

        return listOf(year, month, dayOfMonth, hourOfDay, minute)
    }
}
