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

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import tokyo.randx.cutie.android.hab.data.CutieHabUserRepository
import tokyo.randx.cutie.android.hab.data.CutieHabRecordRepository
import tokyo.randx.cutie.hab.CutieHabUser
import tokyo.randx.cutie.hab.CutieHabRecord
import tokyo.randx.cutie.hab.CutieHabUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CutieHabExpenseGetUseCase @Inject constructor(
    private val userRepository: CutieHabUserRepository,
    private val recordRepository: CutieHabRecordRepository
) {
    fun filterByUser(record: List<CutieHabRecord>, uid: Int): List<CutieHabRecord> =
        CutieHabUtil.filterRecordByUser(record, uid)

    fun filterByCategory(record: List<CutieHabRecord>, categoryId: Int): List<CutieHabRecord> =
        CutieHabUtil.filterRecordByCategory(record, categoryId)

    fun getUserList(): Flow<List<CutieHabUser>> =
        userRepository.geUserList().map { it.sortedBy { user -> user.uid } }

    fun getByDate(date: String): Flow<List<CutieHabRecord>> {
        if (date.isEmpty()) emptyList<CutieHabRecord>().asFlow()

        return recordRepository.getByDate(date)
    }

    suspend fun getRemoteByDate(date: String) {
        if (date.isEmpty()) return

        recordRepository.getRemote(date)
    }

    fun getTotalAmount(record: List<CutieHabRecord>) = CutieHabUtil.getTotalAmount(record)

    fun getTotalAmountList(record: List<CutieHabRecord>): List<Int> =
        CutieHabUtil.getTotalAmountListByUser(record)
}
