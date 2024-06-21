/*
 * Copyright 2024 RandX <010and1001@gmail.com>
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
import CutieCore
import Foundation

class CutieHabExpense: CutieCoreHabExpense, Identifiable {
    var date: Date {
        get {
            Calendar.current.date(from: DateComponents(year: Int(year), month: Int(month), day: Int(day))) ?? Date()
        }
        set(value) {
            year = value.getYear()
            month = value.getMonth()
            day = value.getDay()
        }
    }
}
