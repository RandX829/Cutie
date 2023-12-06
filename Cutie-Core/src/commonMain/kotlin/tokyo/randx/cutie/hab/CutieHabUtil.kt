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
package tokyo.randx.cutie.hab

import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_AMOUNT
import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_CATEGORY
import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_DAY
import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_END
import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_HOUR
import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_MINUTE
import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_MONTH
import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_USER
import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_PAYMENT_METHOD
import tokyo.randx.cutie.hab.CutieHabConstant.INDEX_YEAR
import tokyo.randx.cutie.util.CutieConstant.REGEX_PATTERN_NUMBER
import tokyo.randx.cutie.util.CutieDateTimeUtil.formatDate
import tokyo.randx.cutie.util.CutieDateTimeUtil.formatTime
import tokyo.randx.cutie.util.FormatUtil.formatZerosLeadingNumber

object CutieHabUtil {

    fun filterRecordByCategory(
        record: List<CutieHabRecord>, categoryId: Int
    ): List<CutieHabRecord> = record.filter { it.categoryId==categoryId }

    fun filterRecordByUser(
        record: List<CutieHabRecord>, uid: Int
    ): List<CutieHabRecord> = record.filter { it.uid==uid }

    fun filterRecordByPaymentMethod(
        record: List<CutieHabRecord>, paymentMethodId: Int
    ): List<CutieHabRecord> = record.filter { it.paymentMethodId==paymentMethodId }


    // TODO for show record by Category
    fun sortRecordByCategory(record: List<CutieHabRecord>): Map<Int, List<CutieHabRecord>> {
        val recordByCategory = mutableMapOf<Int, MutableList<CutieHabRecord>>()

        for (rcd in record) {
            val key = rcd.categoryId
            val value = recordByCategory[key]
            if (value!=null) {
                value.add(rcd)
                recordByCategory[key] = value
            } else {
                recordByCategory[key] = mutableListOf(rcd)
            }
        }

        return recordByCategory
    }

    fun getTotalAmountListByCategory(record: List<CutieHabRecord>): Map<Int, Int> {
        TODO("Not yet implemented")
    }

    fun getTotalAmountListByUser(record: List<CutieHabRecord>): List<Int> {
        val totalAmountMap = mutableMapOf<Int, Int>()

        record.forEach {
            totalAmountMap[it.uid] = totalAmountMap.getOrElse(it.uid) { 0 } + it.amount
        }

        return totalAmountMap.values.toList()
    }

    fun getTotalAmount(record: List<CutieHabRecord>): Int = record.sumOf { it.amount }

    fun isValidRecord(record: CutieHabRecord): Boolean {
        return record.id==generateRecordId(record) && record.amount!=0
    }

    private fun generateRecordId(record: CutieHabRecord): String {
        return record.date.replace(Regex(REGEX_PATTERN_NUMBER), String()) + record.time.replace(
            Regex(REGEX_PATTERN_NUMBER), String()
        ) + formatZerosLeadingNumber(
            record.categoryId, INDEX_USER - INDEX_CATEGORY
        ) + formatZerosLeadingNumber(
            record.uid, INDEX_PAYMENT_METHOD - INDEX_USER
        ) + formatZerosLeadingNumber(
            record.paymentMethodId, INDEX_AMOUNT - INDEX_PAYMENT_METHOD
        ) + formatZerosLeadingNumber(record.amount, INDEX_END - INDEX_AMOUNT)
    }

    fun generateRecordId(
        date: String, time: String, categoryId: Int, uid: Int, paymentMethodId: Int, amount: Int
    ): String {
        return date.replace(Regex(REGEX_PATTERN_NUMBER), String()) + time.replace(
            Regex(REGEX_PATTERN_NUMBER), String()
        ) + formatZerosLeadingNumber(
            categoryId, INDEX_USER - INDEX_CATEGORY
        ) + formatZerosLeadingNumber(
            uid, INDEX_PAYMENT_METHOD - INDEX_USER
        ) + formatZerosLeadingNumber(
            paymentMethodId, INDEX_AMOUNT - INDEX_PAYMENT_METHOD
        ) + formatZerosLeadingNumber(amount, INDEX_END - INDEX_AMOUNT)
    }

    fun generateMinRecordId(id: String) = id.padEnd(INDEX_END, CutieHabConstant.MIN)

    fun generateMaxRecordId(id: String) = id.padEnd(INDEX_END, CutieHabConstant.MAX)

    fun formatAmountWithCurrencySymbol(symbol: String, amount: Int): String {
        if (symbol.isEmpty() || amount==0) return String()

        return symbol + amount.toString()
    }

    fun parseDate(recordId: String): String {
        if (recordId.length!=INDEX_END) return String()

        return formatDate(
            recordId.substring(INDEX_YEAR, INDEX_MONTH).toInt(),
            recordId.substring(INDEX_MONTH, INDEX_DAY).toInt(),
            recordId.substring(INDEX_DAY, INDEX_HOUR).toInt()
        )
    }

    fun parseTime(recordId: String): String {
        if (recordId.length!=INDEX_END) return String()

        return formatTime(
            recordId.substring(INDEX_HOUR, INDEX_MINUTE).toInt(),
            recordId.substring(INDEX_MINUTE, INDEX_CATEGORY).toInt()
        )
    }

    fun parseCategoryId(recordId: String): Int {
        if (recordId.length!=INDEX_END) return 0

        return recordId.substring(INDEX_CATEGORY, INDEX_USER).toInt()
    }

    fun getUserId(name: String, users: List<CutieHabUser>): Int {

        for (user in users) {
            if (user.name==name) return user.uid
        }

        return 0
    }

    fun getUser(uid: Int, users: List<CutieHabUser>): String {
        if (users.isEmpty()) return String()

        for (user in users) {
            if (user.uid==uid) return user.name
        }

        return users[0].name
    }

    fun parseUserId(recordId: String): Int {
        if (recordId.length!=INDEX_END) return 0

        return recordId.substring(INDEX_USER, INDEX_PAYMENT_METHOD).toInt()
    }

    fun parsePaymentMethodId(recordId: String): Int {
        if (recordId.length!=INDEX_END) return 0

        return recordId.substring(INDEX_PAYMENT_METHOD, INDEX_AMOUNT).toInt()
    }

    fun parseAmount(recordId: String): Int {
        if (recordId.length!=INDEX_END) return 0

        return recordId.substring(INDEX_AMOUNT, INDEX_END).toInt()
    }
}
