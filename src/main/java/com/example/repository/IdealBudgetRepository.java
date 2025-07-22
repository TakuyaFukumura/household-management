package com.example.repository;

import com.example.entity.IdealBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 理想家計簿のリポジトリインターフェース。
 * データベース操作（検索・保存・削除等）を提供します。
 */
@Repository
public interface IdealBudgetRepository extends JpaRepository<IdealBudget, Long> {

    /**
     * カテゴリ名で昇順ソートして全て取得します。
     * @return ソート済み理想家計簿リスト
     */
    List<IdealBudget> findAllByOrderByCategory();

    /**
     * カテゴリ名で理想家計簿を検索します。
     * @param category カテゴリ名
     * @return 該当する理想家計簿（Optional）
     */
    Optional<IdealBudget> findByCategory(String category);
}
