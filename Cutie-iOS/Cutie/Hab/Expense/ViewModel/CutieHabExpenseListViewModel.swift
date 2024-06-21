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
import Combine

final class CutieHabExpenseListViewModel: ObservableObject {
    private let expenseRepository: CutieHabExpenseRepository
    private let accountRepository: CutieHabAccountRepository
    private var cancellables = Set<AnyCancellable>()
    
    // All Expenses
    @Published var expenses: [CutieHabExpense] = []
    
    var expensesByAccount: [CutieHabExpense] {
        self.expenses.filter { $0.account == self.currentAccount }.sorted { $0.date > $1.date }
    }
    var total: Double {
        CutieHabUtil.shared.calculateTotalAmount(amount: expenses.map { KotlinDouble(integerLiteral: Int($0.amount)) })
    }
    
    var totalByAccount: Double {
        CutieHabUtil.shared.calculateTotalAmount(amount: expensesByAccount.map { KotlinDouble(integerLiteral: Int($0.amount)) })
    }
    @Published var isLoading = false
    @Published var accounts: [String] = []
    @Published var isAccountsLoading = false
    @Published var shouldPresentNewView = false
    @Published var editing: CutieHabExpense = .init(id: "", year: 1970, month: 1, day: 1, category: "", account: "", currency: "JPY", amount: 0, memo: "")
    @Published var currentAccount: String = ""
    
    @Published var selectedDate: Date = Date()
    
    init(
        expenseRepository: CutieHabExpenseRepository,
        accountRepository: CutieHabAccountRepository
    ) {
        self.expenseRepository = expenseRepository
        self.accountRepository = accountRepository
    }
    
    func getExpensesByMonth(date: Date) {
        expenseRepository.getExpensesByMonth(year: date.getYear(), month: date.getMonth())
            .sink(receiveCompletion: {_ in
                self.isLoading = false
            }, receiveValue: { expenses in
                self.expenses = expenses
            })
            .store(in: &cancellables)
    }
    
    func previousMonth() {
        selectedDate = selectedDate.previousMonth()
        getExpensesByMonth(date: selectedDate)
    }
    
    func nextMonth() {
        selectedDate = selectedDate.nextMonth()
        getExpensesByMonth(date: selectedDate)
    }
        
    func getAllAccounts() {
        accountRepository.getAllAccounts()
            .sink(receiveCompletion: {_ in
                self.isAccountsLoading = false
            }, receiveValue: { accounts in
                self.accounts = accounts
                if !accounts.isEmpty { self.currentAccount = accounts[0] }
            })
            .store(in: &cancellables)
    }
    
    private func reset() -> CutieHabExpense {
        .init(id: "", year: 1970, month: 1, day: 1, category: "", account: "", currency: "JPY", amount: 0, memo: "")
    }
}

