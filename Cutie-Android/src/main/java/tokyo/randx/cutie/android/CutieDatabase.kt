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
package tokyo.randx.cutie.android

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import tokyo.randx.cutie.android.hab.data.local.CutieHabRecordDao
import tokyo.randx.cutie.android.hab.data.local.CutieHabRecordEntity

@Database(
    entities = [CutieHabRecordEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2, DatabaseMigration.Spec1to2::class)],
    exportSchema = true
)
abstract class CutieDatabase : RoomDatabase() {
    abstract fun recordDao(): CutieHabRecordDao

    companion object {
        @Volatile
        private var instance: CutieDatabase? = null

        fun getInstance(context: Context): CutieDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    CutieDatabase::class.java,
                    "cutie-database"
                )
                    .build().also { instance = it }
            }
        }
    }
}

object DatabaseMigration {

    @DeleteTable(tableName = "expenses")
    class Spec1to2 : AutoMigrationSpec
}
