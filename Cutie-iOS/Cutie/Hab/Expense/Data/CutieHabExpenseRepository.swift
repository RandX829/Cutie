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
import Firebase
import FirebaseFirestore

protocol CutieHabExpenseRepository {
    func getExpensesByYear(year: Int32) -> AnyPublisher<[CutieHabExpense], Error>
    func getExpensesByMonth(year: Int32, month: Int32) -> AnyPublisher<[CutieHabExpense], Error>
    func addExpense(expense: CutieHabExpense)
    func delete(expense: CutieHabExpense)
}

final class FirestoreCutieHabExpenseRepository: CutieHabExpenseRepository, ObservableObject {
    private let expensesCollection: CollectionReference
    
    init(firestore: Firestore = Firestore.firestore()) {
        self.expensesCollection = firestore.collection("expenses")
    }
    
    // for yearly report
    func getExpensesByYear(year: Int32) -> AnyPublisher<[CutieHabExpense], any Error> {
        let publisher = CurrentValueSubject<[CutieHabExpense], Error>([])
        
        self.expensesCollection
            .whereField("year", isEqualTo: year)
            .order(by: "datetime", descending: true)
            .addSnapshotListener { snapshot, error in
                if error != nil {
                    publisher.send(completion: .finished)
                    return
                }
                
                guard let documents = snapshot?.documents else { return }
                
                let expenses: [CutieHabExpense] = documents.compactMap { document in
                    do {
                        let document = try document.data(as: CutieHabExpenseDocument.self)
                        if document.isDeleted { return nil }
                        print("CutieHabRecordDocument: \(document)")
                        let expense = document.toModel()
                        print(expense)
                        return expense
                    } catch {
                        return nil
                    }
                }
                publisher.send(expenses)
            }
        
        return publisher.handleEvents().eraseToAnyPublisher()
        
    }

    func getExpensesByMonth(year: Int32, month: Int32) -> AnyPublisher<[CutieHabExpense], any Error> {
        let publisher = CurrentValueSubject<[CutieHabExpense], Error>([])
        
        self.expensesCollection
            .whereField("year", isEqualTo: year)
            .whereField("month", isEqualTo: month)
            .addSnapshotListener { snapshot, error in
                if error != nil {
                    publisher.send(completion: .finished)
                    print("Snapshot Listener Error: \(error?.localizedDescription ?? "")")
                    return
                }
                
                guard let documents = snapshot?.documents else { return }
                
                let expenses: [CutieHabExpense] = documents.compactMap { document in
                    do {
                        let document = try document.data(as: CutieHabExpenseDocument.self)
                        if document.isDeleted { return nil }
                        print("CutieHabRecordDocument: \(document)")
                        let expense = document.toModel()
                        print(expense)
                        return expense
                    } catch {
                        return nil
                    }
                }
                publisher.send(expenses)
            }
        
        return publisher.handleEvents().eraseToAnyPublisher()
    }
    
    func addExpense(expense: CutieHabExpense) {
        let document = CutieHabExpenseDocument(
            id: expense.id,
            year: expense.date.getYear(),
            month: expense.date.getMonth(),
            day: expense.date.getDay(),
            category: expense.category,
            account: expense.account,
            currency: expense.currency,
            amount: expense.amount,
            memo: expense.memo,
            isDeleted: false)
        
        try? expensesCollection.document(expense.id).setData(from: document)
    }
    
    func delete(expense: CutieHabExpense) {
        expensesCollection.document(expense.id).updateData(["isDeleted": true])
    }
}
