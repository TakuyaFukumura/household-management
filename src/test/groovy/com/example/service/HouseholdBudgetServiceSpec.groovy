package com.example.service


import com.example.entity.Category
import com.example.entity.HouseholdBudget
import com.example.repository.HouseholdBudgetRepository
import spock.lang.Specification
import spock.lang.Subject

/**
 * HouseholdBudgetServiceのテストクラス
 */
class HouseholdBudgetServiceSpec extends Specification {

    HouseholdBudgetRepository householdBudgetRepository = Mock()

    @Subject
    HouseholdBudgetService householdBudgetService = new HouseholdBudgetService(householdBudgetRepository)

    // Helper method to create mock categories
    private Category createMockCategory(String name) {
        def category = new Category()
        category.setId(name.hashCode() as Long)
        category.setName(name)
        category.setDescription("${name}の説明")
        return category
    }

    def "予算チャートデータ取得_正常系"() {
        given: "予算データが複数存在する"
        def housingCategory = createMockCategory("住居費")
        def foodCategory = createMockCategory("食費")
        def entertainmentCategory = createMockCategory("娯楽費")
        def utilitiesCategory = createMockCategory("光熱費")
        def budgets = [
                new HouseholdBudget(1L, housingCategory, new BigDecimal("42000"), null, null),
                new HouseholdBudget(2L, foodCategory, new BigDecimal("30000"), null, null),
                new HouseholdBudget(3L, entertainmentCategory, new BigDecimal("20000"), null, null),
                new HouseholdBudget(4L, utilitiesCategory, new BigDecimal("15000"), null, null)
        ]
        householdBudgetRepository.findAllByOrderByCategory() >> budgets

        when: "予算チャートデータを取得する"
        def result = householdBudgetService.getBudgetChartData()

        then: "4つのチャートデータが返される"
        result.size() == 4

        and: "各データが正しく設定されている"
        with(result[0]) {
            category == "住居費"
            amount == new BigDecimal("42000")
        }
        with(result[1]) {
            category == "食費"
            amount == new BigDecimal("30000")
        }
        with(result[2]) {
            category == "娯楽費"
            amount == new BigDecimal("20000")
        }
        with(result[3]) {
            category == "光熱費"
            amount == new BigDecimal("15000")
        }
    }

    def "予算チャートデータ取得_データが空の場合"() {
        given: "予算データが存在しない"
        householdBudgetRepository.findAllByOrderByCategory() >> []

        when: "予算チャートデータを取得する"
        def result = householdBudgetService.getBudgetChartData()

        then: "空のリストが返される"
        result.isEmpty()
    }

    def "予算チャートデータ取得_単一データ"() {
        given: "予算データが1つ存在する"
        def foodCategory = createMockCategory("食費")
        def budgets = [
                new HouseholdBudget(1L, foodCategory, new BigDecimal("30000"), null, null)
        ]
        householdBudgetRepository.findAllByOrderByCategory() >> budgets

        when: "予算チャートデータを取得する"
        def result = householdBudgetService.getBudgetChartData()

        then: "1つのチャートデータが返される"
        result.size() == 1

        and: "データが正しく設定されている"
        with(result[0]) {
            category == "食費"
            amount == new BigDecimal("30000")
        }
    }

    def "IDで予算データ取得_存在する場合"() {
        given: "指定IDの予算データが存在する"
        def foodCategory = createMockCategory("食費")
        def budget = new HouseholdBudget(1L, foodCategory, new BigDecimal("30000"), null, null)
        householdBudgetRepository.findById(1L) >> Optional.of(budget)

        when: "IDで予算データを取得する"
        def result = householdBudgetService.getHouseholdBudgetById(1L)

        then: "該当する予算データが返される"
        result.isPresent()
        result.get().id == 1L
        result.get().categoryEntity.name == "食費"
        result.get().amount == new BigDecimal("30000")
    }

    def "IDで予算データ取得_存在しない場合"() {
        given: "指定IDの予算データが存在しない"
        householdBudgetRepository.findById(999L) >> Optional.empty()

        when: "存在しないIDで予算データを取得する"
        def result = householdBudgetService.getHouseholdBudgetById(999L)

        then: "空のOptionalが返される"
        result.isEmpty()
    }

    def "予算データ保存"() {
        given: "保存する予算データ"
        def transportCategory = createMockCategory("交通費")
        def budget = new HouseholdBudget(transportCategory, new BigDecimal("10000"))

        when: "予算データを保存する"
        householdBudgetService.saveHouseholdBudget(budget)

        then: "リポジトリのsaveメソッドが呼ばれる"
        1 * householdBudgetRepository.save(budget)
    }

    def "予算データ削除"() {
        when: "指定IDの予算データを削除する"
        householdBudgetService.deleteHouseholdBudget(1L)

        then: "リポジトリのdeleteByIdメソッドが呼ばれる"
        1 * householdBudgetRepository.deleteById(1L)
    }
}
