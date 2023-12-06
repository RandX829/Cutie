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

object CutieHabConstant {
    // Fixed
    const val SAVINGS = 0 //貯金 存款
    const val HOUSING = 1 //住居 住房
    const val ELECTRICITY = 2 //電気 电费
    const val WATER = 3 //水道 水费
    const val GAS = 4 //ガス 燃气费
    const val CELLPHONE = 5 //携帯電話 电话费
    const val INTERNET = 6 //光回線 网络
    const val INSURANCE = 7 //保険 保险
    const val TELEVISION = 8 //テレビ 电视

    // Variable
    const val GROCERIES = 9 //食料品 食品
    const val EATING_OUT = 10 //外食 下馆子
    const val TRANSPORTATION = 11 //交通費 交通费
    const val HOUSEHOLD_SUPPLIES = 12 //日用品 日用品
    const val CLOTHING = 13 //洋服 衣服
    const val BEAUTY = 14 //美容・コスメ 美容
    const val MEDICAL = 15 //医療 医疗
    const val RECREATION = 16 //娯楽 娱乐
    const val TRAVEL = 17 //旅行 旅行
    const val SOCIAL = 18 //交際費 社交
    const val SPECIAL = 19 //一時出費 临时支出
    const val EDUCATION = 20 //教育 教育
    const val AUTOMOTIVE = 21 //車 汽车
    const val DONATION = 22 //寄付 捐款
    const val TAX = 23 //税金 税
    const val PENSION = 30 //年金（厚生年金、国民年金）养老保险
    const val PENSION_IDECO = 31 //iDeCo
    const val PENSION_SONY_LIFE = 32 //ソニー生命
    const val INCOME = 60 //収入 收入
    const val UNKNOWN = 99 //不明 不明

    const val PAYMENT_METHOD_CASH = 0
    const val PAYMENT_METHOD_CARD = 1

    const val INDEX_YEAR = 0 //Year 4 digits
    const val INDEX_MONTH = 4 //Month 2 digits
    const val INDEX_DAY = 6 //Day 2 digits
    const val INDEX_HOUR = 8 //Hour 2 digits
    const val INDEX_MINUTE = 10 //Minute 2 digits
    const val INDEX_CATEGORY = 12 //Category 2 digits
    const val INDEX_USER = 14 //Payer 2 digits
    const val INDEX_PAYMENT_METHOD = 16 //Payment Method 2 digits
    const val INDEX_AMOUNT = 18 //Amount 12 digits
    const val INDEX_END = 30
    const val MIN = '0'
    const val MAX = '9'
}
