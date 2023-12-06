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
package tokyo.randx.cutie.android.hab.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tokyo.randx.cutie.android.CutieDatabase
import tokyo.randx.cutie.android.hab.data.CutieHabUserRepository
import tokyo.randx.cutie.android.hab.data.CutieHabUserRepositoryImpl
import tokyo.randx.cutie.android.hab.data.CutieHabRecordRepository
import tokyo.randx.cutie.android.hab.data.CutieHabRecordRepositoryImpl
import tokyo.randx.cutie.android.hab.data.local.CutieHabRecordDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CutieHabModule {

    @Singleton
    @Provides
    fun provideCutieHabRecordDao(
        cutieDatabase: CutieDatabase
    ): CutieHabRecordDao = cutieDatabase.recordDao()


    @Singleton
    @Provides
    fun provideCutieHabRecordRepository(
        fireStore: FirebaseFirestore,
        recordDao: CutieHabRecordDao
    ): CutieHabRecordRepository = CutieHabRecordRepositoryImpl(fireStore, recordDao)

    @Singleton
    @Provides
    fun provideCutieHabUserRepository(
        fireStore: FirebaseFirestore
    ): CutieHabUserRepository = CutieHabUserRepositoryImpl(fireStore)
}
