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

struct CutieHabExpenseDetailsView: View {
    @EnvironmentObject private var repositories: Repositories
    @Environment(\.dismiss) private var dismiss
    @ObservedObject var viewModel: CutieHabExpenseDetailsViewModel

    var body: some View {
        VStack {
            // Amount
            Circle()
                .frame(width: 256)
                .foregroundColor(.teal)
                .overlay {
                    HStack(spacing: 0) {
                        Text(LocalizedStringKey(stringLiteral: viewModel.expense.currency.uppercased()))
                        Text(viewModel.expense.amount, format: .number)
                    }
                    .font(.largeTitle)
                    .bold()
                }
            Form {
                // Date
                HStack {
                    Image(systemName: "calendar")
                    Text(viewModel.expense.date, format: .dateTime.year().month().day())
                    Spacer()
                }
                // Category
                HStack {
                    Image(systemName: "circle.grid.3x3.fill")
                    Text(LocalizedStringKey(stringLiteral: viewModel.expense.category))
                    Spacer()
                }
                // Account
                HStack() {
                    Image(systemName: "person")
                    Text(viewModel.expense.account)
                    Spacer()
                }
                // Memo
                Section(header: Text("memo")) {
                    Text(viewModel.expense.memo)
                }
            }
            .toolbar {
                Button("edit") {
                    viewModel.shouldPresentEditView = true
                }
            }
            .sheet(isPresented: $viewModel.shouldPresentEditView) {
                CutieHabExpenseNewEditView(
                    viewModel: CutieHabExpenseNewEditViewModel(
                        expenseRepository: repositories.habExpenseRepository,
                        categoryRepository: repositories.habExpenseCategoryRepository,
                        accountRepository: repositories.habAccountRepository,
                        editing: viewModel.expense
                    )
                )
            }
            // Delete
            Button("delete", role: .destructive) {
                viewModel.delete()
                dismiss()
            }
        }
    }
}
