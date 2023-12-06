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
package tokyo.randx.cutie.util

import tokyo.randx.cutie.util.CutieConstant.DATE_SEPARATOR
import tokyo.randx.cutie.util.CutieConstant.DAY_DIGITS
import tokyo.randx.cutie.util.CutieConstant.HOUR_DIGITS
import tokyo.randx.cutie.util.CutieConstant.MINUTE_DIGITS
import tokyo.randx.cutie.util.CutieConstant.MONTH_DIGITS
import tokyo.randx.cutie.util.CutieConstant.TIME_SEPARATOR
import tokyo.randx.cutie.util.CutieConstant.YEAR_DIGITS
import tokyo.randx.cutie.util.FormatUtil.formatZerosLeadingNumber

object CutieDateTimeUtil {

    // yyyy-MM-dd eg.2023-01-01
    fun formatDate(year: Int, month: Int, dayOfMonth: Int): String =
        formatZerosLeadingNumber(year, YEAR_DIGITS) + DATE_SEPARATOR + formatZerosLeadingNumber(
            month, MONTH_DIGITS
        ) + DATE_SEPARATOR + formatZerosLeadingNumber(dayOfMonth, DAY_DIGITS)

    // yyyyMM eg.202312
    fun formatDate(year: Int, month: Int): String =
        formatZerosLeadingNumber(year, YEAR_DIGITS) + formatZerosLeadingNumber(month, MONTH_DIGITS)

    // hh:mm eg.08:00
    fun formatTime(hour: Int, minute: Int): String =
        formatZerosLeadingNumber(hour, HOUR_DIGITS) + TIME_SEPARATOR + formatZerosLeadingNumber(
            minute, MINUTE_DIGITS
        )
}
