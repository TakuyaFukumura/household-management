package com.example.service;

import com.example.entity.Category;
import com.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * カテゴリーサービス
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * 全カテゴリーを取得
     *
     * @return 全カテゴリーのリスト
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * IDでカテゴリーを取得
     *
     * @param id カテゴリーID
     * @return カテゴリー
     */
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * カテゴリー名でカテゴリーを取得
     *
     * @param name カテゴリー名
     * @return カテゴリー
     */
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * カテゴリーを保存
     *
     * @param category カテゴリー
     * @return 保存されたカテゴリー
     */
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * カテゴリーを削除
     *
     * @param id カテゴリーID
     */
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * カテゴリー名の存在チェック
     *
     * @param name カテゴリー名
     * @return 存在する場合true
     */
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}