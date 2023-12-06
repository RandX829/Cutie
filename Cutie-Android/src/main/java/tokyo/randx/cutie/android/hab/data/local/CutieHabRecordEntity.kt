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
package tokyo.randx.cutie.android.hab.data.local

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tokyo.randx.cutie.hab.CutieHabRecord
import tokyo.randx.cutie.hab.CutieHabUtil

@Entity(tableName = "record")
data class CutieHabRecordEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "memo")
    val memo: String
    //TODO categoryId, uid, paymentMethodId
    //TODO isDeleted for Recovery
)

@Dao
interface CutieHabRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: CutieHabRecordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cache(record: List<CutieHabRecordEntity>)

    @Query("SELECT * FROM record WHERE id == :id")
    fun get(id: String): CutieHabRecordEntity

    @Query("SELECT * FROM record")
    fun getAll(): List<CutieHabRecordEntity>

    @Query("SELECT * FROM record WHERE id >= :start AND " + "id <= :end")
    fun getByRange(start: String, end: String): Flow<List<CutieHabRecordEntity>>

    @Query("DELETE FROM record WHERE id == :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM record")
    suspend fun clear()
}

fun List<CutieHabRecordEntity>.asModel(): List<CutieHabRecord> {
    return map {
        CutieHabRecord(
            id = it.id,
            date = CutieHabUtil.parseDate(it.id),
            time = CutieHabUtil.parseTime(it.id),
            categoryId = CutieHabUtil.parseCategoryId(it.id),
            uid = CutieHabUtil.parseUserId(it.id),
            paymentMethodId = CutieHabUtil.parsePaymentMethodId(it.id),
            amount = CutieHabUtil.parseAmount(it.id),
            memo = it.memo
        )
    }
}

fun CutieHabRecord.asEntity(): CutieHabRecordEntity = CutieHabRecordEntity(id = id, memo = memo)

fun CutieHabRecordEntity.asModel(): CutieHabRecord = CutieHabRecord(
    id = id,
    date = CutieHabUtil.parseDate(id),
    time = CutieHabUtil.parseTime(id),
    categoryId = CutieHabUtil.parseCategoryId(id),
    uid = CutieHabUtil.parseUserId(id),
    paymentMethodId = CutieHabUtil.parsePaymentMethodId(id),
    amount = CutieHabUtil.parseAmount(id),
    memo = memo
)
