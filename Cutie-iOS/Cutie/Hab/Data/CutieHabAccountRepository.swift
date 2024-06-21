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
import Combine
import Firebase

protocol CutieHabAccountRepository {
    func getAllAccounts() -> AnyPublisher<[String], Error>
    func addAccount(account: String)
    func deleteAccount(account: String)
}

final class FirestoreCutieHabAccountRepository: CutieHabAccountRepository, ObservableObject {
    private let accounts: CollectionReference
    
    init(firestore: Firestore = Firestore.firestore()) {
        self.accounts = firestore.collection("accounts")
    }
    
    func getAllAccounts() -> AnyPublisher<[String], Error> {
        let publisher = CurrentValueSubject<[String], Error>([])
        
        self.accounts
            .order(by: "id")
            .addSnapshotListener { snapshot, error in
                if error != nil {
                    publisher.send(completion: .finished)
                    return
                }
                
                guard let documents = snapshot?.documents else {
                    return
                }
                
                let accounts: [String] = documents.compactMap { $0.data()["name"] as? String }
                publisher.send(accounts)
            }
        
        return publisher.handleEvents().eraseToAnyPublisher()
    }
    
    // TODO: - TBD
    func addAccount(account: String) {
        
    }
    
    // TODO: - TBD
    func deleteAccount(account: String) {
        
    }
}
