package com.example.repository;

import com.example.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * カテゴリ名で検索
     *
     * @param name カテゴリ名
     * @return カテゴリ
     */
    Optional<Category> findByName(String name);

    /**
     * カテゴリ名が存在するかチェック
     *
     * @param name カテゴリ名
     * @return 存在する場合true
     */
    boolean existsByName(String name);
}
