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
package tokyo.randx.cutie.android.hab.util

import android.content.Context
import tokyo.randx.cutie.android.R
import tokyo.randx.cutie.hab.CutieHabConstant

object CutieHabResUtil {

    fun getCategoryList(context: Context) = listOf(
        context.getString(R.string.hab_category_savings),
        context.getString(R.string.hab_category_pension_ideco),
        context.getString(R.string.hab_category_pension_sonylife),
        context.getString(R.string.hab_category_housing),
        context.getString(R.string.hab_category_electricity),
        context.getString(R.string.hab_category_water),
        context.getString(R.string.hab_category_gas),
        context.getString(R.string.hab_category_cellphone),
        context.getString(R.string.hab_category_internet),
        context.getString(R.string.hab_category_insurance),
        context.getString(R.string.hab_category_television),
        context.getString(R.string.hab_category_groceries),
        context.getString(R.string.hab_category_eating_out),
        context.getString(R.string.hab_category_transportation),
        context.getString(R.string.hab_category_household_supplies),
        context.getString(R.string.hab_category_clothing),
        context.getString(R.string.hab_category_beauty),
        context.getString(R.string.hab_category_medical),
        context.getString(R.string.hab_category_recreation),
        context.getString(R.string.hab_category_travel),
        context.getString(R.string.hab_category_social),
        context.getString(R.string.hab_category_special),
        context.getString(R.string.hab_category_education),
        context.getString(R.string.hab_category_automotive),
        context.getString(R.string.hab_category_donation),
        context.getString(R.string.hab_category_tax),
        context.getString(R.string.hab_category_unknown)
    )

    fun getCategoryId(category: String, context: Context) = when (category) {
        context.getString(R.string.hab_category_savings) -> CutieHabConstant.SAVINGS
        context.getString(R.string.hab_category_pension_ideco) -> CutieHabConstant.PENSION_IDECO
        context.getString(R.string.hab_category_pension_sonylife) -> CutieHabConstant.PENSION_SONY_LIFE
        context.getString(R.string.hab_category_housing) -> CutieHabConstant.HOUSING
        context.getString(R.string.hab_category_electricity) -> CutieHabConstant.ELECTRICITY
        context.getString(R.string.hab_category_water) -> CutieHabConstant.WATER
        context.getString(R.string.hab_category_gas) -> CutieHabConstant.GAS
        context.getString(R.string.hab_category_cellphone) -> CutieHabConstant.CELLPHONE
        context.getString(R.string.hab_category_internet) -> CutieHabConstant.INTERNET
        context.getString(R.string.hab_category_insurance) -> CutieHabConstant.INSURANCE
        context.getString(R.string.hab_category_television) -> CutieHabConstant.TELEVISION
        context.getString(R.string.hab_category_groceries) -> CutieHabConstant.GROCERIES
        context.getString(R.string.hab_category_eating_out) -> CutieHabConstant.EATING_OUT
        context.getString(R.string.hab_category_transportation) -> CutieHabConstant.TRANSPORTATION
        context.getString(R.string.hab_category_household_supplies) -> CutieHabConstant.HOUSEHOLD_SUPPLIES
        context.getString(R.string.hab_category_clothing) -> CutieHabConstant.CLOTHING
        context.getString(R.string.hab_category_beauty) -> CutieHabConstant.BEAUTY
        context.getString(R.string.hab_category_medical) -> CutieHabConstant.MEDICAL
        context.getString(R.string.hab_category_recreation) -> CutieHabConstant.RECREATION
        context.getString(R.string.hab_category_travel) -> CutieHabConstant.TRAVEL
        context.getString(R.string.hab_category_social) -> CutieHabConstant.SOCIAL
        context.getString(R.string.hab_category_special) -> CutieHabConstant.SPECIAL
        context.getString(R.string.hab_category_education) -> CutieHabConstant.EDUCATION
        context.getString(R.string.hab_category_automotive) -> CutieHabConstant.AUTOMOTIVE
        context.getString(R.string.hab_category_donation) -> CutieHabConstant.DONATION
        context.getString(R.string.hab_category_tax) -> CutieHabConstant.TAX
        else -> CutieHabConstant.UNKNOWN
    }

