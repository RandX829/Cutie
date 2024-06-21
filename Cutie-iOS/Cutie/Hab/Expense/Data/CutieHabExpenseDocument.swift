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
struct CutieHabExpenseDocument: Codable {
    let id: String // DF1E8CE7-7BA1-4355-A9E6-BB12A2D9AD93
    let year: Int32 // 2024
    let month: Int32 // 1
    let day: Int32 // 1
    let category: String // Grocery
    let account: String // X
    let currency: String // JPY
    let amount: Double // 500
    let memo: String // Food
    let isDeleted: Bool // false
}

extension CutieHabExpenseDocument {
    func toModel() -> CutieHabExpense {
        return CutieHabExpense(
            id: id,
            year: year,
            month: month,
            day: day,
            category: category,
            account: account,
            currency: currency,
            amount: amount,
            memo: memo
        )
    }
}
