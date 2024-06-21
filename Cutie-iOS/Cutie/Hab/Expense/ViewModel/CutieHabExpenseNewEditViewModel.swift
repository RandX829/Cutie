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
import Foundation
import Combine

final class CutieHabExpenseNewEditViewModel: ObservableObject {
    private let expenseRepository: CutieHabExpenseRepository
    private let categoryRepository: CutieHabExpenseCategoryRepository
    private let accountRepository: CutieHabAccountRepository
    
    private var cancellables = Set<AnyCancellable>()
    @Published var categories: [String] = []
    @Published var isCategoriesLoading = false
    @Published var accounts: [String] = []
    @Published var isAccountsLoading = false
    @Published var editing: CutieHabExpense
    
    init(
        expenseRepository: CutieHabExpenseRepository,
        categoryRepository: CutieHabExpenseCategoryRepository,
        accountRepository: CutieHabAccountRepository,
        editing: CutieHabExpense = .init(
            id: UUID().uuidString,
            year: Date().getYear(),
            month: Date().getMonth(),
            day: Date().getDay(),
            category: "",
            account: "",
            currency: "JPY",
            amount: 0,
            memo: ""
        )
    ) {
        self.expenseRepository = expenseRepository
        self.categoryRepository = categoryRepository
        self.accountRepository = accountRepository
        self.editing = editing
        getAllCategories()
        getAllAccounts()
    }
    
    func getAllAccounts() {
        accountRepository.getAllAccounts()
            .sink(receiveCompletion: {_ in 
                self.isAccountsLoading = false
            }, receiveValue: { accounts in
                self.accounts = accounts
                if !accounts.isEmpty, self.editing.account.isEmpty { self.editing.account = accounts[0] }
            })
            .store(in: &cancellables)
    }

    func getAllCategories() {
        categoryRepository.getAllCategories()
            .sink(receiveCompletion: {_ in 
                self.isCategoriesLoading = false
            }, receiveValue: { categories in
                self.categories = categories
                if !categories.isEmpty, self.editing.category.isEmpty { self.editing.category = categories[0] }
            })
            .store(in: &cancellables)
    }
    
    func save() {
        guard editing.amount != 0 else { return }

        expenseRepository.addExpense(expense: editing)
    }
}