    fun getCategory(categoryId: Int, context: Context) = when (categoryId) {
        CutieHabConstant.SAVINGS -> context.getString(R.string.hab_category_savings)
        CutieHabConstant.HOUSING -> context.getString(R.string.hab_category_housing)
        CutieHabConstant.ELECTRICITY -> context.getString(R.string.hab_category_electricity)
        CutieHabConstant.WATER -> context.getString(R.string.hab_category_water)
        CutieHabConstant.GAS -> context.getString(R.string.hab_category_gas)
        CutieHabConstant.CELLPHONE -> context.getString(R.string.hab_category_cellphone)
        CutieHabConstant.INTERNET -> context.getString(R.string.hab_category_internet)
        CutieHabConstant.INSURANCE -> context.getString(R.string.hab_category_insurance)
        CutieHabConstant.TELEVISION -> context.getString(R.string.hab_category_television)
        CutieHabConstant.GROCERIES -> context.getString(R.string.hab_category_groceries)
        CutieHabConstant.EATING_OUT -> context.getString(R.string.hab_category_eating_out)
        CutieHabConstant.TRANSPORTATION -> context.getString(R.string.hab_category_transportation)
        CutieHabConstant.HOUSEHOLD_SUPPLIES -> context.getString(R.string.hab_category_household_supplies)
        CutieHabConstant.CLOTHING -> context.getString(R.string.hab_category_clothing)
        CutieHabConstant.BEAUTY -> context.getString(R.string.hab_category_beauty)
        CutieHabConstant.MEDICAL -> context.getString(R.string.hab_category_medical)
        CutieHabConstant.RECREATION -> context.getString(R.string.hab_category_recreation)
        CutieHabConstant.TRAVEL -> context.getString(R.string.hab_category_travel)
        CutieHabConstant.SOCIAL -> context.getString(R.string.hab_category_social)
        CutieHabConstant.SPECIAL -> context.getString(R.string.hab_category_special)
        CutieHabConstant.EDUCATION -> context.getString(R.string.hab_category_education)
        CutieHabConstant.AUTOMOTIVE -> context.getString(R.string.hab_category_automotive)
        CutieHabConstant.DONATION -> context.getString(R.string.hab_category_donation)
        CutieHabConstant.TAX -> context.getString(R.string.hab_category_tax)
        CutieHabConstant.PENSION_IDECO -> context.getString(R.string.hab_category_pension_ideco)
        CutieHabConstant.PENSION_SONY_LIFE -> context.getString(R.string.hab_category_pension_sonylife)
        else -> context.getString(R.string.hab_category_unknown)
    }

    fun getCategoryDrawableResId(categoryId: Int): Int {
        return when (categoryId) {
            CutieHabConstant.SAVINGS -> R.drawable.savings
            CutieHabConstant.PENSION_IDECO -> R.drawable.savings
            CutieHabConstant.PENSION_SONY_LIFE -> R.drawable.savings
            CutieHabConstant.HOUSING -> R.drawable.housing
            CutieHabConstant.ELECTRICITY -> R.drawable.electricity
            CutieHabConstant.WATER -> R.drawable.water
            CutieHabConstant.GAS -> R.drawable.gas
            CutieHabConstant.CELLPHONE -> R.drawable.cellphone
            CutieHabConstant.INTERNET -> R.drawable.internet
            CutieHabConstant.INSURANCE -> R.drawable.insurance
            CutieHabConstant.TELEVISION -> R.drawable.television
            CutieHabConstant.GROCERIES -> R.drawable.groceries
            CutieHabConstant.EATING_OUT -> R.drawable.eating_out
            CutieHabConstant.TRANSPORTATION -> R.drawable.transportation
            CutieHabConstant.HOUSEHOLD_SUPPLIES -> R.drawable.household_supplies
            CutieHabConstant.CLOTHING -> R.drawable.clothing
            CutieHabConstant.BEAUTY -> R.drawable.beauty
            CutieHabConstant.MEDICAL -> R.drawable.medical
            CutieHabConstant.RECREATION -> R.drawable.recreation
            CutieHabConstant.TRAVEL -> R.drawable.travel
            CutieHabConstant.SOCIAL -> R.drawable.social
            CutieHabConstant.SPECIAL -> R.drawable.special
            CutieHabConstant.EDUCATION -> R.drawable.education
            CutieHabConstant.AUTOMOTIVE -> R.drawable.automotive
            CutieHabConstant.DONATION -> R.drawable.donation
            CutieHabConstant.TAX -> R.drawable.tax
            CutieHabConstant.UNKNOWN -> R.drawable.unknown
            else -> R.drawable.unknown
        }
    }

