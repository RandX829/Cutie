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
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tokyo.randx.cutie.android.hab.data.remote.CutieHabUserJson
import tokyo.randx.cutie.android.hab.data.remote.asModel
import tokyo.randx.cutie.hab.CutieHabUser
import javax.inject.Inject
import javax.inject.Singleton

interface CutieHabUserRepository {
    suspend fun create(user: CutieHabUser)
    fun geUserList(): Flow<List<CutieHabUser>>
    suspend fun delete(id: String)
}

@Singleton
class CutieHabUserRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
) : CutieHabUserRepository {
    private val collection by lazy { fireStore.collection(USERS) }

    override suspend fun create(user: CutieHabUser) {
        TODO("Not yet implemented")
    }

    override fun geUserList(): Flow<List<CutieHabUser>> =
        collection.snapshots().map {
            it.toObjects(CutieHabUserJson::class.java).asModel()
        }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val USERS = "users"
    }
}
