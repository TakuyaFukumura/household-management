package com.example.repository;

import com.example.entity.HouseholdBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * 金額で降順ソートして全て取得します。
     *
     * @return 金額降順ソート済み家計予算リスト
     */
    List<HouseholdBudget> findAllByOrderByAmountDesc();

}