    fun getCategory(categoryId: Int): Int {
        return when (categoryId) {
            CutieHabConstant.SAVINGS -> R.drawable.savings
            CutieHabConstant.PENSION_IDECO -> R.drawable.savings
            CutieHabConstant.PENSION_SONY_LIFE -> R.drawable.savings
            CutieHabConstant.HOUSING -> R.drawable.housing
            CutieHabConstant.ELECTRICITY -> R.drawable.electricity
            CutieHabConstant.WATER -> R.drawable.water
            CutieHabConstant.GAS -> R.drawable.gas
            CutieHabConstant.CELLPHONE -> R.drawable.cellphone
            CutieHabConstant.INTERNET -> R.drawable.internet
            CutieHabConstant.INSURANCE -> R.drawable.insurance
            CutieHabConstant.TELEVISION -> R.drawable.television
            CutieHabConstant.GROCERIES -> R.drawable.groceries
            CutieHabConstant.EATING_OUT -> R.drawable.eating_out
            CutieHabConstant.TRANSPORTATION -> R.drawable.transportation
            CutieHabConstant.HOUSEHOLD_SUPPLIES -> R.drawable.household_supplies
            CutieHabConstant.CLOTHING -> R.drawable.clothing
            CutieHabConstant.BEAUTY -> R.drawable.beauty
            CutieHabConstant.MEDICAL -> R.drawable.medical
            CutieHabConstant.RECREATION -> R.drawable.recreation
            CutieHabConstant.TRAVEL -> R.drawable.travel
            CutieHabConstant.SOCIAL -> R.drawable.social
            CutieHabConstant.SPECIAL -> R.drawable.special
            CutieHabConstant.EDUCATION -> R.drawable.education
            CutieHabConstant.AUTOMOTIVE -> R.drawable.automotive
            CutieHabConstant.DONATION -> R.drawable.donation
            CutieHabConstant.TAX -> R.drawable.tax
            CutieHabConstant.UNKNOWN -> R.drawable.unknown
            else -> R.drawable.unknown
        }
    }

    //TODO change to user data store
    fun getUserDrawableResId(userId: Int): Int {
        return when (userId) {
            0 -> R.drawable.automotive
            1 -> R.drawable.beauty
            else -> R.drawable.unknown
        }
    }

    fun getPaymentMethodList(context: Context) = listOf(
        context.getString(R.string.hab_payment_method_cash),
        context.getString(R.string.hab_payment_method_credit_card)
    )

    fun getPaymentMethodId(paymentMethod: String, context: Context) = when (paymentMethod) {
        context.getString(R.string.hab_payment_method_cash) -> CutieHabConstant.PAYMENT_METHOD_CASH
        context.getString(R.string.hab_payment_method_credit_card) -> CutieHabConstant.PAYMENT_METHOD_CARD
        else -> CutieHabConstant.PAYMENT_METHOD_CASH
    }

    fun getPaymentMethod(paymentMethodId: Int, context: Context) = when (paymentMethodId) {
        CutieHabConstant.PAYMENT_METHOD_CASH -> context.getString(R.string.hab_payment_method_cash)
        CutieHabConstant.PAYMENT_METHOD_CARD -> context.getString(R.string.hab_payment_method_credit_card)
        else -> context.getString(R.string.hab_payment_method_cash)
    }

    fun getPaymentMethodDrawableResId(paymentMethodId: Int): Int {
        return when (paymentMethodId) {
            CutieHabConstant.PAYMENT_METHOD_CASH -> R.drawable.cash
            CutieHabConstant.PAYMENT_METHOD_CARD -> R.drawable.credit_card
            else -> R.drawable.unknown
        }
    }
}
