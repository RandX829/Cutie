/*
 * Copyright 2023-2024 RandX <010and1001@gmail.com>
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

struct CutieHabExpenseListView: View {
    @EnvironmentObject private var repositories: Repositories
    @ObservedObject var viewModel: CutieHabExpenseListViewModel
    
    var body: some View {
        // Total
        VStack {
            HStack(spacing: 0) {
                Text("total")
                // TODO: - make is a user's setting
                Text("currency_symbol")
                Text(viewModel.total, format: .number)
            }
            .font(.title2)
            .bold()
            .padding(.bottom, 4)
            
            HStack(spacing: 0) {
                Text(viewModel.currentAccount)
                Text("total")
                // TODO: - make is a user's setting
                Text("currency_symbol")
                Text(viewModel.totalByAccount, format: .number)
            }
            .font(.title3)
            .bold()
            Divider()
            
            // Date
            HStack {
                Button(action: {
                    viewModel.previousMonth()
                }) {
                    Image(systemName: "chevron.left")
                }
                .padding(.leading)
                Spacer()
                Text(viewModel.selectedDate, format: .dateTime.year().month())
                Spacer()
                Button(action: {
                    viewModel.nextMonth()
                }) {
                    Image(systemName: "chevron.right")
                }
                .padding(.trailing)
            }
            Divider()
            
            // Account Filter
            HStack() {
                Spacer()
                Picker("account", selection: $viewModel.currentAccount) {
                    ForEach(viewModel.accounts, id: \.self) {
                        Text($0).tag($0)
                    }
                }
            }
            
            // Expense List
            List {
                ForEach(viewModel.expensesByAccount) { expense in
                    NavigationLink(
                        destination: CutieHabExpenseDetailsView(
                            viewModel: CutieHabExpenseDetailsViewModel(
                                expenseRepository: repositories.habExpenseRepository,
                                expense: expense
                            )
                        )
                    ) {
                        CutieHabExpenseItemView(expense: expense)
                    }
                }
            }
            .sheet(isPresented: $viewModel.shouldPresentNewView) {
                CutieHabExpenseNewEditView(
                    viewModel: CutieHabExpenseNewEditViewModel(
                        expenseRepository: repositories.habExpenseRepository,
                        categoryRepository: repositories.habExpenseCategoryRepository,
                        accountRepository: repositories.habAccountRepository
                    )
                )
            }
        }
        .onAppear {
            viewModel.getExpensesByMonth(date: viewModel.selectedDate)
            viewModel.getAllAccounts()
        }
        .toolbar{
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: {
                    viewModel.shouldPresentNewView = true
                }, label: {
                    Image(systemName: "plus")
                })
            }
        }
    }
}
