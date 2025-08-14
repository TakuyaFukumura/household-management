package com.example.service

import com.example.entity.Category
import com.example.repository.CategoryRepository
import spock.lang.Specification
import spock.lang.Subject

/**
 * CategoryServiceのテストクラス
 */
class CategoryServiceSpec extends Specification {

    CategoryRepository categoryRepository = Mock()

    @Subject
    CategoryService categoryService = new CategoryService(categoryRepository)

    // Helper method to create mock categories
    private Category createMockCategory(Long id, String name, String description = null) {
        def category = new Category()
        category.setId(id)
        category.setName(name)
        category.setDescription(description ?: "${name}の説明")
        return category
    }

    def "予算未設定カテゴリー取得_正常系"() {
        given: "予算が設定されていないカテゴリーが複数存在する"
        def availableCategories = [
                createMockCategory(1L, "交通費"),
                createMockCategory(2L, "光熱費"),
                createMockCategory(3L, "雑費"),
                createMockCategory(4L, "医療費")
        ]
        categoryRepository.findCategoriesWithoutBudget() >> availableCategories

        when: "予算未設定カテゴリーを取得する"
        def result = categoryService.getCategoriesWithoutBudget()

        then: "4つのカテゴリーが返される"
        result.size() == 4
        
        and: "取得されたカテゴリーが正しい"
        result.collect { it.name } == ["交通費", "光熱費", "雑費", "医療費"]
    }

    def "予算未設定カテゴリー取得_全カテゴリーに予算設定済みの場合"() {
        given: "すべてのカテゴリーに予算が設定済み"
        categoryRepository.findCategoriesWithoutBudget() >> []

        when: "予算未設定カテゴリーを取得する"
        def result = categoryService.getCategoriesWithoutBudget()

        then: "空のリストが返される"
        result.isEmpty()
    }

    def "予算未設定カテゴリー取得_単一カテゴリーが利用可能"() {
        given: "予算未設定のカテゴリーが1つ存在する"
        def availableCategory = createMockCategory(1L, "交通費")
        categoryRepository.findCategoriesWithoutBudget() >> [availableCategory]

        when: "予算未設定カテゴリーを取得する"
        def result = categoryService.getCategoriesWithoutBudget()

        then: "1つのカテゴリーが返される"
        result.size() == 1
        result[0].name == "交通費"
    }
}