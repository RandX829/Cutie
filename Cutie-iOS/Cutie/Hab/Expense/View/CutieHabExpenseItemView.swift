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
import SwiftUI

struct CutieHabExpenseItemView: View {
    let expense: CutieHabExpense
    
    var body: some View {
        HStack {
            // Category
            Image(expense.category.lowercased())
                .resizable()
                .frame(width: 48, height: 48)
                .aspectRatio(contentMode: .fit)
            VStack(alignment: .leading, spacing: 0) {
                Spacer()
                // Date
                Text(expense.date, format: .dateTime.year().month().day())
                    .frame(height: 24, alignment: .leading)
                // Memo
                Text(expense.memo)
                    .frame(height: 24, alignment: .leading)
                    .lineLimit(1)
                    .truncationMode(.tail)
                Spacer()
            }
            .padding(.leading, 16)
            
            Spacer()
            
            VStack(alignment: .leading, spacing: 0) {
                Spacer()
                // Amount
                HStack(spacing: 0) {
                    Text(LocalizedStringKey(stringLiteral: expense.currency.uppercased()))
                    Text(expense.amount, format: .number)
                        .lineLimit(1)
                        .truncationMode(.tail)
                }
                .bold()
                Spacer()
            }
        }
        .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, maxHeight: 96, alignment: .leading)
        .background(.white)
    }
}
