package com.example.service

import com.example.dto.ChartDataDto
import com.example.entity.HouseholdExpense
import com.example.repository.HouseholdExpenseRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.time.YearMonth

/**
 * HouseholdExpenseServiceのテストクラス
 */
class HouseholdExpenseServiceSpec extends Specification {

    HouseholdExpenseRepository householdExpenseRepository = Mock()

    @Subject
    HouseholdExpenseService householdExpenseService = new HouseholdExpenseService(householdExpenseRepository)

    def "支出チャートデータ取得_正常系"() {
        given: "指定年月の支出データが複数存在する"
        def targetYm = YearMonth.of(2025, 7)
        def startDate = targetYm.atDay(1)
        def endDate = targetYm.atEndOfMonth()
        def expenses = [
                new HouseholdExpense(LocalDate.of(2025, 7, 1), "食費", new BigDecimal("1000"), "朝食"),
                new HouseholdExpense(LocalDate.of(2025, 7, 2), "食費", new BigDecimal("1500"), "昼食"),
                new HouseholdExpense(LocalDate.of(2025, 7, 3), "交通費", new BigDecimal("500"), "電車代"),
                new HouseholdExpense(LocalDate.of(2025, 7, 4), "光熱費", new BigDecimal("8000"), "電気代")
        ]
        householdExpenseRepository.findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(startDate, endDate) >> expenses

        when: "支出チャートデータを取得する"
        def result = householdExpenseService.getExpenseChartDataByYearAndMonth(targetYm)

        then: "3つのカテゴリのチャートデータが返される"
        result.size() == 3

        and: "食費カテゴリが正しく集計されている"
        def foodData = result.find { it.category == "食費" }
        foodData != null
        foodData.amount == new BigDecimal("2500") // 1000 + 1500

        and: "交通費カテゴリが正しく設定されている"
        def transportData = result.find { it.category == "交通費" }
        transportData != null
        transportData.amount == new BigDecimal("500")

        and: "光熱費カテゴリが正しく設定されている"
        def utilityData = result.find { it.category == "光熱費" }
        utilityData != null
        utilityData.amount == new BigDecimal("8000")
    }

    def "支出チャートデータ取得_データが空の場合"() {
        given: "指定年月の支出データが存在しない"
        def targetYm = YearMonth.of(2025, 1)
        def startDate = targetYm.atDay(1)
        def endDate = targetYm.atEndOfMonth()
        householdExpenseRepository.findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(startDate, endDate) >> []

        when: "支出チャートデータを取得する"
        def result = householdExpenseService.getExpenseChartDataByYearAndMonth(targetYm)

        then: "空のリストが返される"
        result.isEmpty()
    }

    def "支出チャートデータ取得_同一カテゴリ複数データ"() {
        given: "同一カテゴリの支出データが複数存在する"
        def targetYm = YearMonth.of(2025, 7)
        def startDate = targetYm.atDay(1)
        def endDate = targetYm.atEndOfMonth()
        def expenses = [
                new HouseholdExpense(LocalDate.of(2025, 7, 1), "食費", new BigDecimal("1000"), "朝食"),
                new HouseholdExpense(LocalDate.of(2025, 7, 1), "食費", new BigDecimal("1200"), "昼食"),
                new HouseholdExpense(LocalDate.of(2025, 7, 1), "食費", new BigDecimal("800"), "夕食")
        ]
        householdExpenseRepository.findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(startDate, endDate) >> expenses

        when: "支出チャートデータを取得する"
        def result = householdExpenseService.getExpenseChartDataByYearAndMonth(targetYm)

        then: "1つのカテゴリのチャートデータが返される"
        result.size() == 1

        and: "金額が正しく合計されている"
        with(result[0]) {
            category == "食費"
            amount == new BigDecimal("3000") // 1000 + 1200 + 800
        }
    }

    def "IDで支出データ取得_存在する場合"() {
        given: "指定IDの支出データが存在する"
        def expense = new HouseholdExpense(LocalDate.of(2025, 7, 1), "食費", new BigDecimal("1000"), "朝食")
        expense.setId(1L)
        householdExpenseRepository.findById(1L) >> Optional.of(expense)

        when: "IDで支出データを取得する"
        def result = householdExpenseService.getExpenseById(1L)

        then: "該当する支出データが返される"
        result.isPresent()
        with(result.get()) {
            id == 1L
            category == "食費"
            amount == new BigDecimal("1000")
            description == "朝食"
        }
    }

    def "IDで支出データ取得_存在しない場合"() {
        given: "指定IDの支出データが存在しない"
        householdExpenseRepository.findById(999L) >> Optional.empty()

        when: "存在しないIDで支出データを取得する"
        def result = householdExpenseService.getExpenseById(999L)

        then: "空のOptionalが返される"
        result.isEmpty()
    }

    def "支出データ保存"() {
        given: "保存する支出データ"
        def expense = new HouseholdExpense(LocalDate.now(), "交通費", new BigDecimal("500"), "電車代")

        when: "支出データを保存する"
        householdExpenseService.saveExpense(expense)

        then: "リポジトリのsaveメソッドが呼ばれる"
        1 * householdExpenseRepository.save(expense)
    }

    def "支出データ削除"() {
        when: "指定IDの支出データを削除する"
        householdExpenseService.deleteExpense(1L)

        then: "リポジトリのdeleteByIdメソッドが呼ばれる"
        1 * householdExpenseRepository.deleteById(1L)
    }
}
