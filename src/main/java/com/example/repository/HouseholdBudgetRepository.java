package com.example.repository;

import com.example.entity.HouseholdBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 家計予算のDB操作（検索・保存・削除等）を提供します。
 */
@Repository
public interface HouseholdBudgetRepository extends JpaRepository<HouseholdBudget, Long> {

    /**
     * カテゴリ名で昇順ソートして全て取得します。
     *
     * @return ソート済み家計予算リスト
     */
    List<HouseholdBudget> findAllByOrderByCategory();

    /**
     * カテゴリ名で家計予算を検索します。
     *
     * @param category カテゴリ名
     * @return 該当する家計予算（Optional）
     */
    Optional<HouseholdBudget> findByCategory(String category);
}
