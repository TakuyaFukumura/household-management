package com.example.repository;

import com.example.entity.HouseholdExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HouseholdExpenseRepository extends JpaRepository<HouseholdExpense, Long> {

    /**
     * 指定した日付範囲内の家計データを、支出日降順・ID降順で取得します。
     *
     * @param startDate 検索開始日
     * @param endDate   検索終了日
     * @return 支出日が指定範囲内の家計費リスト（支出日降順・ID降順）
     */
    List<HouseholdExpense> findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(LocalDate startDate, LocalDate endDate);
}
