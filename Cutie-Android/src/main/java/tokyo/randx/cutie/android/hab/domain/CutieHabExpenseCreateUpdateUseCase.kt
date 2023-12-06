/*
 * Copyright 2023 RandX <010and1001@gmail.com>
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
package tokyo.randx.cutie.android.hab.domain

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import tokyo.randx.cutie.android.hab.data.CutieHabUserRepository
import tokyo.randx.cutie.android.hab.data.CutieHabRecordRepository
import tokyo.randx.cutie.android.hab.util.CutieHabResUtil
import tokyo.randx.cutie.android.hab.util.CutieHabResUtil.getCategoryId
import tokyo.randx.cutie.android.hab.util.CutieHabResUtil.getPaymentMethodId
import tokyo.randx.cutie.android.util.CutieDateTimeProvider
import tokyo.randx.cutie.hab.CutieHabUser
import tokyo.randx.cutie.hab.CutieHabRecord
import tokyo.randx.cutie.hab.CutieHabUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CutieHabExpenseCreateUpdateUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: CutieHabUserRepository,
    private val recordRepository: CutieHabRecordRepository
) {

    suspend fun get(id: String?): CutieHabRecord {
        if (id.isNullOrEmpty()) return CutieHabRecord()

        return recordRepository.get(id)
    }

    private suspend fun create(
        id: String,
        date: String,
        time: String,
        categoryId: Int,
        uid: Int,
        paymentMethodId: Int,
        amount: Int,
        memo: String
    ) {
        val record = CutieHabRecord(
            id = id,
            date = date,
            time = time,
            categoryId = categoryId,
            uid = uid,
            paymentMethodId = paymentMethodId,
            amount = amount,
            memo = memo
        )

        if (CutieHabUtil.isValidRecord(record)) recordRepository.create(record)
    }

    private suspend fun update(
        id: String,
        newId: String,
        date: String,
        time: String,
        categoryId: Int,
        uid: Int,
        paymentMethodId: Int,
        amount: Int,
        memo: String
    ) {
        delete(id)
        create(newId, date, time, categoryId, uid, paymentMethodId, amount, memo)
    }

    suspend fun createOrUpdate(
        id: String,
        date: String,
        time: String,
        category: String,
        payerId: Int,
        paymentMethod: String,
        amountStr: String,
        memo: String
    ) {
        val categoryId = getCategoryId(category, context)
        val paymentMethodId = getPaymentMethodId(paymentMethod, context)
        val amount = if (amountStr.isEmpty()) 0 else amountStr.toInt()
        // ID for NEW Record when Update
        val newId = CutieHabUtil.generateRecordId(
            date, time, categoryId, payerId, paymentMethodId, amount
        )

        if (id.isEmpty()) create(
            newId, date, time, categoryId, payerId, paymentMethodId, amount, memo
        ) else update(
            id, newId, date, time, categoryId, payerId, paymentMethodId, amount, memo
        )
    }

    fun getCategory(categoryId: Int) = CutieHabResUtil.getCategory(categoryId, context)

    fun getCategoryList() = CutieHabResUtil.getCategoryList(context)

    fun getUserList(): Flow<List<CutieHabUser>> = userRepository.geUserList()

    fun getPaymentMethod(paymentMethodId: Int) =
        CutieHabResUtil.getPaymentMethod(paymentMethodId, context)

    fun getPaymentMethodList() = CutieHabResUtil.getPaymentMethodList(context)

    suspend fun delete(recordId: String) {
        if (recordId.isNotEmpty()) recordRepository.delete(recordId)
    }

    fun getDatetime(): List<Int> = CutieDateTimeProvider.getDatetime()
}
