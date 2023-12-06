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
package tokyo.randx.cutie.android.hab.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tokyo.randx.cutie.android.hab.data.local.CutieHabRecordDao
import tokyo.randx.cutie.android.hab.data.local.CutieHabRecordEntity
import tokyo.randx.cutie.android.hab.data.local.asEntity
import tokyo.randx.cutie.android.hab.data.local.asModel
import tokyo.randx.cutie.android.hab.data.remote.CutieHabRecordJson
import tokyo.randx.cutie.android.hab.data.remote.asEntity
import tokyo.randx.cutie.android.hab.data.remote.asJson
import tokyo.randx.cutie.hab.CutieHabRecord
import tokyo.randx.cutie.hab.CutieHabUtil
import javax.inject.Inject
import javax.inject.Singleton

interface CutieHabRecordRepository {
    suspend fun create(record: CutieHabRecord)
    suspend fun get(id: String): CutieHabRecord
    fun getByDate(date: String): Flow<List<CutieHabRecord>>
    suspend fun getRemote(date: String)
    suspend fun restore()
    suspend fun backup()
    suspend fun delete(id: String)
    suspend fun recovery()
}

@Singleton
class CutieHabRecordRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val recordDao: CutieHabRecordDao
) : CutieHabRecordRepository {
    private val collection by lazy { fireStore.collection(RECORD) }

    override suspend fun create(record: CutieHabRecord) {
        // Save to Local DB
        recordDao.insert(record.asEntity())
        // Save to Remote DB
        createRemote(record)
    }

    private fun createRemote(record: CutieHabRecord) {
        val recordJson = record.asJson()
        collection.document(recordJson.id).set(recordJson)
    }

    override suspend fun get(id: String): CutieHabRecord = recordDao.get(id).asModel()


    /**
     * @param date eg.2023, 202308, 20230801
     *
     * */
    override fun getByDate(date: String): Flow<List<CutieHabRecord>> {
        return recordDao.getByRange(
            CutieHabUtil.generateMinRecordId(date),
            CutieHabUtil.generateMaxRecordId(date)
        ).map { record ->
            record.asModel().sortedByDescending { it.id }
        }
    }

    override suspend fun getRemote(date: String) {
        collection
            .whereLessThan(ID, CutieHabUtil.generateMaxRecordId(date))
            .whereGreaterThan(ID, CutieHabUtil.generateMinRecordId(date))
            .orderBy(ID, Query.Direction.DESCENDING)
            .snapshots()
            .collect {
                cache(it.toObjects(CutieHabRecordJson::class.java).asEntity())
            }
    }

    override suspend fun restore() {
        collection
            .snapshots()
            .collect {
                cache(it.toObjects(CutieHabRecordJson::class.java).asEntity())
            }
    }

    override suspend fun backup() {
        recordDao.getAll().forEach {
            createRemote(it.asModel())
        }
    }

    override suspend fun delete(id: String) {
        // Delete from Local DB
        recordDao.delete(id)
        // Delete from Remote DB
        deleteRemote(id)
    }

    override suspend fun recovery() {
        TODO("Not yet implemented")
    }

    private fun deleteRemote(id: String) = collection.document(id).delete()

    private suspend fun cache(record: List<CutieHabRecordEntity>) {
        recordDao.cache(record)
    }

    companion object {
        private const val RECORD = "record"
        private const val ID = "id"
    }
}
