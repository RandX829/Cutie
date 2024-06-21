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

struct CutieHabExpenseNewEditView: View {
    @Environment(\.dismiss) private var dismiss
    @ObservedObject var viewModel: CutieHabExpenseNewEditViewModel
    
    var body: some View {
        VStack {
            HStack {
                Button(action: { dismiss() }) {
                    Text("cancel")
                }
                Spacer()
                Button(action: {
                    viewModel.save()
                    dismiss()
                }) {
                    Text("done")
                }
            }
            .padding([.top, .horizontal])
            
            Form {
                HStack {
                    DatePicker(
                        "date",
                        selection: $viewModel.editing.date,
                        displayedComponents: [.date]
                    )
                }
                
                HStack {
                    Picker("category", selection: $viewModel.editing.category) {
                        ForEach(viewModel.categories, id: \.self) { category in
                            Text(LocalizedStringKey(stringLiteral: category)).tag(category)
                        }
                    }
                }
                
                HStack {
                    Picker("account", selection: $viewModel.editing.account) {
                        ForEach(viewModel.accounts, id: \.self) {
                            Text($0).tag($0)
                        }
                    }
                }
                
                Section(header: Text("amount")) {
                    TextField("amount", value: $viewModel.editing.amount, format: .number)
                }
                
                Section(header: Text("memo")) {
                    TextField("memo", text: $viewModel.editing.memo)
                        .multilineTextAlignment(.leading)
                }
            }
            .multilineTextAlignment(.trailing)
        }
    }
}
